package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.ScoreRule;
import org.wlyyy.itrs.request.ScoreRuleQuery;

import java.util.List;
import java.util.Objects;

@Mapper
public interface ScoreRuleRepository {

    /**
     * 根据id查询积分规则
     *
     * @param id id
     * @return 积分规则
     */
    @Select("select id, rule, score, gmt_create, gmt_modify from score_rule where id = #{id}")
    ScoreRule findById(@Param("id") Long id);

    /**
     * 根据规则查询相应积分
     *
     * @param rule 规则
     * @return 积分
     */
    @Select("select score from score_rule where rule = #{rule}")
    Integer findScoreByRule(@Param("rule") String rule);

    /**
     * 新建积分规则，忽略id、gmtCreate、gmtModify字段
     * @param scoreRule 积分规则
     */
    @Insert("insert into score_rule(rule, score, gmt_create, gmt_modify) values (" +
            "#{rule}, #{score}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(ScoreRule scoreRule);

    @SelectProvider(type = ScoreRuleQueryProvider.class, method = "select")
    List<ScoreRule> findByCondition(@Param("scoreRule") ScoreRuleQuery queryObject, Pageable page);

    @SelectProvider(type = ScoreRuleQueryProvider.class, method = "count")
    long countByCondition(@Param("scoreRule") ScoreRuleQuery queryObject);

    @UpdateProvider(type = ScoreRuleUpdateByIdProvider.class, method = "myMethod")
    int updateById(ScoreRule scoreRule);

    /**
     * Update动态SQL
     */
    class ScoreRuleUpdateByIdProvider {
        public static String myMethod(ScoreRule scoreRule) {
            return new ScoreRuleRepository.ScoreRuleUpdateByIdProvider().getUpdate(scoreRule);
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

        String getUpdate(ScoreRule scoreRule) {
            Objects.requireNonNull(scoreRule.getId(), "Cannot update when id is null");

            builder.append("update score_rule set ");

            tryAppend(scoreRule.getRule(), "rule = #{rule}");
            tryAppend(scoreRule.getScore(), "score = #{score}");

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
    class ScoreRuleQueryProvider {
        public static String select(@Param("scoreRule") ScoreRuleQuery scoreRule, Pageable page) {
            return new ScoreRuleRepository.ScoreRuleQueryProvider().getSelect(scoreRule, page);
        }
        public static String count(@Param("scoreRule") ScoreRuleQuery scoreRule) {
            return new ScoreRuleRepository.ScoreRuleQueryProvider().getCount(scoreRule);
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
         * @param scoreRule 积分规则查询对象
         * @return sql语句
         */
        private String getCount(ScoreRuleQuery scoreRule) {
            if (scoreRule == null) {
                return "select count(*) from score_rule";
            }

            builder.append("select count(*) from score_rule");

            packageWhere(scoreRule);

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param scoreRule 积分规则查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(ScoreRuleQuery scoreRule, Pageable page) {
            if (scoreRule == null) {
                return St.r("select {} from score_rule {}",
                        "id, rule, score, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, rule, score, gmt_create, gmt_modify from score_rule");

            packageWhere(scoreRule);

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(ScoreRuleQuery scoreRule) {

            tryAppendWhere(scoreRule.getId(), "id = #{id}");
            tryAppendWhere(scoreRule.getRule(), "rule = #{scoreRule.rule}");
            tryAppendWhere(scoreRule.getScore(), "score = #{scoreRule.score}");
            tryAppendWhere(scoreRule.getGmtCreateStart(), "gmt_create >= #{scoreRule.gmtCreateStart}");
            tryAppendWhere(scoreRule.getGmtCreateEnd(), "gmt_create <= #{scoreRule.gmtCreateEnd}");
            tryAppendWhere(scoreRule.getGmtModifyStart(), "gmt_modify >= #{scoreRule.gmtModifyStart}");
            tryAppendWhere(scoreRule.getGmtModifyEnd(), "gmt_modify <= #{scoreRule.gmtModifyEnd}");
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
