package org.wlyyy.itrs.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * 职位类别
 */
public class PositionType {

    private Long id;
    private String englishName;
    private String chineseName;
    private List<PositionType> subTypes;

    public PositionType(Long id, String englishName, String chineseName) {
        this.id = id;
        this.englishName = englishName;
        this.chineseName = chineseName;
        subTypes = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public List<PositionType> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<PositionType> subTypes) {
        this.subTypes = subTypes;
    }
}