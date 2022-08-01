package ru.clevertec.check.runner.util;

import java.util.Collections;
import java.util.List;

public class Pagination {

    private static final int DEFAULT_PAGE_SIZE = 20;

    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize == 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }
        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
