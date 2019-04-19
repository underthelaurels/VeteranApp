package com.agileenhanced.cs4704.veteransconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChatSelection extends AppCompatActivity
{
    private ListView chatsList;
    private ChatRoom[] chatRooms;
    private final String CHANNEL_ID = "channel_id";
    private final String ROOM_NAME = "room_name";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_selection);
        chatsList = (ListView) findViewById(R.id.chats_list);

        // Static list of chat rooms that are available.
        // Dynamically adding new chat rooms is a feature we would like to add.
        chatRooms = new ChatRoom[] {
                new ChatRoom("General Chat", "0iAgd2gcNwqRffSn"),
                new ChatRoom("Disabilities", "jb8BAgl3r9ckiAmf"),
                new ChatRoom("Education", "TwgCrzNIzk8BJ33S"),
                new ChatRoom("Army Chat", "xT3nUf9TQgfkHia5"),
                new ChatRoom("Navy Chat", "Xj3B3ycXFzruMA7i"),
                new ChatRoom("Marine Corps Chat", "ymYwUWQvEsvYxJiM"),
                new ChatRoom("Air Force Chat", "cJ41qBI49psgQv7T"),
                new ChatRoom("Coast Guard", "CS46sqZeEyjfWr4W")
                    };

        String[] chatRoomNames = new String[] {"General Chat", "Disabilities", "Education", "Army Chat", "Navy Chat", "Marine Corps Chat", "Air Force Chat", "Coast Guard Chat"};
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatRoomNames);
        chatsList.setAdapter(itemsAdapter);
        chatsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
            {
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                chatIntent.putExtra(CHANNEL_ID, chatRooms[pos].getChannelID());
                chatIntent.putExtra(ROOM_NAME, chatRooms[pos].getRoomName());
                startActivity(chatIntent);
            }
        });
    }
}

class ChatRoom
{
    private String roomName;
    private String channelID;

    public ChatRoom(String name, String id)
    {
        this.roomName = name;
        this.channelID = id;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public String getChannelID()
    {
        return channelID;
    }
}
