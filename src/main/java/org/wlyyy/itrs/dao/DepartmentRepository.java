package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.Department;
import org.wlyyy.itrs.request.DepartmentQuery;

import java.util.List;
import java.util.Objects;

/**
 * department 组织机构表操作
 */
@Mapper
public interface DepartmentRepository {

    /**
     * 根据id查询组织机构表
     *
     * @param id id
     * @return 组织机构表
     */
    @Select("select id, department_name, parent_id, gmt_create, gmt_modify from department where id = #{id}")
    Department findById(@Param("id") Long id);

    /**
     * 新建组织机构表，忽略id、gmtCreate、gmtModify字段
     * @param department 组织机构表
     */
    @Insert("insert into department(department_name, parent_id, gmt_create, gmt_modify) values (" +
            "#{departmentName}, #{parentId}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Department department);

    @SelectProvider(type = DepartmentRepository.DepartmentQueryProvider.class, method = "select")
    List<Department> findByCondition(@Param("department") DepartmentQuery queryObject, Pageable page);

    @SelectProvider(type = DepartmentRepository.DepartmentQueryProvider.class, method = "count")
    long countByCondition(@Param("department") DepartmentQuery queryObject);

    @UpdateProvider(type = DepartmentRepository.DepartmentUpdateByIdProvider.class, method = "myMethod")
    int updateById(Department department);

    /**
     * Update动态SQL
     */
    class DepartmentUpdateByIdProvider {
        public static String myMethod(Department department) {
            return new DepartmentRepository.DepartmentUpdateByIdProvider().getUpdate(department);
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

        String getUpdate(Department department) {
            Objects.requireNonNull(department.getId(), "Cannot update when id is null");

            builder.append("update department set ");
            
            tryAppend(department.getDepartmentName(), "department_name = #{departmentName}");
            tryAppend(department.getParentId(), "parent_id = #{parentId}");

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
    class DepartmentQueryProvider {
        public static String select(@Param("department") DepartmentQuery department, Pageable page) {
            return new DepartmentRepository.DepartmentQueryProvider().getSelect(department, page);
        }
        public static String count(@Param("department") DepartmentQuery department) {
            return new DepartmentRepository.DepartmentQueryProvider().getCount(department);
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
         * @param department 组织机构查询对象
         * @return sql语句
         */
        private String getCount(DepartmentQuery department) {
            if (department == null) {
                return "select count(*) from department";
            }

            builder.append("select count(*) from department where ");

            packageWhere(department);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param department 组织机构查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(DepartmentQuery department, Pageable page) {
            if (department == null) {
                return St.r("select {} from department {}",
                        "id, department_name, parent_id, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, department_name, parent_id, gmt_create, gmt_modify from department");

            packageWhere(department);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(DepartmentQuery department) {
            String departmentName = "concat('%', #{department.departmentName}, '%')";

            tryAppendWhere(department.getId(), "id = #{id}");
            tryAppendWhere(department.getDepartmentName(), "department_name like " + departmentName);
            tryAppendWhere(department.getParentId(), "parent_id = #{parentId}");
            tryAppendWhere(department.getGmtCreateEnd(), "gmt_create <= #{department.gmtCreateEnd}");
            tryAppendWhere(department.getGmtModifyStart(), "gmt_modify >= #{department.gmtModifyStart}");
            tryAppendWhere(department.getGmtModifyEnd(), "gmt_modify <= #{department.gmtModifyEnd}");
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
