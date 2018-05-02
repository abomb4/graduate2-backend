package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.*;
import org.wlyyy.itrs.domain.PositionType;

import java.util.List;

/**
 *
 */
@Mapper
public interface PositionTypeRepository {

    /**
     * find by id
     *
     * @param id id
     * @return 不包含子类信息的职位信息对象
     */
    @Select("select * from position_type where id = #{id}")
    PositionType findById(@Param("id") Long id);

    /**
     * 新建用户角色对应信息，忽略id、gmtCreate、gmtModify字段
     *
     * @param PositionType 用户角色对应信息
     */
    @Insert("insert into position_type(id, parent_id, chinese_name, english_name, gmt_create, gmt_modify) values (" +
            "#{id}, #{parentId}, #{chineseName}, #{englishName} now(), now())")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(PositionType PositionType);

    @Select("select * from position_type")
    List<PositionType> findAll();

    /**
     * 删除职位
     *
     * @param id 职位id
     * @return 删除的条数
     */
    @Delete("delete from position_type where id in #{id}")
    long deleteById(@Param("id") Long id);

    /**
     * 删除职位
     *
     * @param ids 职位id们
     * @return 删除的条数
     */
    @Delete("delete from position_type where id in #{ids}")
    long deleteById(@Param("ids") List<Long> ids);

}
