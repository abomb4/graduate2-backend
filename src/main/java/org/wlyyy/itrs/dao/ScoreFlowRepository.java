package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.ScoreFlow;
import org.wlyyy.itrs.request.ScoreFlowQuery;

import java.util.List;
import java.util.Objects;

@Mapper
public interface ScoreFlowRepository {

    /**
     * 根据id查询积分流水变动记录
     *
     * @param id id
     * @return 积分流水变动记录
     */
    @Select("select id, user_id, score, memo, gmt_create, gmt_modify from score_flow where id = #{id}")
    ScoreFlow findById(@Param("id") Long id);

    /**
     * 新建积分流水变动记录，忽略id、gmtCreate、gmtModify字段
     * @param scoreFlow 积分流水变动记录
     */
    @Insert("insert into score_flow(user_id, score, memo, gmt_create, gmt_modify) values (" +
            "#{userId}, #{score}, #{memo}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(ScoreFlow scoreFlow);

    @SelectProvider(type = ScoreFlowQueryProvider.class, method = "select")
    List<ScoreFlow> findByCondition(@Param("scoreFlow") ScoreFlowQuery queryObject, Pageable page);

    @SelectProvider(type = ScoreFlowQueryProvider.class, method = "count")
    long countByCondition(@Param("scoreFlow") ScoreFlowQuery queryObject);

    @UpdateProvider(type = ScoreFlowUpdateByIdProvider.class, method = "myMethod")
    int updateById(ScoreFlow scoreFlow);

    /**
     * Update动态SQL
     */
    class ScoreFlowUpdateByIdProvider {
        public static String myMethod(ScoreFlow scoreFlow) {
            return new ScoreFlowRepository.ScoreFlowUpdateByIdProvider().getUpdate(scoreFlow);
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

        String getUpdate(ScoreFlow scoreFlow) {
            Objects.requireNonNull(scoreFlow.getId(), "Cannot update when id is null");

            builder.append("update score_flow set ");

            tryAppend(scoreFlow.getUserId(), "user_id = #{userId}");
            tryAppend(scoreFlow.getScore(), "score = #{score}");
            tryAppend(scoreFlow.getMemo(), "memo = #{memo}");

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
    class ScoreFlowQueryProvider {
        public static String select(@Param("scoreFlow") ScoreFlowQuery scoreFlow, Pageable page) {
            return new ScoreFlowRepository.ScoreFlowQueryProvider().getSelect(scoreFlow, page);
        }
        public static String count(@Param("scoreFlow") ScoreFlowQuery scoreFlow) {
            return new ScoreFlowRepository.ScoreFlowQueryProvider().getCount(scoreFlow);
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
         * @param scoreFlow 积分流水变动记录查询对象
         * @return sql语句
         */
        private String getCount(ScoreFlowQuery scoreFlow) {
            if (scoreFlow == null) {
                return "select count(*) from score_flow";
            }

            builder.append("select count(*) from score_flow");

            packageWhere(scoreFlow);

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param scoreFlow 积分流水变动记录查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(ScoreFlowQuery scoreFlow, Pageable page) {
            if (scoreFlow == null) {
                return St.r("select {} from score_flow {}",
                        "id, user_id, score, memo, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, user_id, score, memo, gmt_create, gmt_modify from score_flow");

            packageWhere(scoreFlow);

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(ScoreFlowQuery scoreFlow) {

            tryAppendWhere(scoreFlow.getId(), "id = #{id}");
            tryAppendWhere(scoreFlow.getUserId(), "user_id = #{scoreFlow.userId}");
            tryAppendWhere(scoreFlow.getScore(), "score = #{scoreFlow.score}");
            tryAppendWhere(scoreFlow.getGmtCreateStart(), "gmt_create >= #{scoreFlow.gmtCreateStart}");
            tryAppendWhere(scoreFlow.getGmtCreateEnd(), "gmt_create <= #{scoreFlow.gmtCreateEnd}");
            tryAppendWhere(scoreFlow.getGmtModifyStart(), "gmt_modify >= #{scoreFlow.gmtModifyStart}");
            tryAppendWhere(scoreFlow.getGmtModifyEnd(), "gmt_modify <= #{scoreFlow.gmtModifyEnd}");
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
