package org.wlyyy.itrs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 查找下一序列
 */
@Mapper
public interface SequenceRepository {

    // query sql select nextval_bunch(#name:varchar#, #count:int#) as val from DUAL
    @Select("select nextval_bunch(#{querymap.name}, #{querymap.count}) as val from DUAL")
    Long findNextVal(@Param("querymap") Map<String, Object> querymap);
}
