package com.agileenhanced.cs4704.veteransconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SignupActivity extends AppCompatActivity
{
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.input_confirm)
    EditText _confirmPassText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup()
    {
        Log.d(TAG, "Signup");

        if (!validate())
        {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_DayNight_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String username = _nameText.getText().toString();
        final String password = _passwordText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://35.245.223.73/user/register";

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
                                            onSignupSuccess(username, password);
                                        } else
                                        {
                                            onSignupFailed();
                                        }
                                        progressDialog.dismiss();
                                    }
                                }, 1500);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                onSignupFailed();
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


    public void onSignupSuccess(String username, String password)
    {
        Toast.makeText(getBaseContext(), "Signup successful!", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("username", username);
        resultIntent.putExtra("password", password);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onSignupFailed()
    {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED, null);
        _signupButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String username = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirm = _confirmPassText.getText().toString();

        if (username.isEmpty() || username.length() < 3)
        {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else
        {
            _nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10)
        {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else
        {
            _passwordText.setError(null);
        }

        if (!confirm.equals(password))
        {
            _confirmPassText.setError("must match above password");
            valid = false;
        } else
        {
            _confirmPassText.setError(null);
        }

        return valid;
    }
}