package org.wlyyy.itrs.vo;

import org.wlyyy.itrs.domain.Candidate;

import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * 展示层人才库列表对象
 *
 * @author wly
 */
public class CandidateListItemVo {

    public static CandidateListItemVo buildFromDomain(Candidate source) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Long id = source.getId();
        final String name = source.getName();
        final Integer sex = source.getSex();
        final String phoneNo = source.getPhoneNo();
//        final Optional<String> option = Optional.ofNullable(source.getPhoneNo());
//        final String phoneNo = option.orElse("");
//        final String email = source.getEmail();
//        final String graduateTime = formatter.format(source.getGraduateTime());
//        final String degree = source.getDegree();
//        final String workingPlace = source.getWorkingPlace();
//        final String memo = source.getMemo();
//        final String attachment = source.getAttachment();
//        final String phoneNo = checkIsNull(source.getPhoneNo());

        final String email = checkIsNull(source.getEmail());
        // 这里写的有点蠢
        String graduateTime = "";
        if (source.getGraduateTime() != null) {
            graduateTime = formatter.format(source.getGraduateTime());
        }

        final String degree = checkIsNull(source.getDegree());
        final String workingPlace = checkIsNull(source.getWorkingPlace());
        final String memo = checkIsNull(source.getMemo());
        final String attachment = checkIsNull(source.getAttachment());

        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());
        return new CandidateListItemVo(id, name, sex, phoneNo, email, graduateTime,
                degree,  workingPlace, memo, attachment, gmtCreate, gmtModify);
    }

    private static String checkIsNull(String filed) {
        Optional<String> option = Optional.ofNullable(filed);
        String value = option.orElse("");

        return value;
    }

    private Long id;
    private String name;
    private Integer sex;
    private String phoneNo;
    private String email;
    private String graduateTime;
    private String degree;
    private String workingPlace;
    private String memo;
    private String attachment;    // 附件
    private String gmtCreate;
    private String gmtModify;

    public CandidateListItemVo() {
    }

    public CandidateListItemVo(Long id, String name, Integer sex, String phoneNo, String email, String graduateTime,
                               String degree, String workingPlace, String memo, String attachment, String gmtCreate,
                               String gmtModify) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phoneNo = phoneNo;
        this.email = email;
        this.graduateTime = graduateTime;
        this.degree = degree;
        this.workingPlace = workingPlace;
        this.memo = memo;
        this.attachment = attachment;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
