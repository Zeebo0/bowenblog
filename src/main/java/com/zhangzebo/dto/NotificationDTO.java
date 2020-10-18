package com.zhangzebo.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    //  提问者，发起通知的人
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    //  提醒的类型
    private String typeName;
    private Long outerId;
    private Integer type;
}
