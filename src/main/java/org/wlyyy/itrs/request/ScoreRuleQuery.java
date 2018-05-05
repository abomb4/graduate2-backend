package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Date;

public class ScoreRuleQuery {

    private Long id;
    private String rule;
    private Integer score;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public ScoreRuleQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public ScoreRuleQuery setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public ScoreRuleQuery setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public ScoreRuleQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public ScoreRuleQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public ScoreRuleQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public ScoreRuleQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public ScoreRuleQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
