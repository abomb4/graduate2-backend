package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.Candidate;
import org.wlyyy.itrs.request.CandidateQuery;
import org.wlyyy.itrs.service.CandidateService;
import org.wlyyy.itrs.vo.CandidateListItemVo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;

    @RequestMapping(value = "/myProfile/candidate/list", method = RequestMethod.GET)
    public BaseRestPageableResponse<CandidateListItemVo> queryCandidateList(CandidateQuery candidateQuery, int pageNo, int pageSize) {

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "gmt_modify"));
        BaseServicePageableRequest<CandidateQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize, candidateQuery.setSort(sort));
        BaseServicePageableResponse<Candidate> candidateResult =  candidateService.findByCondition(request);

        if (!candidateResult.isSuccess()) {
            return new BaseRestPageableResponse<>(false, "查询人才库列表失败!", null,
                    candidateResult.getPageNo(), candidateResult.getPageSize(), candidateResult.getTotal());
        }

        List<Candidate> candidateList = candidateResult.getDatas();
        List<CandidateListItemVo> datas = candidateList.stream().map(source -> CandidateListItemVo.buildFromDomain(source)).collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "查询人才库列表成功!", datas,
                candidateResult.getPageNo(), candidateResult.getPageSize(), candidateResult.getTotal());
    }

    @RequestMapping(value = "/myProfile/candidate/get/{id}", method = RequestMethod.GET)
    public BaseRestResponse<CandidateListItemVo> findCandidateById(@PathVariable("id") Long id) {
        Candidate candidate = candidateService.findById(id);
        CandidateListItemVo candidateListItemVo = CandidateListItemVo.buildFromDomain(candidate);
        return new BaseRestResponse<>(true, "根据候选人id查找招聘需求成功!", candidateListItemVo);
    }
}
