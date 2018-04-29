package org.wlyyy.common.domain;

import org.springframework.data.domain.Sort;

/**
 * 通用分页查询请求类
 */
public class BaseServicePageableRequest<T> {

    private final int pageNo;
    private final int pageSize;
    private final T data;

    public BaseServicePageableRequest(int pageNo, int pageSize, T data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public T getData() {
        return data;
    }
}
