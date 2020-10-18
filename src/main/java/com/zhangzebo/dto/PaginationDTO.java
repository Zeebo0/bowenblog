package com.zhangzebo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;    //  问题集合，回复集合
    private boolean showPrevious;   //  是否展示上一页按钮
    private boolean showNext;   //  是否展示下一页按钮
    private boolean showFirstPage; //   是否展示首页按钮
    private boolean showEndPage;    //  是否展示末页按钮
    private Integer page;    //  当前页码
    private List<Integer> pages = new ArrayList<>();    //  当前页面展示的页码集合
    private Integer totalCount; //  总问题数
    private Integer totalPages; //  总分页数

    public void setPagination(Integer totalPages, Integer page) {
        this.totalPages = totalPages;

        //  给当前页码赋值
        this.page = page;
        //  设定一页最多展示7个页码，判断哪些页码能加入到当前页码集合中，先将当前页码加入到页码集合中
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPages) {
                pages.add(page + i);
            }
        }

        //  是否展示上一页按钮
        showPrevious = (page == 1) ? false : true;
        //  是否展示下一页按钮
        showNext = (page == totalPages) ? false : true;
        //  是否展示首页按钮
        showFirstPage = (pages.contains(1)) ? false : true;
        //  是否展示末页按钮
        showEndPage = (pages.contains(totalPages)) ? false : true;
    }
}
