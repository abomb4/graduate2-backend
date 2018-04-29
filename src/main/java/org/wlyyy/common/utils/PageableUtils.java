package org.wlyyy.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.wlyyy.common.domain.BaseServicePageableRequest;

/**
 * 分页util，主要用于处理Spring Pageable相关东西
 *
 * @author wly
 */
public class PageableUtils {

    /**
     * 根据我定义的对象创建Spring Pageable
     * @param req 自定义请求对象
     * @return Pageable对象
     */
    public static Pageable getPageable(BaseServicePageableRequest req) {
        return new PageRequest(req.getPageNo() - 1, req.getPageSize());
    }

    /**
     * 根据我定义的对象创建Spring Pageable
     * @param req 自定义请求对象
     * @param sort 排序方式
     * @return Pageable对象
     */
    public static Pageable getPageable(BaseServicePageableRequest req, Sort sort) {
        return new PageRequest(req.getPageNo() - 1, req.getPageSize(), sort);
    }
}
