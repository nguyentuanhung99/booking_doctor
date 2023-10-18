package com.cg.inventorydepartmentservice.service.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.domain.Page;

import java.util.List;

@With
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListResult<T> {
    public long totalElements;
    public int totalPages;
    public int size;
    public int page;
    public List<T> content;


    public static <T> ListResult<T> from(Page<T> page) {
        return new ListResult<T>()
                .withPage(page.getNumber() + 1)
                .withSize(page.getSize())
                .withContent(page.getContent())
                .withTotalElements(page.getTotalElements())
                .withTotalPages(page.getTotalPages());
    }
}

