package com.zhangzebo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private List<QuestionDTO> questions;    //  总问题集合
    private boolean showPrevious;   //  是否展示上一页
    private boolean showFirstPage;  //  是否展示第一页
    private boolean showNext;   //  是否展示下一页
    private boolean showEndPage;    //  是否展示最后一页
    private Integer totalPages; //  总页数
    private Integer page;   //  当前页码
    private List<Integer> pages = new ArrayList<>();    //  当前页面展示的页面数

    /**
     *
     * @param totalCount 总问题数
     * @param page  当前页
     * @param size  每页显示的问题数
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            totalPages = totalCount / size;
        } else {
            totalPages = (totalCount / size) + 1;
        }

        this.page = page;

        //  将当前页加入到页面集合中，进行循环判断，设置当前页左侧显示3个，右侧也显示3个
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            //  判断出的页面加到集合的头部
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            //  判断出的页面加到集合的尾部
            if (page + i <= totalPages) {
                pages.add(page + i);
            }
        }

        //  判断是否展示上一页
        showPrevious = (page == 1) ? false : true;

        //  判断是否展示下一页
        showNext = (page == totalPages) ? false : true;

        //  判断是否展示第一页(这个针对的是页面的首页箭头)
        showFirstPage = (pages.contains(1)) ? false : true;

        //  判断是否展示最后一页(这个针对的是页面的尾页箭头)
        showEndPage = (pages.contains(totalPages)) ? false : true;
    }
}
