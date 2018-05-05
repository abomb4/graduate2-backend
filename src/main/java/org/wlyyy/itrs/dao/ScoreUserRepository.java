package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.ScoreUser;
import org.wlyyy.itrs.request.ScoreUserQuery;

import java.util.List;
import java.util.Objects;

public interface ScoreUserRepository {

    /**
     * 根据id查询用户当前积分对象
     *
     * @param id id
     * @return 用户当前积分对象
     */
    @Select("select id, user_id, current_score, gmt_create, gmt_modify from score_user where id = #{id}")
    ScoreUser findById(@Param("id") Long id);

    /**
     * 根据用户id查询用户当前积分对象
     *
     * @param userId 用户id
     * @return 该用户当前积分对象
     */
    @Select("select id, user_id, current_score, gmt_create, gmt_modify from score_user where user_id = #{userId}")
    ScoreUser findCurrentScoreByUserId(@Param("userId") Long userId);

    /**
     * 新建用户当前积分，忽略id、gmtCreate、gmtModify字段
     * @param scoreUser 用户当前积分
     */
    @Insert("insert into score_user(user_id, current_score, gmt_create, gmt_modify) values (" +
            "#{userId}, #{currentScore}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(ScoreUser scoreUser);

    @SelectProvider(type = ScoreUserQueryProvider.class, method = "select")
    List<ScoreUser> findByCondition(@Param("scoreUser") ScoreUserQuery queryObject, Pageable page);

    @SelectProvider(type = ScoreUserQueryProvider.class, method = "count")
    long countByCondition(@Param("scoreUser") ScoreUserQuery queryObject);

    @UpdateProvider(type = ScoreUserUpdateByIdProvider.class, method = "myMethod")
    int updateById(ScoreUser scoreUser);

    /**
     * Update动态SQL
     */
    class ScoreUserUpdateByIdProvider {
        public static String myMethod(ScoreUser scoreUser) {
            return new ScoreUserRepository.ScoreUserUpdateByIdProvider().getUpdate(scoreUser);
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

        String getUpdate(ScoreUser scoreUser) {
            Objects.requireNonNull(scoreUser.getId(), "Cannot update when id is null");

            builder.append("update score_user set ");

            tryAppend(scoreUser.getUserId(), "user_id = #{userId}");
            tryAppend(scoreUser.getCurrentScore(), "current_score = #{currentScore}");

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
    class ScoreUserQueryProvider {
        public static String select(@Param("scoreUser") ScoreUserQuery scoreUser, Pageable page) {
            return new ScoreUserRepository.ScoreUserQueryProvider().getSelect(scoreUser, page);
        }
        public static String count(@Param("scoreUser") ScoreUserQuery scoreUser) {
            return new ScoreUserRepository.ScoreUserQueryProvider().getCount(scoreUser);
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
         * @param scoreUser 用户当前积分查询对象
         * @return sql语句
         */
        private String getCount(ScoreUserQuery scoreUser) {
            if (scoreUser == null) {
                return "select count(*) from score_user";
            }

            builder.append("select count(*) from score_user");

            packageWhere(scoreUser);

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param scoreUser 用户当前积分查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(ScoreUserQuery scoreUser, Pageable page) {
            if (scoreUser == null) {
                return St.r("select {} from score_user {}",
                        "id, user_id, current_score, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, user_id, current_score, gmt_create, gmt_modify from score_user");

            packageWhere(scoreUser);

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(ScoreUserQuery scoreUser) {

            tryAppendWhere(scoreUser.getId(), "id = #{id}");
            tryAppendWhere(scoreUser.getUserId(), "user_id = #{scoreUser.userId}");
            tryAppendWhere(scoreUser.getCurrentScore(), "current_score = #{scoreUser.currentScore}");
            tryAppendWhere(scoreUser.getGmtCreateStart(), "gmt_create >= #{scoreUser.gmtCreateStart}");
            tryAppendWhere(scoreUser.getGmtCreateEnd(), "gmt_create <= #{scoreUser.gmtCreateEnd}");
            tryAppendWhere(scoreUser.getGmtModifyStart(), "gmt_modify >= #{scoreUser.gmtModifyStart}");
            tryAppendWhere(scoreUser.getGmtModifyEnd(), "gmt_modify <= #{scoreUser.gmtModifyEnd}");
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
