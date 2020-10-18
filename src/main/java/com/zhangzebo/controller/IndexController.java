package com.zhangzebo.controller;

import com.zhangzebo.dto.PaginationDTO;
import com.zhangzebo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//  控制登录
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        @RequestParam(value = "search", required = false) String search) {
        PaginationDTO pagination = questionService.list(page, size, search);
        //  此时添加到页面的不仅又question信息，还有提问用户的信息，主要是为了获取头像
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "index";
    }
}
