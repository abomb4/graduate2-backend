package org.wlyyy.itrs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.ScoreFlow;
import org.wlyyy.itrs.domain.ScoreRule;
import org.wlyyy.itrs.domain.ScoreUser;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.request.ScoreFlowQuery;
import org.wlyyy.itrs.request.ScoreRuleQuery;
import org.wlyyy.itrs.service.AuthenticationService;
import org.wlyyy.itrs.service.ScoreFlowService;
import org.wlyyy.itrs.service.ScoreRuleService;
import org.wlyyy.itrs.service.ScoreUserService;
import org.wlyyy.itrs.vo.ScoreFlowListItemVo;
import org.wlyyy.itrs.vo.ScoreRuleListItemVo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ScoreController {

    @Autowired
    private ScoreFlowService scoreFlowService;

    @Autowired
    private ScoreUserService scoreUserService;

    @Autowired
    private ScoreRuleService scoreRuleService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/myProfile/score/list", method = RequestMethod.GET)
    public BaseRestPageableResponse<ScoreFlowListItemVo> queryScoreFlowList(int pageNo, int pageSize) {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();
        Sort sort = new Sort(new Order(Sort.Direction.DESC, "gmt_modify"));
        BaseServicePageableRequest<ScoreFlowQuery> scoreFlowRequest = new BaseServicePageableRequest<>(pageNo, pageSize,
                new ScoreFlowQuery().setUserId(userAgent.getId()).setSort(sort));
        BaseServicePageableResponse<ScoreFlow> scoreFlowResult = scoreFlowService.findByCondition(scoreFlowRequest);
        List<ScoreFlow> scoreFlowList = scoreFlowResult.getDatas();
        List<ScoreFlowListItemVo> datas = scoreFlowList.stream().map(ScoreFlowListItemVo::buildFromDomain)
                .collect(Collectors.toList());

        return new BaseRestPageableResponse<>(true, "查询用户积分流程变动记录成功!", datas,
                scoreFlowResult.getPageNo(), scoreFlowResult.getPageSize(), scoreFlowResult.getTotal());
    }

    @RequestMapping(value = "/myProfile/score/current", method = RequestMethod.GET)
    public BaseRestResponse<Integer> queryCurrentScore() {
        // 获取当前登录用户信息
        UserAgent userAgent = authenticationService.isLogin().getData();
        ScoreUser scoreUser = scoreUserService.findByUserId(userAgent.getId());
        Integer currentScore = scoreUser.getCurrentScore();
        return new BaseRestResponse<>(true, "查询用户当前积分成功!", currentScore);
    }

    @RequestMapping(value = "/score/rule", method = RequestMethod.GET)
    public BaseRestPageableResponse<ScoreRuleListItemVo> queryScoreRuleList(int pageNo, int pageSize) {
        BaseServicePageableRequest<ScoreRuleQuery> scoreRuleRequest = new BaseServicePageableRequest<>(pageNo, pageSize, new ScoreRuleQuery());
        BaseServicePageableResponse<ScoreRule> scoreRuleResult = scoreRuleService.findByCondition(scoreRuleRequest);
        List<ScoreRule> scoreRuleList = scoreRuleResult.getDatas();
        List<ScoreRuleListItemVo> datas = scoreRuleList.stream().map(ScoreRuleListItemVo::buildFromDomain)
                .collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询积分规则成功!", datas,
                scoreRuleResult.getPageNo(), scoreRuleResult.getPageSize(), scoreRuleResult.getTotal());
    }
}
