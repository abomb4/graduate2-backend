package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.*;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.request.RoleQuery;
import org.wlyyy.itrs.service.RoleService;

import java.util.Set;

/**
 * 角色相关api
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色信息列表
     *
     * @return 角色信息列表
     */
    @RequestMapping(value = "/myProfile/user/roleList")
    BaseRestPageableResponse<Role> listRoles() {
        final int pageNo = 1;
        final int pageSize = Integer.MAX_VALUE;
        BaseServicePageableResponse<Role> roleListResult = roleService.findByCondition(
                new BaseServicePageableRequest<RoleQuery>(pageNo, pageSize, new RoleQuery()));

        return new BaseRestPageableResponse<>(true, roleListResult.getMessage(), roleListResult.getDatas(),
                roleListResult.getPageNo(), roleListResult.getPageSize(), roleListResult.getTotal());
    }

    /**
     * 查询该用户id下的已有角色列表
     *
     * @param id 用户id
     * @return 已有角色列表
     */
    @RequestMapping(value = "/myProfile/user/exisRoleList/{id}", method = RequestMethod.GET)
    BaseRestResponse<Set<Role>> listExisRols(@PathVariable("id") Long id) {
        Set<Role> exisRoleSet = roleService.findRolesByUserId(id).getData();
        return new BaseRestResponse<>(true, "查询该用户已有角色列表成功!", exisRoleSet);
    }

    /**
     * 给用户分布角色
     *
     * @param id 用户id
     * @param roleIdSet 角色id set
     * @return 分配角色成功or失败
     */
    @RequestMapping(value = "/myProfile/user/assignRole/{id}", method = RequestMethod.GET)
    BaseRestResponse<String> assignRoles(@PathVariable("id") Long id, Set<Long> roleIdSet) {
        BaseServiceResponse<String> updateResult = roleService.updateUserRole(id, roleIdSet);
        return new BaseRestResponse<>(updateResult.isSuccess(), updateResult.getMessage(), updateResult.getData());
    }
}
