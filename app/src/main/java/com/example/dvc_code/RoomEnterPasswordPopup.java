package com.example.dvc_code;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RoomEnterPasswordPopup extends Dialog {
    Context context;
    RoomInfoActivity ria;

    int room_number;

    EditText editText;
    Button button1, button2;

    public RoomEnterPasswordPopup(Context context, RoomInfoActivity ria, int room_number) {
        super(context);
        this.context = context;
        this.ria = ria;
        this.room_number= room_number;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_enter_room_password);

        editText = (EditText) findViewById(R.id.editText_popup_password);
        button1 = (Button) findViewById(R.id.button_popup_password_enter);
        button2 = (Button) findViewById(R.id.button_popup_password_cancel);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "비밀번호 방은 비밀번호가 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ria.RoomEnterRequest_Next(room_number, editText.getText().toString());
                dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
