package com.example.dvc_code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Socket socket;
    DataOutputStream writeSocket;
    DataInputStream readSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "접속중입니다.", Toast.LENGTH_SHORT).show();
                (new Connect()).start();
            }
        });
    }

    class Connect extends Thread{


        public void run(){
            TextView textView = (TextView) findViewById(R.id.textView);
            try {
                /*
                InetAddress addr = java.net.InetAddress.getByName("syureu.iptime.org");
                String straddr = addr.getHostAddress();
                */

                socket = new Socket("61.105.240.85", 13799);
                writeSocket = new DataOutputStream(socket.getOutputStream());
                byte[] b = textView.getText().toString().getBytes();
                writeSocket.write(b);

                readSocket = new DataInputStream(socket.getInputStream());
                readSocket.read(b);
                String tmp = b.toString();
                //Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "예외 발생", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
