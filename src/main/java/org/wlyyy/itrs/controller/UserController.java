package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestPageableResponse;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.request.UserQuery;
import org.wlyyy.itrs.service.AuthenticationService;
import org.wlyyy.itrs.service.DepartmentService;
import org.wlyyy.itrs.service.UserService;
import org.wlyyy.itrs.vo.UserListItemVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 根据条件查询用户，不进行分页
     *
     * @param userQuery 用户查询条件对象
     * @return 用户对象列表，不带分页参数
     */
    @RequestMapping(value = "/myProfile/user/list", method = RequestMethod.GET)
    public BaseRestResponse<List<UserListItemVo>> listUser(final UserQuery userQuery) {
        UserAgent userAgent = authenticationService.isLogin().getData();

        int pageNo = 1;
        int pageSize = Integer.MAX_VALUE;
        BaseServicePageableRequest<UserQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize, userQuery);
        BaseServicePageableResponse<User> userQueryResult = userService.findByCondition(request);
        List<User> userList = userQueryResult.getDatas();
        List<User> userListFilter = userList.stream().filter(user -> !Objects.equals(user.getId(), userAgent.getId())).collect(Collectors.toList());
        List<UserListItemVo> datas = userListFilter.stream().map(source -> UserListItemVo.buildFromDomain(source,
                (did) -> departmentService.findById(did).getDepartmentName()))
                .collect(Collectors.toList());
        return new BaseRestResponse<>(true, "按条件查询用户成功!", datas);

    }

    /**
     * 根据条件分页查询用户，并进行分页
     *
     * @param userQuery 用户查询条件对象
     * @param pageNo 页码
     * @param pageSize 分页大小
     * @return 用户对象列表，带分页参数
     */
    @RequestMapping(value = "/myProfile/user/listPage", method = RequestMethod.GET)
    public BaseRestPageableResponse<UserListItemVo> listUserPage(final UserQuery userQuery, final int pageNo, final int pageSize) {
        BaseServicePageableRequest<UserQuery> request = new BaseServicePageableRequest<>(pageNo, pageSize, userQuery);
        BaseServicePageableResponse<User> userQueryResult = userService.findByCondition(request);
        List<User> userList = userQueryResult.getDatas();
        List<UserListItemVo> datas = userList.stream().map(source -> UserListItemVo.buildFromDomain(source,
                (did) -> departmentService.findById(did).getDepartmentName()))
                .collect(Collectors.toList());
        return new BaseRestPageableResponse<>(true, "按条件分页查询用户成功!", datas,
                userQueryResult.getPageNo(), userQueryResult.getPageSize(), userQueryResult.getTotal());
    }
}
