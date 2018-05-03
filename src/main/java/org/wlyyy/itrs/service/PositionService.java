package org.wlyyy.itrs.service;

import org.wlyyy.itrs.domain.PositionType;

import java.util.List;

public interface PositionService {

    /**
     * 构造职位类别树
     *
     * @return tree
     */
    List<PositionType> getPositionTypes();

    /**
     * 根据职位类别id查询
     *
     * @param id 职位类别id
     * @return 职位类别，没有的话返回null
     */
    PositionType findById(Long id);
}
