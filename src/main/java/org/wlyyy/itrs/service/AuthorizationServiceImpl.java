package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlyyy.common.cache.SimpleKeyValueCache;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleKeyValueCache cache;

    @Override
    public BaseServiceResponse<UserAgent> login(String userName, String password, String clientIp) {
        // 1. 校验参数
        // 2. 验证密码
        final BaseServiceResponse<User> validateResponse = userService.validateUser(userName, password);
        if (validateResponse.isSuccess()) {
            final User user = validateResponse.getData();
            // 3. 生成SessionKey
            // 4. TODO 获取roles
            // 4. 创建UserAgent
            // 4. 存储到分布式缓存
            final String sessionKey = SecurityUtils.generateSessionKey(user, clientIp, LocalDateTime.now());
            // TODO ROLES!!!!!!!!!!!!!!!!!!1
            final Set<Role> roles = new HashSet<>();
            final UserAgent userAgent = new UserAgent()
                    .setSessionKey(sessionKey)
                    .setId(user.getId())
                    .setEmail(user.getEmail())
                    .setRoles(roles)
                    .setUserName(user.getUserName())
                    .setSex(user.getSex())
                    .setDepartmentId(user.getDepartmentId())
                    .setRealName(user.getRealName())
                    .setLoginTime(LocalDateTime.now())
                    .setRefreshTime(LocalDateTime.now())
                    .setIp(clientIp)
                    ;

            // Put to distributed cache
            cache.put(sessionKey, userAgent, UserAgent.class);
            return new BaseServiceResponse<>(true, "Login successfully", userAgent, null);

        } else {
            return new BaseServiceResponse<>(false, "Validate failed", null, null);
        }
    }

    @Override
    public boolean isLogin(String sessionKey, String clientIp) {
        final UserAgent userAgent = cache.get(sessionKey, UserAgent.class);
        if (userAgent == null) {
            return false;
        } else {
            return userAgent.getIp().equals(clientIp);
        }
    }

    @Override
    public boolean logout(String sessionKey) {
        cache.remove(sessionKey);
        return true;
    }
}
