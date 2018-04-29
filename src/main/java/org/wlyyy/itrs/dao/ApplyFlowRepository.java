package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.ApplyFlow;
import org.wlyyy.itrs.request.ApplyFlowQuery;

import java.util.List;
import java.util.Objects;

/**
 * apply_flow 招聘流程信息表操作
 */
@Mapper
public interface ApplyFlowRepository {

    /**
     * 根据id查询招聘流程信息
     *
     * @param id id
     * @return 招聘流程信息
     */
    @Select("select id, demand_no, candidate_id, user_id, current_flow_node, current_dealer, flow_status, gmt_create, gmt_modify from apply_flow where id = #{id}")
    ApplyFlow findById(@Param("id") Long id);

    /**
     * 新建招聘流程信息，忽略id、gmtCreate、gmtModify字段
     * @param applyFlow 招聘流程信息
     */
    @Insert("insert into apply_flow(demand_no, candidate_id, user_id, current_flow_node, current_dealer, flow_status, gmt_create, gmt_modify) values (" +
            "#{demandNo}, #{candidateId}, #{userId}, #{currentFlowNode}, #{currentDealer}, #{flowStatus}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(ApplyFlow applyFlow);

    @SelectProvider(type = ApplyFlowQueryProvider.class, method = "select")
    List<ApplyFlow> findByCondition(@Param("applyFlow") ApplyFlowQuery queryObject, Pageable page);

    @SelectProvider(type = ApplyFlowQueryProvider.class, method = "count")
    long countByCondition(@Param("applyFlow") ApplyFlowQuery queryObject);

    @UpdateProvider(type = ApplyFlowUpdateByIdProvider.class, method = "myMethod")
    int updateById(ApplyFlow applyFlow);

    /**
     * Update动态SQL
     */
    class ApplyFlowUpdateByIdProvider {
        public static String myMethod(ApplyFlow applyFlow) {
            return new ApplyFlowUpdateByIdProvider().getUpdate(applyFlow);
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

        String getUpdate(ApplyFlow applyFlow) {
            Objects.requireNonNull(applyFlow.getId(), "Cannot update when id is null");

            builder.append("update apply_flow set ");

            tryAppend(applyFlow.getDemandNo(), "demand_no = #{demandNo}");
            tryAppend(applyFlow.getCandidateId(), "candidate_id = #{candidateId}");
            tryAppend(applyFlow.getUserId(), "user_id = #{userId}");
            tryAppend(applyFlow.getCurrentFlowNode(), "current_flow_node = #{currentFlowNode}");
            tryAppend(applyFlow.getCurrentDealer(), "current_dealer = #{currentDealer}");
            tryAppend(applyFlow.getFlowStatus(), "flow_status = #{flowStatus}");

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
    class ApplyFlowQueryProvider {
        public static String select(@Param("applyFlow") ApplyFlowQuery applyFlow, Pageable page) {
            return new ApplyFlowQueryProvider().getSelect(applyFlow, page);
        }
        public static String count(@Param("applyFlow") ApplyFlowQuery applyFlow) {
            return new ApplyFlowQueryProvider().getCount(applyFlow);
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
         * @param applyFlow 招聘流程查询对象
         * @return sql语句
         */
        private String getCount(ApplyFlowQuery applyFlow) {
            if (applyFlow == null) {
                return "select count(*) from apply_flow";
            }

            builder.append("select count(*) from apply_flow where ");

            packageWhere(applyFlow);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param applyFlow 招聘流程查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(ApplyFlowQuery applyFlow, Pageable page) {
            if (applyFlow == null) {
                return St.r("select {} from apply_flow {}",
                        "id, demand_no, candidate_id, user_id, current_flow_node, current_dealer, flow_status, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, demand_no, candidate_id, user_id, current_flow_node, current_dealer, flow_status, gmt_create, gmt_modify from apply_flow");

            packageWhere(applyFlow);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(ApplyFlowQuery applyFlow) {
            String currentFlowNode = "concat('%', #{applyFlow.currentFlowNode}, '%')";

            tryAppendWhere(applyFlow.getId(), "id = #{id}");
            tryAppendWhere(applyFlow.getDemandNo(), "demand_no = #{applyFlow.demandNo}");
            tryAppendWhere(applyFlow.getCandidateId(), "candidate_id = #{applyFlow.candidateId}");
            tryAppendWhere(applyFlow.getUserId(), "user_id = #{applyFlow.userId}");
            tryAppendWhere(applyFlow.getCurrentFlowNode(), "current_flow_node like " + currentFlowNode);
            tryAppendWhere(applyFlow.getCurrentDealer(), "current_dealer = #{applyFlow.currentDealer}");
            tryAppendWhere(applyFlow.getFlowStatus(), "flow_status = #{applyFlow.flowStatus}");
            tryAppendWhere(applyFlow.getGmtCreateStart(), "gmt_create >= #{applyFlow.gmtCreateStart}");
            tryAppendWhere(applyFlow.getGmtCreateEnd(), "gmt_create <= #{applyFlow.gmtCreateEnd}");
            tryAppendWhere(applyFlow.getGmtModifyStart(), "gmt_modify >= #{applyFlow.gmtModifyStart}");
            tryAppendWhere(applyFlow.getGmtModifyEnd(), "gmt_modify <= #{applyFlow.gmtModifyEnd}");
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
