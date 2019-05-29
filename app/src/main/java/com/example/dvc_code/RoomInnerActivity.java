package com.example.dvc_code;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RoomInnerActivity extends AppCompatActivity {

    TextView textView;
    Button exit_button;
    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_room);

        textView=(TextView)findViewById(R.id.textView_innerRoom);
        exit_button=(Button)findViewById(R.id.button_innerRoom_Exit);

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomInfoActivity.ria.InnerRoomExitRequest();
                finish();
            }
        });
    }
}
