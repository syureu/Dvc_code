package com.example.dvc_code;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import flexjson.JSONSerializer;

public class RoomInfoActivity extends AppCompatActivity {

    Intent intent;
    TextView textView;
    String data;
    String[] strArray;
    LinearLayout roomInfoActivityLinearLayout;
    Button roomInfoActivityRefreshButton, roomInfoActivityMakeButton;
    static RoomInfoActivity ria;

    public static Handler handler;

    public static Gson gson = new Gson();
    public static JSONSerializer serializer = new JSONSerializer();
    public static String messageStr;
    public static byte[] messageByte;

    TextView[] rm_textView;
    TextView[] rn_textView;
    TextView[] rmas_textView;
    TextView[] np_textView;
    TextView[] mp_textView;
    TextView[] lock_textView;
    Button[] enter_button;


    public void SetRoom() {
        roomInfoActivityLinearLayout.removeAllViews();
        roomInfoActivityLinearLayout.addView(textView);

        if (data.equals("0"))
            textView.setText("현재 개설된 방이 없습니다. 새로고침 하시거나 방을 개설하세요.");
        else {
            strArray = data.split(" ");
            textView.setText("현재 개설된 방 개수 : " + strArray[0]);

            int room_count = Integer.parseInt(strArray[0]);

            rm_textView = new TextView[room_count];
            rn_textView = new TextView[room_count];
            rmas_textView = new TextView[room_count];
            np_textView = new TextView[room_count];
            mp_textView = new TextView[room_count];
            lock_textView = new TextView[room_count];
            enter_button = new Button[room_count];

            LinearLayout[] ll_horizon = new LinearLayout[room_count];

            for (int i = 0, j = 1; i < room_count; i++, j = j + 6) {

                rm_textView[i] = new TextView(this);    //방 번호
                rm_textView[i].setText(strArray[j]);
                rn_textView[i] = new TextView(this);    //방 제목
                rn_textView[i].setText(strArray[j + 1]);
                rmas_textView[i] = new TextView(this);    //방장 이름
                rmas_textView[i].setText(strArray[j + 2]);
                np_textView[i] = new TextView(this);    //현재 인원
                np_textView[i].setText(strArray[j + 3]);
                mp_textView[i] = new TextView(this);    //최대 인원
                mp_textView[i].setText(strArray[j + 4]);
                lock_textView[i] = new TextView(this);    // 잠금방인지, 아닌지 표시
                lock_textView[i].setText(strArray[j + 5]);
                enter_button[i] = new Button(this);
                enter_button[i].setText("입장");

                final int tmp = i;

                enter_button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoomEnterRequest(tmp);
                    }
                });

                ll_horizon[i] = new LinearLayout(this);
                ll_horizon[i].setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params2.weight = 1;
                ll_horizon[i].addView(rm_textView[i], params2);
                ll_horizon[i].addView(rn_textView[i], params2);
                ll_horizon[i].addView(rmas_textView[i], params2);
                ll_horizon[i].addView(np_textView[i], params2);
                ll_horizon[i].addView(mp_textView[i], params2);
                ll_horizon[i].addView(lock_textView[i], params2);
                ll_horizon[i].addView(enter_button[i], params2);
                ll_horizon[i].setBackgroundColor(Color.rgb((int) (Math.random() * 100) + 155, (int) (Math.random() * 100) + 155, (int) (Math.random() * 100) + 155));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                roomInfoActivityLinearLayout.addView(ll_horizon[i], params);
            }
        }
    }

    void RefreshButtonClick() {
        Message m = new Message();
        m.flags = 0;
        (new Connect(m)).start();
    }

    public void PopupRoomMakeRequest(String room_name, int max_people, boolean bool_pw, String room_pw) {
        Message m = new Message();
        m.flags = 2;
        m.room_name = room_name;
        m.max_people = max_people;
        m.bool_pw = bool_pw;
        if (bool_pw) m.room_pw = room_pw;
        (new Connect(m)).start();
    }

    void RoomEnterRequest(int room_number) {
        if (lock_textView[room_number].getText().toString().equals("True")) {
            RoomEnterPasswordPopup repp = new RoomEnterPasswordPopup(this, this, room_number);
            repp.show();
        } else {
            RoomEnterRequest_Next(room_number, null);
        }
    }

    void RoomEnterRequest_Next(int room_number, String room_pw) {
        Message m = new Message();
        m.flags = 4;
        m.room_number = Integer.parseInt(rm_textView[room_number].getText().toString());
        m.room_pw = room_pw;
        new Connect(m).start();
    }

    void RoomEnterRequest_Sequence(int room_number, String room_pw) {
        Message m = new Message();
        m.flags = 4;
        m.room_number = room_number;
        m.room_pw = room_pw;
        new Connect(m).start();
    }

    void RoomEnterRequest_Success() {
        Intent intent = new Intent(RoomInfoActivity.this, RoomInnerActivity.class);
        startActivity(intent);
    }

    public void InnerRoomExitRequest() {
        Message m = new Message();
        m.flags = 6;
        new Connect(m).start();
    }

    void MakeButtonClick() {
        RoomMakePopup rmp = new RoomMakePopup(this, this);
        rmp.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        intent = getIntent();
        data = intent.getStringExtra("room_info");
        ria = this;

        textView = (TextView) findViewById(R.id.roomInfoActivityTextView);
        roomInfoActivityLinearLayout = (LinearLayout) findViewById(R.id.roomInfoActivityLinearLayout);
        roomInfoActivityRefreshButton = (Button) findViewById(R.id.roomInfoActivityRefreshButton);
        roomInfoActivityMakeButton = (Button) findViewById(R.id.roomInfoActivityMakeButton);

        messageStr = "";
        messageByte = new byte[1024];

        SetRoom();

        roomInfoActivityRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshButtonClick();
            }
        });
        roomInfoActivityMakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeButtonClick();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch(msg.what) {
                    case 0:
                        SetRoom();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "해당 방 번호를 찾을 수 없습니다. 새로고침 하십시요.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "해당 방은 정원 초과입니다. 새로고침 하십시요.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    class Connect extends Thread {
        Message m;

        public Connect(Message m) {
            this.m = m;
        }

        public void run() {
            messageStr = serializer.serialize(m) + "<EOF>";
            try {
                messageByte = messageStr.getBytes("utf-8");
                MainActivity.writeSocket.write(messageByte);
                messageByte = new byte[1024];
                int recvCnt = MainActivity.readSocket.read(messageByte);
                byte[] formatted = new byte[recvCnt];
                for (int i = 0; i < recvCnt; i++) formatted[i] = messageByte[i];
                messageStr = new String(formatted, StandardCharsets.UTF_8);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                // System.out.println(e);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // System.out.println(e);
            }
            Message recvm = gson.fromJson(messageStr, Message.class);
            recvm.DoMessage(ria);
        }
    }
}