package com.zhangzebo.mapper;

import com.zhangzebo.model.Comment;
import com.zhangzebo.model.CommentExample;
import com.zhangzebo.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentMapperExt {
    int incCommentCount(Comment record);
}