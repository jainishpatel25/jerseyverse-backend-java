package com.ecommerce.jerseyverse.util;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import org.springframework.data.domain.Page;

public class PaginationUtils {

    public static <T> PageResponse<T> buildPageResponse(Page<T> page) {

        PageResponse<T> response = new PageResponse<>();

        response.setContent(page.getContent());
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());

        return response;
    }
}