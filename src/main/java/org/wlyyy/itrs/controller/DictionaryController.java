package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.itrs.domain.PositionType;
import org.wlyyy.itrs.service.PositionService;

import java.util.List;

@RestController
@RequestMapping(value = "/dict")
public class DictionaryController {

    @Autowired
    private PositionService positionService;

    /**
     * 查询职位类别树
     *
     * @return 职位类别树
     */
    @RequestMapping(value = "/positionTypeTree", method = RequestMethod.GET)
    public BaseRestResponse<List<PositionType>> findPositionTypeTree() {
        List<PositionType> treeTypes = positionService.getPositionTypes();
        return new BaseRestResponse<>(true, "查询职位类别树成功!", treeTypes);
    }
}
