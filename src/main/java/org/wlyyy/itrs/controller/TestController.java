package org.wlyyy.itrs.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.itrs.dao.UserRepository;
import org.wlyyy.itrs.domain.PositionType;
import org.wlyyy.itrs.domain.User;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserRepository dao;

    @RequestMapping("positions")
    public BaseRestResponse<List<PositionType>> getJobs() {
        final AtomicLong ids = new AtomicLong(1);
        final LinkedList<PositionType> jobs = new LinkedList<>();
        final PositionType type1  = new PositionType(ids.getAndIncrement(), "Product", "产品");
        final PositionType type2  = new PositionType(ids.getAndIncrement(), "R&D", "研发");
        final PositionType type3  = new PositionType(ids.getAndIncrement(), "Testing", "测试");
        final PositionType type4  = new PositionType(ids.getAndIncrement(), "Service", "客服");
        final PositionType type5  = new PositionType(ids.getAndIncrement(), "Design", "设计");
        final PositionType type6  = new PositionType(ids.getAndIncrement(), "Operation", "运营");
        final PositionType type7  = new PositionType(ids.getAndIncrement(), "Pre-sales", "售前");
        final PositionType type8  = new PositionType(ids.getAndIncrement(), "Marketing", "营销");
        final PositionType type9  = new PositionType(ids.getAndIncrement(), "Finance", "金融");
        final PositionType type10 = new PositionType(ids.getAndIncrement(), "Data", "数据");
        final PositionType type11 = new PositionType(ids.getAndIncrement(), "Function", "职能");
        final PositionType type12 = new PositionType(ids.getAndIncrement(), "Management", "管理");

        final PositionType type13  = new PositionType(ids.getAndIncrement(), "Analyser", "需求分析");
        final PositionType type14  = new PositionType(ids.getAndIncrement(), "PM", "产品经理");
        type1.getSubTypes().add(type13);
        type1.getSubTypes().add(type14);

        final PositionType type15  = new PositionType(ids.getAndIncrement(), "Java", "Java");
        final PositionType type16  = new PositionType(ids.getAndIncrement(), "C++", "C++");
        final PositionType type17  = new PositionType(ids.getAndIncrement(), "C#", "C#");
        final PositionType type18  = new PositionType(ids.getAndIncrement(), "Delphi", "Delphi");
        final PositionType type19  = new PositionType(ids.getAndIncrement(), "Python", "Python");
        final PositionType type20  = new PositionType(ids.getAndIncrement(), "Database", "数据库");
        final PositionType type21  = new PositionType(ids.getAndIncrement(), "Frontend", "前端");

        type2.getSubTypes().add(type15);
        type2.getSubTypes().add(type16);
        type2.getSubTypes().add(type17);
        type2.getSubTypes().add(type18);
        type2.getSubTypes().add(type19);
        type2.getSubTypes().add(type20);
        type2.getSubTypes().add(type21);

        final PositionType type22  = new PositionType(ids.getAndIncrement(), "Testing", "测试");
        final PositionType type23  = new PositionType(ids.getAndIncrement(), "Test&Dev", "测试开发");

        type3.getSubTypes().add(type22);
        type3.getSubTypes().add(type23);

        final PositionType type24  = new PositionType(ids.getAndIncrement(), "Implement", "实施");
        final PositionType type25  = new PositionType(ids.getAndIncrement(), "Maintain", "维护");
        final PositionType type26  = new PositionType(ids.getAndIncrement(), "Operation", "运维");
        final PositionType type27  = new PositionType(ids.getAndIncrement(), "PM", "项目管理");

        type4.getSubTypes().add(type24);
        type4.getSubTypes().add(type25);
        type4.getSubTypes().add(type26);
        type4.getSubTypes().add(type27);

        final PositionType type28  = new PositionType(ids.getAndIncrement(), "UI", "UI");
        final PositionType type29  = new PositionType(ids.getAndIncrement(), "UED", "UED");
        final PositionType type30  = new PositionType(ids.getAndIncrement(), "Design", "平面设计 Flatten");

        type5.getSubTypes().add(type28);
        type5.getSubTypes().add(type29);
        type5.getSubTypes().add(type30);

        jobs.add(type1);
        jobs.add(type2);
        jobs.add(type3);
        jobs.add(type4);
        jobs.add(type5);
        jobs.add(type6);
        jobs.add(type7);
        jobs.add(type8);
        jobs.add(type9);
        jobs.add(type10);
        jobs.add(type11);
        jobs.add(type12);

        return new BaseRestResponse<List<PositionType>>(true, "Query success", jobs);
    }

    @RequestMapping("test")
    public Map<String, String> xx() {
        final Map<String, String> map = new HashMap<>();
        LOG.info("■■■■■■■■■■ get方法 ■■■■■■■■■■■■");
        map.put("get", new Gson().toJson(dao.findById(1L)));
        LOG.info("■■■■■■■■■■ insert方法 ■■■■■■■■■■■■");
        User user = new User();

        user.setId(1L);
        user.setUserName("userName");
        user.setEmail("wlyyy@163.com");
        user.setPassword("password");
        user.setSalt("salt");
        user.setSex(1);
        user.setDepartmentId(1L);
        user.setRealName("realName");

        dao.insert(user);
        map.put("insert", "INSERT SUCCESS");

        LOG.info("■■■■■■■■■■ modify方法 ■■■■■■■■■■■■");
        final int affected = dao.updateById(user);
        map.put("update", "UPDATE MAY SUCCESS " + affected);

        return map;
    }

    @RequestMapping("user/{id}")
    @ResponseBody
    public GetUserResponse getUser(@PathVariable("id") String id) {
        return new GetUserResponse(true, "Get success", dao.findById(Long.parseLong(id)));
    }

    /**
     * 页面层User返回对象
     */
    static class UserResponse {
        private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        public static UserResponse buildByUser(User user) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            return new UserResponse(user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getSex(),
                    user.getDepartmentId(),
                    user.getRealName(),
                    simpleDateFormat.format(user.getGmtCreate()),
                    simpleDateFormat.format(user.getGmtModify())
            );
        }

        private final Long id;
        private final String userName;
        private final String email;

        private final Integer sex;
        private final Long departmentId;
        private final String realName;
        private final String gmtCreate;
        private final String gmtModify;

        public UserResponse(Long id, String userName, String email, Integer sex, Long departmentId, String realName, String gmtCreate, String gmtModify) {
            this.id = id;
            this.userName = userName;
            this.email = email;
            this.sex = sex;
            this.departmentId = departmentId;
            this.realName = realName;
            this.gmtCreate = gmtCreate;
            this.gmtModify = gmtModify;
        }

        public Long getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public Integer getSex() {
            return sex;
        }

        public Long getDepartmentId() {
            return departmentId;
        }

        public String getRealName() {
            return realName;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public String getGmtModify() {
            return gmtModify;
        }
    }

    static class GetUserResponse extends BaseRestResponse<UserResponse> {

        public GetUserResponse(boolean success, String message, User data) {
            super(success, message, UserResponse.buildByUser(data));
        }
    }



}
