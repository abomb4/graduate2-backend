package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.itrs.request.DemandQuery;

import java.util.*;
import java.util.function.Supplier;

/**
 * 增删查改招聘需求表
 *
 * @author wly
 */
@Mapper
public interface DemandRepository {

    /**
     * 根据id查询招聘需求
     *
     * @param id id
     * @return 招聘需求
     */
    @Select("select * from demand where id = #{id}")
    Demand findById(@Param("id") Long id);

    /**
     * 根据招聘需求No查询招聘需求
     *
     * @param demandNo 招聘需求No
     * @return 招聘需求
     */
    @Select("select * from demand where demand_no = #{demandNo}")
    Demand findByNo(String demandNo);

    /**
     * 调用DemandQueryProvider类中的select方法的返回值，拼接成sql查询语句
     *
     * @param queryObject demandQuery查询对象
     * @param page 分页请求对象
     * @return 查询结果
     */
    @SelectProvider(type = DemandQueryProvider.class, method = "select")
    List<Demand> findByCondition(@Param("demand") DemandQuery queryObject, Pageable page);

    @SelectProvider(type = DemandQueryProvider.class, method = "count")
    long countByCondition(@Param("demand") DemandQuery queryObject);

    /**
     * 新建招聘需求，忽略id、gmtCreate、gmtModify字段
     * @param demand 招聘需求
     */
    @Insert("insert into demand (demand_no, publisher_id, position_type, job_name, department_id, hr_name, total, working_place, degree_request, status, memo, proc_key, gmt_create, gmt_modify) values (" +
            "#{demandNo}, #{publisherId}, #{positionType}, #{jobName}, #{departmentId}, #{hrName}, #{total}, #{workingPlace}, #{degreeRequest}, 1, #{memo}, #{procKey}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Demand demand);

    @UpdateProvider(type = DemandUpdateByIdProvider.class, method = "updateMethod")
    int updateById(Demand demand);

    /**
     * Update动态SQL
     */
    class DemandUpdateByIdProvider {

        public static String updateMethod(Demand demand) {
            return new DemandRepository.DemandUpdateByIdProvider().getUpdate(demand);
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

        String getUpdate(Demand demand) {
            Objects.requireNonNull(demand.getId(), "Cannot update when id is null");

            builder.append("update demand set ");

            // 需求编号、发布用户id、招聘部门id等是否可以?
            tryAppend(demand.getDemandNo(), "demand_no = #{demandNo}");
            tryAppend(demand.getPublisherId(), "publisher_id = #{publisherId}");
            tryAppend(demand.getPositionType(), "position_type = #{positionType}");
            tryAppend(demand.getJobName(), "job_name = #{jobName}");
            tryAppend(demand.getDepartmentId(), "department_id = #{departmentId}");
            tryAppend(demand.getHrName(), "hr_name = #{hrName}");
            tryAppend(demand.getTotal(), "total = #{total}");
            tryAppend(demand.getWorkingPlace(), "working_place = #{workingPlace}");
            tryAppend(demand.getDegreeRequest(), "degree_request = #{degreeRequest}");
            tryAppend(demand.getStatus(), "status = #{status}");
            tryAppend(demand.getMemo(), "memo = #{memo}");
            tryAppend(demand.getProcKey(), "proc_key = #{procKey}");

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of Update Condition should be not null");
            }

            builder.append(", gmt_modify = now() where id = #{id}");

            return builder.toString();
        }
    }


    /**
     * 动态查询
     */
    class DemandQueryProvider {
        public static String select(@Param("demand") DemandQuery demand, Pageable page) {
            return new DemandRepository.DemandQueryProvider().getSelect(demand, page);
        }
        public static String count(@Param("demand") DemandQuery demand) {
            return new DemandRepository.DemandQueryProvider().getCount(demand);
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

        private void tryAppendWhere(Object o, Supplier<String> forAppend) {
            if (Objects.nonNull(o) && !"".equals(o)) {
                if (!first) {
                    builder.append(DELIMITER);
                } else {
                    builder.append(" where ");
                }
                first = false;
                builder.append(forAppend.get());
            }
        }

        /**
         * 获取查询条件count sql
         *
         * @param demand 招聘需求查询对象
         * @return sql语句
         */
        private String getCount(DemandQuery demand) {
            if (demand == null) {
                return "select count(*) from demand";
            }

            builder.append("select count(*) from demand");

            packageWhere(demand);

            // 不能全都是空
//            if (first) {
//                throw new IllegalArgumentException("One of query condition should be not null");
//            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param demand 招聘需求查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(DemandQuery demand, Pageable page) {
            if (demand == null) {
                return St.r("select {} from demand{} {}",
                        "id, demand_no, publisher_id, position_type, job_name, department_id, hr_name, total, working_place, degree_request, status, memo, proc_key, gmt_create, gmt_modify",
                        getOrder(page), getPage(page)
                );
            }
            builder.append("select id, demand_no, publisher_id, position_type, job_name, department_id, hr_name, total, working_place, degree_request, status, memo, proc_key, gmt_create, gmt_modify from demand");

            packageWhere(demand);

            // 不能全都是空
//            if (first) {
//                throw new IllegalArgumentException("One of query condition should be not null");
//            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(DemandQuery demand) {
            String hrName = "concat('%', #{demand.hrName}, '%')";
            String workingPlace = "concat('%', #{demand.workingPlace}, '%')";

            tryAppendWhere(demand.getId(), "id = #{demand.id}");
            tryAppendWhere(demand.getDemandNo(), "demand_no = #{demand.demandNo}");
            tryAppendWhere(demand.getPublisherId(), "publisher_id = #{demand.publisherId}");

            tryAppendWhere(demand.getPositionType(), () -> {
                final Collection<Long> positionType = demand.getPositionType();
                final Iterator<Long> iterator = positionType.iterator();

                if (positionType.isEmpty()) {
                    return "";
                } else if (positionType.size() > 1) {
                    final StringJoiner joiner = new StringJoiner(", ");
                    while(iterator.hasNext()) {
                        final Long id = iterator.next();
                        joiner.add(String.valueOf(id));
                    }
                    return St.r("position_type in ({})", joiner.toString());
                } else {
                    return St.r("position_type = {}", iterator.next());
                }
            });

            tryAppendWhere(demand.getJobName(), "job_name = #{demand.jobName}");
            tryAppendWhere(demand.getDepartmentId(), "department_id = #{demand.departmentId}");
            tryAppendWhere(demand.getHrName(), "hr_name like " + hrName);
            tryAppendWhere(demand.getTotalStart(), "total >= #{demand.totalStart}");
            tryAppendWhere(demand.getTotalEnd(), "total <= #{demand.totalEnd}");
            tryAppendWhere(demand.getWorkingPlace(), "working_place like " + workingPlace);
            tryAppendWhere(demand.getDegreeRequest(), "degree_request = #{demand.degreeRequest}");
            tryAppendWhere(demand.getStatus(), "status = #{demand.status}");
            tryAppendWhere(demand.getMemo(), "memo = #{demand.memo}");
            tryAppendWhere(demand.getProcKey(), "proc_key = #{demand.procKey}");
            tryAppendWhere(demand.getGmtCreateStart(), "gmt_create >= #{demand.gmtCreateStart}");
            tryAppendWhere(demand.getGmtCreateEnd(), "gmt_create <= #{demand.gmtCreateEnd}");
            tryAppendWhere(demand.getGmtModifyStart(), "gmt_modify >= #{demand.gmtModifyStart}");
            tryAppendWhere(demand.getGmtModifyEnd(), "gmt_modify <= #{demand.gmtModifyEnd}");
        }

        private String getOrder(Pageable page) {
            final StringBuilder sortBuilder =  new StringBuilder();
            if (page.getSort() != null) {
                Sort sort = page.getSort();
                int count = 0;
                for (Order order: sort) {
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
