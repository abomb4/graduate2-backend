package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.domain.ScoreFlow;

import java.text.SimpleDateFormat;

/**
 * 展示层用户积分变动流水记录对象
 *
 * @author wly
 */
public class ScoreFlowListItemVo {

    public static ScoreFlowListItemVo buildFromDomain(ScoreFlow source) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Long id = source.getId();
        final Long userId = source.getUserId();
        final Integer score = source.getScore();
        final String memo = source.getMemo();
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new ScoreFlowListItemVo(id, userId, score, memo, gmtCreate, gmtModify);
    }

    public ScoreFlowListItemVo() {
    }

    public ScoreFlowListItemVo(
            Long id, Long userId, Integer score, String memo,
            String gmtCreate, String gmtModify
    ) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.memo = memo;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    private Long id;
    private Long userId;
    private Integer score;
    private String memo;
    private String gmtCreate;
    private String gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(String gmtModify) {
        this.gmtModify = gmtModify;
    }
}
