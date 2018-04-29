package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.wlyyy.itrs.domain.Recommend;
import org.wlyyy.itrs.request.RecommendQuery;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import java.util.List;
import java.util.Objects;

/**
 * 增删查改推荐表
 */
@Mapper
public interface RecommendRepository {

    /**
     * 根据id查询推荐表
     *
     * @param id id
     * @return 推荐表
     */
    @Select("select * from recommend where id = #{id}")
    Recommend findById(Long id);

    @SelectProvider(type = RecommendRepository.RecommendQueryProvider.class, method = "select")
    List<Recommend> findByCondition(@Param("recommend") RecommendQuery queryObject, Pageable page);

    @SelectProvider(type = RecommendRepository.RecommendQueryProvider.class, method = "count")
    long countByCondition(@Param("recommend") RecommendQuery queryObject);

    /**
     * 新建推荐表，忽略id、gmtCreate、gmtModify字段
     * @param recommend 推荐表
     */
    @Insert("insert into recommend (candidate_id, user_id, gmt_create, gmt_modify) values (" +
            "#{candidateId}, #{userId}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Recommend recommend);

    @UpdateProvider(type = RecommendRepository.RecommendUpdateByIdProvider.class, method = "updateMethod")
    int updateById(Recommend recommend);

    /**
     * Update动态SQL
     */
    class RecommendUpdateByIdProvider {

        public static String updateMethod(Recommend recommend) {
            return new RecommendRepository.RecommendUpdateByIdProvider().getUpdate(recommend);
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

        String getUpdate(Recommend recommend) {
            Objects.requireNonNull(recommend.getId(), "Cannot update when id is null");

            builder.append("update recommend set ");

            tryAppend(recommend.getCandidateId(), "candidate_id = #{candidateId}");
            tryAppend(recommend.getUserId(), "user_id = #{userId}");

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
    class RecommendQueryProvider {
        public static String select(@Param("recommend") RecommendQuery recommend, Pageable page) {
            return new RecommendRepository.RecommendQueryProvider().getSelect(recommend, page);
        }
        public static String count(@Param("recommend") RecommendQuery recommend) {
            return new RecommendRepository.RecommendQueryProvider().getCount(recommend);
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
         * @param recommend 推荐表查询对象
         * @return sql语句
         */
        private String getCount(RecommendQuery recommend) {
            if (recommend == null) {
                return "select count(*) from recommend";
            }

            builder.append("select count(*) from recommend");

            packageWhere(recommend);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param recommend 推荐表查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(RecommendQuery recommend, Pageable page) {
            if (recommend == null) {
                return St.r("select {} from recommend {}",
                        "id, recommend_no, publisher_id, position_type, position, department_id, hr_name, total, working_place, degree_request, status, memo, proc_key, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, recommend_no, publisher_id, position_type, position, department_id, hr_name, total, working_place, degree_request, status, memo, proc_key, gmt_create, gmt_modify from recommend");

            packageWhere(recommend);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(RecommendQuery recommend) {

            tryAppendWhere(recommend.getId(), "id = #{recommend.id}");
            tryAppendWhere(recommend.getCandidateId(), "candidate_id = #{recommend.candidateId}");
            tryAppendWhere(recommend.getUserId(), "user_id = #{recommend.userId}");
            tryAppendWhere(recommend.getGmtCreateStart(), "gmt_create >= #{recommend.gmtCreateStart}");
            tryAppendWhere(recommend.getGmtCreateEnd(), "gmt_create <= #{recommend.gmtCreateEnd}");
            tryAppendWhere(recommend.getGmtModifyStart(), "gmt_modify >= #{recommend.gmtModifyStart}");
            tryAppendWhere(recommend.getGmtModifyEnd(), "gmt_modify <= #{recommend.gmtModifyEnd}");
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
