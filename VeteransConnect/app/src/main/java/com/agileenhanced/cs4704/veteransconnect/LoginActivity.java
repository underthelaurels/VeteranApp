package com.agileenhanced.cs4704.veteransconnect;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private SharedPreferences myPrefs;
    private static final String PREFS_NAME = "PrefsFile";

    @InjectView(R.id.input_username)
    EditText _usernameText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    private CheckBox rememberMe;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        rememberMe = (CheckBox) findViewById(R.id.checkBox_rememberMe);

        _loginButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        myPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        getPreferencesData();
    }

    public void login()
    {
        Log.d(TAG, "Login");

        if (!validate())
        {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);


        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        sendLoginRequest(username, password);
    }

    private void sendLoginRequest(final String username, final String password)
    {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://35.245.223.73/user/login";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(final String response)
                    {
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        new android.os.Handler().postDelayed(
                                new Runnable()
                                {
                                    public void run()
                                    {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        if (response.contains("\"status\":\"success\""))
                                        {
                                            // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                            // Save the user data if it was correct.
                                            if (rememberMe.isChecked())
                                            {
                                                Boolean isChecked = rememberMe.isChecked();
                                                SharedPreferences.Editor editor = myPrefs.edit();
                                                editor.putString("pref_name", _usernameText.getText().toString());
                                                editor.putString("pref_pass", _passwordText.getText().toString());
                                                editor.putBoolean("pref_check", isChecked);
                                                editor.apply();
                                            } else
                                            {
                                                myPrefs.edit().clear().apply();
                                                _usernameText.setText("");
                                                _passwordText.setText("");
                                            }
                                            onLoginSuccess(username);
                                        } else
                                        {
                                            onLoginFailed();
                                        }
                                        progressDialog.dismiss();
                                    }
                                }, 1000);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                onLoginFailed();
            }
        })
        {
            protected Map<String, String> getParams()
            {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                MyData.put("password", password);
                return MyData;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SIGNUP)
        {
            if (resultCode == RESULT_OK)
            {
                String username = data.getStringExtra("username");
                String password = data.getStringExtra("password");
                sendLoginRequest(username, password);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String username)
    {
        _loginButton.setEnabled(true);
        // Send the user name to the main activity so we can log them out.
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra("USER_NAME", username);
        startActivity(mainIntent);
    }

    public void onLoginFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    private void getPreferencesData()
    {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name"))
        {
            String u = sp.getString("pref_name", "not found.");
            if (!u.equals("not found."))
            {
                _usernameText.setText(u.toString());
            }
        }
        if (sp.contains("pref_pass"))
        {
            String p = sp.getString("pref_pass", "not found.");
            if (!p.equals("not found."))
            {
                _passwordText.setText(p.toString());
            }
        }
        if (sp.contains("pref_check"))
        {
            Boolean b = sp.getBoolean("pref_check", false);
            rememberMe.setChecked(b);
        }
    }

    public boolean validate()
    {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty())
        {
            _usernameText.setError("enter a valid username");
            valid = false;
        } else
        {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10)
        {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else
        {
            _passwordText.setError(null);
        }

        return valid;
    }
}
