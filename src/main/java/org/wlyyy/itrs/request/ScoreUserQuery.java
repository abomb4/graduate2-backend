package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Date;

public class ScoreUserQuery {

    private Long id;
    private Long userId;
    private Integer currentScore;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public ScoreUserQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ScoreUserQuery setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public ScoreUserQuery setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public ScoreUserQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public ScoreUserQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public ScoreUserQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public ScoreUserQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public ScoreUserQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
