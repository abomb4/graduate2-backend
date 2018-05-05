package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.domain.ScoreRule;

import java.text.SimpleDateFormat;

/**
 * 展示层积分规则对象
 *
 * @author wly
 */
public class ScoreRuleListItemVo {

    public static ScoreRuleListItemVo buildFromDomain(ScoreRule source) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Long id = source.getId();
        final String rule = source.getRule();
        final Integer score = source.getScore();
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new ScoreRuleListItemVo(id, rule, score, gmtCreate, gmtModify);
    }

    public ScoreRuleListItemVo() {
    }

    public ScoreRuleListItemVo(Long id, String rule, Integer score, String gmtCreate, String gmtModify) {
        this.id = id;
        this.rule = rule;
        this.score = score;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    private Long id;
    private String rule;
    private Integer score;
    private String gmtCreate;
    private String gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
