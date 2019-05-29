package com.example.dvc_code;

public class Message {
    public int flags;

    public String room_info;

    public String room_name;
    public int max_people;
    public boolean bool_pw;
    public String room_pw;

    public boolean room_make_success;

    public int room_number;

    public int room_enter_code;

    public boolean room_exit_success;

    public void DoMessage(RoomInfoActivity ria) {
        if (flags == 1) {
            ria.data= room_info;
            RoomInfoActivity.handler.sendEmptyMessage(0);
        }
        if (flags == 3) {
            if (room_make_success) {
                ria.RoomEnterRequest_Sequence(room_number, room_pw);
            } else {
                //System.out.println("방 개설 실패..");
            }
        }
        if (flags == 5) {
            if (room_enter_code == 0) {
                ria.RoomEnterRequest_Success();
            } else if (room_enter_code == 1) {
                ria.handler.sendEmptyMessage(1);
            } else if (room_enter_code == 2) {
                ria.handler.sendEmptyMessage(2);
            } else if (room_enter_code == 3) {
                ria.handler.sendEmptyMessage(3);
            }
        }
    }
}
