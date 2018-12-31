package com.eco.wisdompark.converter.req;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.wisdompark.domain.dto.req.PageReqDto;

/**
 * 分页请求对象转换类
 */
public class PageReqDtoToPageConverter {

    /**
     * 分页请求对象转换
     *
     * @param pageReqDto 分页请求对象
     * @return 分页对象
     */
    public static IPage converter(PageReqDto pageReqDto) {
        IPage page = new Page();
        page.setCurrent(pageReqDto.getCurrent());
        page.setSize(pageReqDto.getSize());
        return page;
    }

}