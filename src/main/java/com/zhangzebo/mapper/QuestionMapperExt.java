package com.zhangzebo.mapper;

import com.zhangzebo.dto.QuestionQueryDTO;
import com.zhangzebo.model.Question;

import java.util.List;

public interface QuestionMapperExt {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}