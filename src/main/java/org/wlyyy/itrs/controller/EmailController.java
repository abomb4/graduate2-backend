package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.EmailLog;
import org.wlyyy.itrs.request.EmailLogQuery;
import org.wlyyy.itrs.service.EmailLogService;
import org.wlyyy.itrs.vo.EmailLogListItemVo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmailController {

    @Autowired
    private EmailLogService emailLogService;

    /**
     * 分页查询邮件日志列表
     *
     * @param pageNo 页码
     * @param pageSize 分页大小
     * @return 邮件日志列表
     */
    @RequestMapping(value = "/myProfile/user/emailLog/list", method = RequestMethod.GET)
    public BaseRestPageableResponse<EmailLogListItemVo> queryEmailLogList(int pageNo, int pageSize) {

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "gmt_modify"));
        BaseServicePageableRequest<EmailLogQuery> emailLogRequest = new BaseServicePageableRequest<>(pageNo, pageSize,
                new EmailLogQuery().setSort(sort));
        BaseServicePageableResponse<EmailLog> emailLogResult = emailLogService.findByCondition(emailLogRequest);
        List<EmailLog> emailLogList = emailLogResult.getDatas();
        List<EmailLogListItemVo> datas = emailLogList.stream().map(emailLog -> EmailLogListItemVo.buildFromDomain(emailLog))
                .collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "分页查询邮件日志成功", datas,
                emailLogResult.getPageNo(), emailLogResult.getPageSize(), emailLogResult.getTotal());
    }
}
