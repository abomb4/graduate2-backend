package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.PageableUtils;
import org.wlyyy.itrs.dao.UserRepository;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.request.UserQuery;
import org.wlyyy.itrs.utils.SecurityUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository dao;

    @Override
    public BaseServicePageableResponse<User> findByCondition(BaseServicePageableRequest<UserQuery> request) {

        final Pageable pageable = PageableUtils.getPageable(request);
        final List<User> queryResult = dao.findByCondition(request.getData(), pageable);

        final long count;
        if (request.getPageNo() == 1 && (queryResult.size() < request.getPageSize())) {
            count = queryResult.size();
        } else {
            count = dao.countByCondition(request.getData());
        }

        return new BaseServicePageableResponse<>(
                true, "Query success", queryResult,
                request.getPageNo(), request.getPageSize(), count
        );
    }

    @Override
    public User findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public BaseServiceResponse<User> validateUser(String userName, String password) {
        final User fullUserInfo = dao.findFullByUserName(userName);
        if (fullUserInfo == null) {
            return new BaseServiceResponse<>(false, "User not found.", null, null);
        }

        final String realPassword = fullUserInfo.getPassword();
        final String salt = fullUserInfo.getSalt();

        final String encrypt = SecurityUtils.encrypyPassword(password, salt);

        // 将用户对象的密码和盐置为null，避免泄露密码和盐
        fullUserInfo.setPassword(null);
        fullUserInfo.setSalt(null);

        if (encrypt.equals(realPassword)) {
            return new BaseServiceResponse<>(true, "Validate successful.", fullUserInfo, null);
        } else {
            return new BaseServiceResponse<>(false, "Password incorrect.", null, null);
        }
    }

    @Override
    public BaseServiceResponse<User> createUser(User user) {
        user.setSalt(SecurityUtils.generateSalt());
        final String encrypyPassword = SecurityUtils.encrypyPassword(user.getPassword(), user.getSalt());
        user.setPassword(encrypyPassword);
        dao.insert(user);
        return new BaseServiceResponse<>(true, "Create user successfully.", null, null);
    }

    @Override
    @Transactional
    public BaseServiceResponse<User> modifyPassword(String oldPassword, String newPassword, String userName) {
        final User fullUserInfo = dao.findFullByUserName(userName);
        if (fullUserInfo == null) {
            return new BaseServiceResponse<>(false, "User not found.", null, null);
        }

        final String realPassword = fullUserInfo.getPassword();
        final String salt = fullUserInfo.getSalt();
        final String encrypt = SecurityUtils.encrypyPassword(oldPassword, salt);

        if (encrypt.equals(realPassword)) {
            // 旧密码正确，更新密码
            final String modifyEncrypt = SecurityUtils.encrypyPassword(newPassword, salt);
            fullUserInfo.setPassword(modifyEncrypt);
            dao.updateById(fullUserInfo);

            // 将用户对象的密码和盐置为null，避免泄露密码和盐
            fullUserInfo.setPassword(null);
            fullUserInfo.setSalt(null);
            return new BaseServiceResponse<>(true, "Modify password successful.", fullUserInfo, null);
        } else {
            return new BaseServiceResponse<>(false, "Old password incorrect.", null, null);
        }
    }

    @Override
    public BaseServiceResponse<Integer> modifyUser(User user) {
        user.setPassword(null);
        user.setSalt(null);
        final int i = dao.updateById(user);
        return new BaseServiceResponse<>(true, "Update sucessful.", i, null);
    }
}
