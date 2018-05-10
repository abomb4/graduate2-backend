package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.EmailLog;
import org.wlyyy.itrs.request.EmailLogQuery;

import java.util.List;
import java.util.Objects;

@Mapper
public interface EmailLogRepository {

    /**
     * 根据id查询邮件日志
     *
     * @param id id
     * @return 邮件日志
     */
    @Select("select id, apply_flow_id, send_email, receive_email, status, content, gmt_create, gmt_modify from email_log where id = #{id}")
    EmailLog findById(@Param("id") Long id);

    /**
     * 新建邮件日志，忽略id、gmtCreate、gmtModify字段
     * @param emailLog 邮件日志
     */
    @Insert("insert into email_log(apply_flow_id, send_email, receive_email, status, content, gmt_create, gmt_modify) values (" +
            "#{applyFlowId}, #{sendEmail}, #{receiveEmail}, #{status}, #{content}, now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(EmailLog emailLog);

    @SelectProvider(type = EmailLogRepository.EmailLogQueryProvider.class, method = "select")
    List<EmailLog> findByCondition(@Param("emailLog") EmailLogQuery queryObject, Pageable page);

    @SelectProvider(type = EmailLogRepository.EmailLogQueryProvider.class, method = "count")
    long countByCondition(@Param("emailLog") EmailLogQuery queryObject);

    @UpdateProvider(type = EmailLogRepository.EmailLogUpdateByIdProvider.class, method = "myMethod")
    int updateById(EmailLog emailLog);

    /**
     * Update动态SQL
     */
    class EmailLogUpdateByIdProvider {
        public static String myMethod(EmailLog emailLog) {
            return new EmailLogRepository.EmailLogUpdateByIdProvider().getUpdate(emailLog);
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

        String getUpdate(EmailLog emailLog) {
            Objects.requireNonNull(emailLog.getId(), "Cannot update when id is null");

            builder.append("update email_log set ");

            tryAppend(emailLog.getApplyFlowId(), "apply_flow_id = #{applyFlowId}");
            tryAppend(emailLog.getSendEmail(), "send_email = #{sendEmail}");
            tryAppend(emailLog.getReceiveEmail(), "receive_email = #{receiveEmail}");
            tryAppend(emailLog.getStatus(), "status = #{status}");
            tryAppend(emailLog.getContent(), "content = #{content}");

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
    class EmailLogQueryProvider {
        public static String select(@Param("emailLog") EmailLogQuery emailLog, Pageable page) {
            return new EmailLogRepository.EmailLogQueryProvider().getSelect(emailLog, page);
        }
        public static String count(@Param("emailLog") EmailLogQuery emailLog) {
            return new EmailLogRepository.EmailLogQueryProvider().getCount(emailLog);
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
         * @param emailLog 邮件日志查询对象
         * @return sql语句
         */
        private String getCount(EmailLogQuery emailLog) {
            if (emailLog == null) {
                return "select count(*) from email_log";
            }

            builder.append("select count(*) from email_log");

            packageWhere(emailLog);

            return builder.toString();
        }

        /**
         * 获取查询条件sql
         *
         * @param emailLog 邮件日志查询对象
         * @param page 分页对象
         * @return sql语句
         */
        private String getSelect(EmailLogQuery emailLog, Pageable page) {
            if (emailLog == null) {
                return St.r("select {} from email_log {}",
                        "id, apply_flow_id, send_email, receive_email, status, content, gmt_create, gmt_modify",
                        getPage(page)
                );
            }
            builder.append("select id, apply_flow_id, send_email, receive_email, status, content, gmt_create, gmt_modify from email_log");

            packageWhere(emailLog);

            builder.append(getOrder(page));
            builder.append(" ").append(getPage(page));

            return builder.toString();
        }

        private void packageWhere(EmailLogQuery emailLog) {

            tryAppendWhere(emailLog.getId(), "id = #{id}");
            tryAppendWhere(emailLog.getApplyFlowId(), "apply_flow_id = #{emailLog.applyFlowId}");
            tryAppendWhere(emailLog.getSendEmail(), "send_email = #{emailLog.sendEmail}");
            tryAppendWhere(emailLog.getReceiveEmail(), "receive_email = #{emailLog.receiveEmail}");
            tryAppendWhere(emailLog.getStatus(), "status = #{emailLog.status}");
            tryAppendWhere(emailLog.getContent(), "content = #{emailLog.content}");
            tryAppendWhere(emailLog.getGmtCreateStart(), "gmt_create >= #{emailLog.gmtCreateStart}");
            tryAppendWhere(emailLog.getGmtCreateEnd(), "gmt_create <= #{emailLog.gmtCreateEnd}");
            tryAppendWhere(emailLog.getGmtModifyStart(), "gmt_modify >= #{emailLog.gmtModifyStart}");
            tryAppendWhere(emailLog.getGmtModifyEnd(), "gmt_modify <= #{emailLog.gmtModifyEnd}");
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
