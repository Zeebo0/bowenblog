package com.zhangzebo.enums;

public enum NotificationStatusEnum {
    UNREAD(0, "未读"),
    READ(1, "以读")
    ;

    private int status;
    private String name;

    NotificationStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
