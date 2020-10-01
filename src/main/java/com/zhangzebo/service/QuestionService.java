package com.zhangzebo.service;

import com.zhangzebo.dto.PageDTO;
import com.zhangzebo.dto.QuestionDTO;
import com.zhangzebo.mapper.QuestionMapper;
import com.zhangzebo.mapper.UserMapper;
import com.zhangzebo.model.Question;
import com.zhangzebo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    //  获取所有的问题
    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        //  获取总共的问题条数
        Integer totalCount = questionMapper.count();
        //  计算分页数
        pageDTO.setPagination(totalCount, page, size);

        //  防止输入超过页面范围的值(尚未完善，发现报错)
        if (page < 1) {
            page = 1;
        }else if (page > pageDTO.getTotalPages()) {
            page = pageDTO.getTotalPages();
        }

        Integer offset = size *  (page - 1);
        //  带头像属性的问题集合
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //  没有头像属性的问题集合
        List<Question> questionList = questionMapper.list(offset, size);


        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //  将问题列表赋值到页面属性中
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }
}
