package org.wlyyy.itrs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.vo.DemandListItemVo;

@RestController
@RequestMapping("demand")
public class DemandController {

    @RequestMapping("list")
    public BaseRestPageableResponse<DemandListItemVo> queryDemandList() {

        return null;
    }
}
