package com.example.dvc_code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RoomInfoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        Intent intent = getIntent();
        String data = intent.getStringExtra("room_info");

        TextView textView = (TextView) findViewById(R.id.roomInfoActivityTextView);
        if(data.equals("0"))
            textView.setText("현재 개설된 방이 없습니다. 새로고침 하시거나 방을 개설하세요.");
        //else if() {

        //}

    }
}
