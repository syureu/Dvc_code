package com.example.dvc_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    static Socket socket;
    static DataOutputStream writeSocket;
    static DataInputStream readSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.mainActivityButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "접속중입니다.", Toast.LENGTH_SHORT).show();
                (new Connect()).start();
            }
        });
    }

    void NextActivity_with_Room_info(String str) {
        Intent intent = new Intent(MainActivity.this,RoomInfoActivity.class);
        intent.putExtra("room_info",str);
        startActivity(intent);
    }

    class Connect extends Thread{
        public void run(){
            TextView textView = (TextView)findViewById(R.id.mainActivityTextView);
            EditText editText = (EditText)findViewById(R.id.mainActivityEditText);
            try {
                /*
                InetAddress addr = java.net.InetAddress.getByName("syureu.iptime.org");
                String straddr = addr.getHostAddress();
                */

                socket = new Socket("113.198.237.207", 13799);
                writeSocket = new DataOutputStream(socket.getOutputStream());
                readSocket = new DataInputStream(socket.getInputStream());

                byte[] b = editText.getText().toString().getBytes("utf-8");
                writeSocket.write(b);

                int recvCnt = readSocket.read(b);
                byte[] formatted = new byte[recvCnt];
                for(int i=0; i<recvCnt; i++) {
                    formatted[i]=b[i];
                }

                NextActivity_with_Room_info(new String(formatted, StandardCharsets.UTF_8));
            } catch (Exception e) {
                textView.setText(e.toString());
            }
        }
    }
}
