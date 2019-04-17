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

        chatRooms = new ChatRoom[] {
                new ChatRoom("General Chat", "0iAgd2gcNwqRffSn"),
                new ChatRoom("Disabilities", "jb8BAgl3r9ckiAmf")
                    };

        String[] chatRoomNames = new String[] {"General Chat", "Disabilities"};
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
