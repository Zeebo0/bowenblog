package com.zhangzebo.controller;

import com.zhangzebo.dto.QuestionDTO;
import com.zhangzebo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        //  获取QuestionDTO对象是因为该对象属性比较丰富
        QuestionDTO questionDTO = questionService.getById(id);
        //  累加浏览次数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
