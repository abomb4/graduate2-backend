package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.itrs.domain.Candidate;
import org.wlyyy.itrs.request.CandidateQuery;
import org.wlyyy.common.utils.StringTemplateUtils.St;


import java.util.List;
import java.util.Objects;

/**
 *  candidate 被推荐人信息表操作
 */
@Mapper
public interface CandidateRepository {

    /**
     * 根据id查询被推荐人信息
     *
     * @param id id
     * @return 被推荐人信息
     */
    @Select("select id, name, sex, phone_no, email, graduate_time, degree, working_place, memo, attachment, gmt_create, gmt_modify from candidate where id = #{id}")
    Candidate findById(@Param("id") Long id);

    /**
     * 新建被推荐人信息，忽略id、gmtCreate、gmtModify字段
     * @param candidate 被推荐人信息
     */
    @Insert("insert into candidate(name, sex, phone_no, email, graduate_time, degree, working_place, memo, attachment, gmt_create, gmt_modify) values (" +
            "#{name}, #{sex}, #{phoneNo}, #{email}, #{graduateTime}, #{degree}, #{workingPlace}, #{memo}, #{attachment}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(Candidate candidate);

    @SelectProvider(type = CandidateQueryProvider.class, method = "select")
    List<Candidate> findByCondition(@Param("candidate") CandidateQuery queryObject, Pageable page);

    @SelectProvider(type = CandidateQueryProvider.class, method = "count")
    long countByCondition(@Param("candidate") CandidateQuery queryObject);

    @UpdateProvider(type = CandidateUpdateByIdProvider.class, method = "myMethod")
    int updateById(Candidate candidate);

    /**
     * Update动态SQL
     */
    class CandidateUpdateByIdProvider {
        public static String myMethod(Candidate candidate) {
            return new CandidateUpdateByIdProvider().getUpdate(candidate);
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

        String getUpdate(Candidate candidate) {
            Objects.requireNonNull(candidate.getId(), "Cannot update when id is null");

            builder.append("update candidate set ");

            tryAppend(candidate.getName(), "name = #{name}");
            tryAppend(candidate.getSex(), "sex = #{sex}");
            tryAppend(candidate.getPhoneNo(), "phone_no = #{phoneNo}");
            tryAppend(candidate.getEmail(), "email = #{email}");
            tryAppend(candidate.getGraduateTime(), "graduate_time = #{graduateTime}");
            tryAppend(candidate.getDegree(), "degree = #{degree}");
            tryAppend(candidate.getWorkingPlace(), "working_place = #{workingPlace}");
            tryAppend(candidate.getMemo(), "memo = #{memo}");
            tryAppend(candidate.getAttachment(), "attachment = #{attachment}");

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
    class CandidateQueryProvider {
        public static String select(@Param("candidate") CandidateQuery candidate, Pageable page) {
            return new CandidateQueryProvider().getSelect(candidate, page);
        }
        public static String count(@Param("candidate") CandidateQuery candidate) {
            return new CandidateQueryProvider().getCount(candidate);
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
         * @param candidate 被推荐人查询对象
         * @return sql语句
         */
        private String getCount(CandidateQuery candidate) {
            if (candidate == null) {
                return "select count(*) from candidate";
            }

            builder.append("select count(*) from candidate where ");

            packageWhere(candidate);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param candidate 被推荐人查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(CandidateQuery candidate, Pageable page) {
            if (candidate == null) {
                return St.r("select {} from candidate {}",
                        "id, name, sex, phone_no, email, graduate_time, degree, working_place, memo, attachment, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, name, sex, phone_no, email, graduate_time, degree, working_place, memo, attachment, gmt_create, gmt_modify from candidate");

            packageWhere(candidate);

            // 不能全都是空
            if (first) {
                throw new IllegalArgumentException("One of query condition should be not null");
            }

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(CandidateQuery candidate) {
            String name = "concat('%', #{candidate.name}, '%')";
            String workingPlace = "concat('%', #{candidate.workingPlace}, '%')";

            tryAppendWhere(candidate.getId(), "id = #{id}");
            tryAppendWhere(candidate.getName(), "name like " + name);
            tryAppendWhere(candidate.getSex(), "sex = #{candidate.sex}");
            tryAppendWhere(candidate.getPhoneNo(), "phone_no = #{candidate.phoneNo}");
            tryAppendWhere(candidate.getEmail(), "email = #{candidate.email}");
            tryAppendWhere(candidate.getGraduateTimeStart(), "graduate_time >= #{candidate.graduateTimeStart}");
            tryAppendWhere(candidate.getGraduateTimeEnd(), "graduate_time <= #{candidate.graduateTimeEnd}");
            tryAppendWhere(candidate.getDegree(), "degree = #{candidate.degree}");
            tryAppendWhere(candidate.getWorkingPlace(), "working_place like " + workingPlace);
            tryAppendWhere(candidate.getGmtCreateStart(), "gmt_create >= #{candidate.gmtCreateStart}");
            tryAppendWhere(candidate.getGmtCreateEnd(), "gmt_create <= #{candidate.gmtCreateEnd}");
            tryAppendWhere(candidate.getGmtModifyStart(), "gmt_modify >= #{candidate.gmtModifyStart}");
            tryAppendWhere(candidate.getGmtModifyEnd(), "gmt_modify <= #{candidate.gmtModifyEnd}");
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
