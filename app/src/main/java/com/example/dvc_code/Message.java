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
                ria.RoomEnterRequest_Next(room_number, room_pw);
            } else {
                //System.out.println("방 개설 실패..");
            }
        }
        if (flags == 5) {
            if (room_enter_code == 0) {
                ria.RoomEnterRequest_Success();
            } else if (room_enter_code == 1) {
                //System.out.println("해당 방 번호를 찾을 수 없습니다.");
            } else if (room_enter_code == 2) {
                //System.out.println("방의 정원이 초과되었습니다.");
            } else if (room_enter_code == 3) {
                //System.out.println("비밀번호가 틀렸습니다.");
            }
        }
    }
}
