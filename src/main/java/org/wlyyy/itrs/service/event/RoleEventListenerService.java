package org.wlyyy.itrs.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.event.ApplyFlowEvent;
import org.wlyyy.itrs.request.RoleQuery;
import org.wlyyy.itrs.service.RoleService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * hr指派面试官后 给该员工赋予面试官角色
 */
@Component
public class RoleEventListenerService {

    @Autowired
    private RoleService roleService;

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RoleEventListenerService.class);

    @Transactional
    @EventListener
    public void assignIntervieweeRole(ApplyFlowEvent event) throws Exception {
        String otherMessage = event.getOtherMessage();

        if (otherMessage != null && otherMessage.equals("指派")) {
            ApplyFlow applyFlow = event.getApplyFlow();
            Long intervieweeId = applyFlow.getCurrentDealer();

            // 获取面试官角色对应的role id
            BaseServicePageableRequest<RoleQuery> roleRequest = new BaseServicePageableRequest<>(1, 1,
                    new RoleQuery().setRoleName("ROLE_INTERVIEWEE"));
            Role intervieweeRole = roleService.findByCondition(roleRequest).getDatas().get(0);
            Set<Long> roleSet = new HashSet<>();
            roleSet.add(intervieweeRole.getId());

            // 先判断该员工是否有面试官角色
            Set<Role> exitRoleSet = roleService.findRolesByUserId(intervieweeId).getData();
            Set<Long> exitRoleId = exitRoleSet.stream().map(Role::getId).collect(Collectors.toSet());
            if (exitRoleId.contains(intervieweeRole.getId())) {
                // 该员工已有面试官角色
                LOG.info("该员工已有面试官角色!");
            } else {
                roleService.updateUserRole(intervieweeId, roleSet);
                LOG.info("给【" + intervieweeId + "】分配面试官角色成功!");
            }
        }
    }
}
