package com.projetko.stockmanagement.common;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPage,
        boolean first,
        boolean last
) {

}
