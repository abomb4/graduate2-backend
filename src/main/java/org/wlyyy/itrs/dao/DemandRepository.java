package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.wlyyy.itrs.domain.Demand;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.request.DemandQuery;

import java.util.List;
import java.util.Objects;

/**
 * 增删查改招聘需求表
 *
 * @author wly
 */
@Mapper
public interface DemandRepository {

    @Select("select * from demand where id = #{id}")
    Demand getById(Long id);

    @SelectProvider(type = DemandQueryProvider.class, method = "selectDemand")
    List<Demand> findByCondition(@Param("demand") DemandQuery queryObject, Pageable page);

    /**
     * 新建招聘需求，忽略id、gmtCreate、gmtModify字段
     * @param demand 招聘需求
     */
    @Insert("insert into demand (demand_no, publisher_id, position_type, position, department_id, hr_name, total, working_place, degree_request, status, memo, gmt_create, gmt_modify) values (" +
            "#{demandNo}, #{publisherId}, #{positionType}, #{position}, #{departmentId}, #{hrName}, #{total}, #{workingPlace}, #{degreeRequest}, 1, #{memo}, now(), now())")
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
            tryAppend(demand.getPosition(), "position = #{position}");
            tryAppend(demand.getDepartmentId(), "department_id = #{departmentId}");
            tryAppend(demand.getHrName(), "hr_name = #{hrName}");
            tryAppend(demand.getTotal(), "total = #{total}");
            tryAppend(demand.getWorkingPlace(), "working_place = #{workingPlace}");
            tryAppend(demand.getDegreeRequest(), "degree_request = #{degreeRequest}");
            tryAppend(demand.getStatus(), "status = #{status}");
            tryAppend(demand.getMemo(), "memo = #{memo}");

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
        public static String selectDemand(@Param("demand") DemandQuery demand, Pageable page) {
            return new DemandRepository.DemandQueryProvider().getSelect(demand, page);
        }

        static String DELIMITER = " and ";

        boolean first = true;
        final StringBuilder builder = new StringBuilder();

        private void tryAppend(Object o, String forAppend) {
            if (Objects.nonNull(o) && !"".equals(o)) {
                if (!first) {
                    builder.append(DELIMITER);
                }
                first = false;
                builder.append(forAppend);
            }
        }

        private String getSelect(DemandQuery demand, Pageable page) {
            if (demand == null) {
                return St.r("select {} from demand {}",
                        "id, demand_no, publisher_id, position_type, position, department_id, hr_name, total, working_place, degree_request, status, memo, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, demand_no, publisher_id, position_type, position, department_id, hr_name, total, working_place, degree_request, status, memo, gmt_create, gmt_modify from demand where ");

            String hrName = "concat('%', #{demand.hrName}, '%')";
            String workingPlace = "concat('%', #{demand.workingPlace}, '%')";

            tryAppend(demand.getId(), "id = #{demand.id}");
            tryAppend(demand.getDemandNo(), "demand_no = #{demand.demandNo}");
            tryAppend(demand.getPublisherId(), "publisher_id = #{demand.publisherId}");
            tryAppend(demand.getPositionType(), "position_type = #{demand.positionType}");
            tryAppend(demand.getPosition(), "position = #{demand.position}");
            tryAppend(demand.getDepartmentId(), "department_id = #{demand.departmentId}");
            tryAppend(demand.getHrName(), "hr_name like " + hrName);
            tryAppend(demand.getTotalStart(), "total >= #{demand.totalStart}");
            tryAppend(demand.getTotalEnd(), "total <= #{demdemand.totalEnd}");
            tryAppend(demand.getWorkingPlace(), "working_place like " + workingPlace);
            tryAppend(demand.getDegreeRequest(), "degree_request = #{demand.degreeRequest}");
            tryAppend(demand.getStatus(), "status = #{demand.status}");
            tryAppend(demand.getMemo(), "memo = #{demand.memo}");
            tryAppend(demand.getGmtCreateStart(), "gmt_create >= #{demand.gmtCreateStart}");
            tryAppend(demand.getGmtCreateEnd(), "gmt_create <= #{demdemand.and.gmtCreateEnd}");
            tryAppend(demand.getGmtModifyStart(), "gmt_modify >= #{demand.gmtModifyStart}");
            tryAppend(demand.getGmtModifyEnd(), "gmt_modify <= #{demand.gmtModifyEnd}");


            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private String getPage(Pageable page) {
            return St.r("limit {}, {}", page.getOffset(), page.getPageSize());
        }
    }
}
