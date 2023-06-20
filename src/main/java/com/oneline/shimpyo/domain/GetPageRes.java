package com.oneline.shimpyo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetPageRes<T> {

    private int totalPage;
    private long totalElements;
    private int pageSize;
    private int numberOfElements;
    private List<T> list;

    public GetPageRes(int totalPage, long totalElements, int pageSize, int numberOfElements, List<T> list) {
        this.totalPage = totalPage;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.numberOfElements = numberOfElements;
        this.list = list;
    }

    public GetPageRes(Page<T> page) {
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.numberOfElements = page.getNumberOfElements();
        this.list = page.getContent();
    }
}
