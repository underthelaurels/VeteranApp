package com.agileenhanced.cs4704.veteransconnect;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.HistoryRoomListener;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;
import com.scaledrone.lib.SubscribeOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatActivity extends AppCompatActivity implements RoomListener
{
    private String channelID;
    private String roomName = "observable-";
    private TextView roomNameText;
    private EditText editText;
    private Scaledrone scaledrone;
    private ListView messagesView;
    private MessageAdapter messageAdapter;
    private MemberData data;
    private final String CHANNEL_ID = "channel_id";
    private final String ROOM_NAME = "room_name";
    private final String PREFERENCES = "PrefsFile";
    private final String USER_NAME = "pref_name";
    private final String USER_COLOR = "sender_color";
    private RequestQueue queue;

    HashMap<String, MemberData> memberDataHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        roomNameText = (TextView) findViewById(R.id.chat_name);
        roomNameText.setText(getIntent().getStringExtra(ROOM_NAME));
        roomName = roomName + getIntent().getStringExtra(ROOM_NAME);
        channelID = getIntent().getStringExtra(CHANNEL_ID);
        // This is where we write the message
        editText = (EditText) findViewById(R.id.editText);
        messagesView = (ListView) findViewById(R.id.messages_view);
        assert (messagesView != null);
        messageAdapter = new MessageAdapter(this);
        messagesView.setAdapter(messageAdapter);

        SharedPreferences sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        if (sp.getString(USER_COLOR, "").equals(""))
        {
            sp.edit().putString(USER_COLOR, getRandomColor()).commit();
        }
        data = new MemberData(sp.getString(USER_NAME, getRandomName()),
                sp.getString(USER_COLOR, getRandomColor()));

        queue = Volley.newRequestQueue(this);

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener()
        {
            @Override
            public void onOpen()
            {
                System.out.println("Scaledrone connection open");
                // Since the MainActivity itself already implement RoomListener we can pass it as a target
                scaledrone.subscribe(roomName, ChatActivity.this);
            }

            @Override
            public void onOpenFailure(Exception ex)
            {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex)
            {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason)
            {
                System.err.println(reason);
            }
        });

        getMessageHistory();
    }

    private String getRandomName()
    {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor()
    {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while (sb.length() < 7)
        {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    // Successfully connected to Scaledrone room
    @Override
    public void onOpen(Room room)
    {
        System.out.println("Connected to room");
    }

    // Connecting to Scaledrone room failed
    @Override
    public void onOpenFailure(Room room, Exception ex)
    {
        System.err.println(ex);
    }

    // Received a message from Scaledrone room
    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage)
    {
        // To transform the raw JsonNode into a POJO we can use an ObjectMapper
        final ObjectMapper mapper = new ObjectMapper();
        try
        {
            // member.clientData is a MemberData object, let's parse it as such
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            // if the clientID of the message sender is the same as our's it was sent by us
            boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            // since the message body is a simple string in our case we can use json.asText() to parse it as such
            // if it was instead an object we could use a similar pattern to data parsing
            final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    messageAdapter.add(message);
                    // scroll the ListView to the last added element
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    // Sending a message to the Scaledrone room and to our own database.
    public void sendMessage(View view)
    {
        String message = editText.getText().toString();
        if (message.length() > 0)
        {
            postToDatabase(message);
            // clear the EditText for convenience.
            editText.getText().clear();
        }
    }

    private void postToDatabase(final String message)
    {
        String url = "http://35.245.223.73/chat/send";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(final String response)
                    {
                        if (response.contains("\"status\":\"success\""))
                        {
                            scaledrone.publish(roomName, message);
                        } else
                        {
                            Toast.makeText(getApplicationContext(), "Message failed to send", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams()
            {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("message", message);
                MyData.put("sender", data.getName());
                MyData.put("sender_color", data.getColor());
                MyData.put("channel_id", channelID);
                return MyData;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void getMessageHistory()
    {
        String url = "http://35.245.223.73/chat/poll?channel_id=" + channelID;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // display response
                        try
                        {
                            // Toast.makeText(getApplicationContext(), response.toString(4), Toast.LENGTH_LONG).show();
                            // TODO: figure out how to parse JSON and add the messages to the messageAdapter
                            JSONArray messageHistory = (JSONArray) response.get("messages");
                            for (int i = 0; i < messageHistory.length(); i++)
                            {
                                JSONObject currObj = messageHistory.getJSONObject(i);
                                final Message message = new Message(currObj.getString("message"),
                                        new MemberData(currObj.getString("sender"), currObj.getString("sender_color")),
                                        currObj.getString("sender").equals(data.getName()));
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        messageAdapter.add(message);
                                        // scroll the ListView to the last added element
                                        messagesView.setSelection(messagesView.getCount() - 1);
                                    }
                                });
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }
}

class MemberData
{
    private String name;
    private String color;

    public MemberData(String name, String color)
    {
        this.name = name;
        this.color = color;
    }

    // Add an empty constructor so we can later parse JSON into MemberData using Jackson
    public MemberData()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }
}

