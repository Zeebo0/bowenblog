package com.zhangzebo.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String comment;
    private Integer type;
    private String content;
}
