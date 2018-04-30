package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.request.RoleQuery;

import java.util.List;
import java.util.Objects;

/**
 * role 角色表
 */
@Mapper
public interface RoleRepository {

    /**
     * 根据id查询角色信息
     *
     * @param id id
     * @return 角色信息
     */
    @Select("select id, role_name, memo, gmt_create, gmt_modify from role where id = #{id}")
    Role findById(@Param("id") Long id);

    /**
     * 新建角色信息，忽略id、gmtCreate、gmtModify字段
     * @param role 角色信息
     */
    @Insert("insert into role(role_name, memo, gmt_create, gmt_modify) values (" +
            "#{roleName}, #{memo}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Role role);

    @SelectProvider(type = RoleRepository.RoleQueryProvider.class, method = "select")
    List<Role> findByCondition(@Param("role") RoleQuery queryObject, Pageable page);

    @SelectProvider(type = RoleRepository.RoleQueryProvider.class, method = "count")
    long countByCondition(@Param("role") RoleQuery queryObject);

    @UpdateProvider(type = RoleRepository.RoleUpdateByIdProvider.class, method = "myMethod")
    int updateById(Role role);

    /**
     * Update动态SQL
     */
    class RoleUpdateByIdProvider {
        public static String myMethod(Role role) {
            return new RoleRepository.RoleUpdateByIdProvider().getUpdate(role);
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

        String getUpdate(Role role) {
            Objects.requireNonNull(role.getId(), "Cannot update when id is null");

            builder.append("update role set ");

            tryAppend(role.getRoleName(), "role_name = #{roleName}");
            tryAppend(role.getMemo(), "memo = #{memo}");

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of update attribute should be not null");
            }

            builder.append(", gmt_modify = now() where id = #{id}");

            return builder.toString();
        }
    }

    /**
     * 动态查询
     */
    class RoleQueryProvider {
        public static String select(@Param("role") RoleQuery role, Pageable page) {
            return new RoleRepository.RoleQueryProvider().getSelect(role, page);
        }
        public static String count(@Param("role") RoleQuery role) {
            return new RoleRepository.RoleQueryProvider().getCount(role);
        }

        static String DELIMITER = " and ";

        boolean first = true;
        final StringBuilder builder = new StringBuilder();

        private void tryAppendWhere(Object o, String forAppend) {
            if (Objects.nonNull(o) && !"".equals(o)) {
                if (first) {
                    builder.append(" where ");
                } else {
                    builder.append(DELIMITER);
                }
                first = false;
                builder.append(forAppend);
            }
        }

        /**
         * 获取查询条件count sql
         *
         * @param role 角色查询对象
         * @return sql语句
         */
        private String getCount(RoleQuery role) {
            if (role == null) {
                return "select count(*) from role";
            }

            builder.append("select count(*) from role");

            packageWhere(role);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param role 角色查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(RoleQuery role, Pageable page) {
            if (role == null) {
                return St.r("select {} from role {}",
                        "id, role_name, memo, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, role_name, memo, gmt_create, gmt_modify from role");

            packageWhere(role);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(RoleQuery role) {
            String roleName = "concat('%', #{role.roleName}, '%')";

            tryAppendWhere(role.getId(), "id = #{id}");
            tryAppendWhere(role.getRoleName(), "role_name like " + roleName);
            tryAppendWhere(role.getMemo(), "memo = #{role.memo}");
            tryAppendWhere(role.getGmtCreateStart(), "gmt_create >= #{role.gmtCreateStart}");
            tryAppendWhere(role.getGmtCreateEnd(), "gmt_create <= #{role.gmtCreateEnd}");
            tryAppendWhere(role.getGmtModifyStart(), "gmt_modify >= #{role.gmtModifyStart}");
            tryAppendWhere(role.getGmtModifyEnd(), "gmt_modify <= #{role.gmtModifyEnd}");
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
