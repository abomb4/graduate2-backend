package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.UserRole;
import org.wlyyy.itrs.request.UserRoleQuery;

import java.util.List;
import java.util.Objects;

/**
 * user_role 用户角色对应表操作
 */
@Mapper
public interface UserRoleRepository {

    /**
     * 根据id查询用户角色对应信息
     *
     * @param id id
     * @return 用户角色对应信息
     */
    @Select("select id, user_id, role_id, gmt_create, gmt_modify from user_role where id = #{id}")
    UserRole findById(@Param("id") Long id);

    /**
     * 新建用户角色对应信息，忽略id、gmtCreate、gmtModify字段
     * @param userRole 用户角色对应信息
     */
    @Insert("insert into user_role(user_id, role_id, gmt_create, gmt_modify) values (" +
            "#{userId}, #{roleId}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(UserRole userRole);

    @SelectProvider(type = UserRoleRepository.UserRoleQueryProvider.class, method = "select")
    List<UserRole> findByCondition(@Param("userRole") UserRoleQuery queryObject, Pageable page);

    @SelectProvider(type = UserRoleRepository.UserRoleQueryProvider.class, method = "count")
    long countByCondition(@Param("userRole") UserRoleQuery queryObject);

    /**
     * 动态查询
     */
    class UserRoleQueryProvider {
        public static String select(@Param("userRole") UserRoleQuery userRole, Pageable page) {
            return new UserRoleRepository.UserRoleQueryProvider().getSelect(userRole, page);
        }
        public static String count(@Param("userRole") UserRoleQuery userRole) {
            return new UserRoleRepository.UserRoleQueryProvider().getCount(userRole);
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
         * @param userRole 用户角色对应查询对象
         * @return sql语句
         */
        private String getCount(UserRoleQuery userRole) {
            if (userRole == null) {
                return "select count(*) from user_role";
            }

            builder.append("select count(*) from user_role");

            packageWhere(userRole);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param userRole 用户角色对应查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(UserRoleQuery userRole, Pageable page) {
            if (userRole == null) {
                return St.r("select {} from user_role {}",
                        "id, user_id, role_id, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, user_id, role_id, gmt_create, gmt_modify from user_role");

            packageWhere(userRole);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(UserRoleQuery userRole) {

            tryAppendWhere(userRole.getId(), "id = #{id}");
            tryAppendWhere(userRole.getUserId(), "user_id = #{userRole.userId}");
            tryAppendWhere(userRole.getRoleId(), "role_id = #{userRole.roleId}");
            tryAppendWhere(userRole.getGmtCreateStart(), "gmt_create >= #{userRole.gmtCreateStart}");
            tryAppendWhere(userRole.getGmtCreateEnd(), "gmt_create <= #{userRole.gmtCreateEnd}");
            tryAppendWhere(userRole.getGmtModifyStart(), "gmt_modify >= #{userRole.gmtModifyStart}");
            tryAppendWhere(userRole.getGmtModifyEnd(), "gmt_modify <= #{userRole.gmtModifyEnd}");
        }

        private String getOrder(Pageable page) {
            final StringBuilder sortBuilder = new StringBuilder();
            if (page.getSort() != null) {
                Sort sort = page.getSort();
                int count = 0;
                for (Sort.Order order : sort) {
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
