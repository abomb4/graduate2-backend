package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.request.UserQuery;

import java.util.List;
import java.util.Objects;

/**
 * 买票e
 *
 * @author wlyyy
 */
@Mapper
public interface UserRepository {

    /**
     * 根据id查询用户，不包含password
     *
     * @param id id
     * @return 用户对象
     */
    @Select("select id, user_name, email, sex, salt, department_id, real_name, gmt_create, gmt_modify from user where id = #{id}")
    User findById(@Param("id") Long id);

    /**
     * 根据userName查询用户，包含password
     *
     * @param userName 用户名
     * @return 用户对象`
     */
    @Select("select * from user where user_name = #{userName}")
    User findFullByUserName(@Param("userName") String userName);

    @SelectProvider(type = UserQueryProvider.class, method = "select")
    List<User> findByCondition(@Param("user") UserQuery queryObject, Pageable page);

    @SelectProvider(type = UserQueryProvider.class, method = "count")
    long countByCondition(@Param("user") UserQuery queryObject);

    /**
     * 新建用户，忽略id、gmtCreate、gmtModify字段
     * @param user 用户
     */
    @Insert("insert into user(user_name, email, password, salt, sex, department_id, real_name, gmt_create, gmt_modify) values (" +
            "#{userName}, #{email}, #{password}, #{salt}, #{sex}, #{departmentId}, #{realName}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(User user);

    // @Update("update user put user_name = #{userName}, password = #{password}, salt = #{salt}, sex = #{sex}, department_id = #{departmentId}, real_name = #{realName}, gmt_modify = now() where id = #{id}")
    @UpdateProvider(type = UserUpdateByIdProvider.class, method = "myMethod")
    int updateById(User user);

    /**
     * Update动态SQL
     */
    class UserUpdateByIdProvider {

        public static String myMethod(User user) {
            return new UserUpdateByIdProvider().getUpdate(user);
        }

        boolean first = true;
        final StringBuilder builder = new StringBuilder();

        private void tryAppend(Object o, String forAppend) {
            if (Objects.nonNull(o) && !"".equals(o)) {
                if (!first) {
                    builder.append(", ");
                }
                first = false;
                builder.append(forAppend);
            }
        }

        String getUpdate(User user) {
            Objects.requireNonNull(user.getId(), "Cannot update when id is null");

            builder.append("update user set ");

            tryAppend(user.getUserName(), "user_name = #{userName}");
            tryAppend(user.getEmail(), "email = #{email}");
            tryAppend(user.getPassword(), "password = #{password}");
            tryAppend(user.getSalt(), "salt = #{salt}");
            tryAppend(user.getSex(), "sex = #{sex}");
            tryAppend(user.getDepartmentId(), "department_id = #{departmentId}");
            tryAppend(user.getRealName(), "real_name = #{realName}");

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of [userName, email, password, salt, sex, departmentId, realName] should be not null");
            }

            builder.append(", gmt_modify = now() where id = #{id}");

            return builder.toString();
        }
    }

    /**
     * 动态查询。
     */
    class UserQueryProvider {
        public static String select(@Param("user") UserQuery user, Pageable page) {
            return new UserQueryProvider().getSelect(user, page);
        }
        public static String count(@Param("user") UserQuery user) {
            return new UserQueryProvider().getCount(user);
        }

        static String DELIMITER = " and ";

        boolean first = true;
        final StringBuilder builder = new StringBuilder();

        private void tryAppendWhere(Object o, String forAppend) {
            if (Objects.nonNull(o) && !"".equals(o)) {
                if (!first) {
                    builder.append(DELIMITER);
                } else {
                    builder.append(" where ");
                }
                first = false;
                builder.append(forAppend);
            }
        }

        /**
         * 获取查询条件count sql
         *
         * @param user 用户查询对象
         * @return sql语句
         */
        private String getCount(UserQuery user) {
            if (user == null) {
                return "select count(*) from user";
            }

            builder.append("select count(*) from user");

            packageWhere(user);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param user 用户查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(UserQuery user, Pageable page) {
            if (user == null) {
                return St.r("select {} from user {}",
                        "id, user_name, email, sex, salt, department_id, real_name, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, user_name, email, sex, department_id, real_name, gmt_create, gmt_modify from user where ");

            packageWhere(user);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(UserQuery user) {
            String userName = "concat('%', #{user.userName}, '%')";
            String realName = "concat('%', #{user.realName}, '%')";

            tryAppendWhere(user.getId(), "id = #{id}");
            tryAppendWhere(user.getUserName(), "user_name like " + userName);
            tryAppendWhere(user.getEmail(), "email = #{user.email}");
            tryAppendWhere(user.getDepartmentId(), "department_id = #{user.departmentId}");
            tryAppendWhere(user.getRealName(), "real_name like " + realName);
            tryAppendWhere(user.getGmtCreateStart(), "gmt_create >= #{user.gmtCreateStart}");
            tryAppendWhere(user.getGmtCreateEnd(), "gmt_create <= #{user.gmtCreateEnd}");
            tryAppendWhere(user.getGmtModifyStart(), "gmt_modify >= #{user.gmtModifyStart}");
            tryAppendWhere(user.getGmtModifyEnd(), "gmt_modify <= #{user.gmtModifyEnd}");
        }

        private String getOrder(Pageable page) {
            final StringBuilder sortBuilder =  new StringBuilder();
            if (page.getSort() != null) {
                Sort sort = page.getSort();
                int count = 0;
                for (Sort.Order order: sort) {
                    if (count == 0) {
                        // 第一个order
                        sortBuilder.append(" order by ");
                        sortBuilder.append(order.getProperty()).append(" ").append(order.getDirection());
                    } else {
                        sortBuilder.append(", ").append(order.getProperty()).append(" ").append(order.getDirection());
                    }
                    count++;
                }
            }
            return sortBuilder.toString();
        }

        private String getPage(Pageable page) {
            return St.r("limit {}, {}", page.getOffset(), page.getPageSize());
        }
    }

}
