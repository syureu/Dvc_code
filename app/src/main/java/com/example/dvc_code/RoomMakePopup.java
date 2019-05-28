package com.example.dvc_code;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class RoomMakePopup extends Dialog {

    Context context;
    RoomInfoActivity ria;
    EditText editText_room_name, editText_room_pw;
    RadioButton radioButton_max_2, radioButton_max_3;
    CheckBox checkBox_room_pw;
    Button roomMakeButton, cancelButton;

    public RoomMakePopup(Context context, RoomInfoActivity ria) {
        super(context);
        this.context = context;
        this.ria = ria;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_make_room);

        editText_room_name = (EditText) findViewById(R.id.editText_room_name);
        editText_room_pw = (EditText) findViewById(R.id.editText_room_pw);
        radioButton_max_2 = (RadioButton) findViewById(R.id.radioButton_max_2);
        radioButton_max_3 = (RadioButton) findViewById(R.id.radioButton_max_3);
        checkBox_room_pw = (CheckBox) findViewById(R.id.checkBox_room_pw);
        roomMakeButton = (Button) findViewById(R.id.PopupRoomMakeButton);
        cancelButton = (Button) findViewById(R.id.PopupCancelButton);

        editText_room_pw.setEnabled(false);

        checkBox_room_pw.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_room_pw.isChecked()) {
                    editText_room_pw.setEnabled(true);
                } else {
                    editText_room_pw.setEnabled(false);
                }
            }
        });

        roomMakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_room_name.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "방 제목은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(checkBox_room_pw.isChecked() && editText_room_pw.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "비밀번호를 설정하면 비밀번호 입력은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tmp;
                if (radioButton_max_2.isChecked()) tmp = 2;
                else tmp = 3;
                ria.PopupRoomMakeRequest(editText_room_name.getText().toString(), tmp, checkBox_room_pw.isChecked(), editText_room_pw.getText().toString());
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
