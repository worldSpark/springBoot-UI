package com.fc.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*//=
 * @Description sql通用
 * @author luoy
 * @Date 2018年08月26日 上午11:30:00
 * @version 1.0.0
 **/
@Mapper
@Component
public interface SqlMapper {

    @Select("${sql}")
    void sqlExecute(@Param("sql") String sql);

    @Insert("${sql}")
    int insert(@Param("sql") String sql);

    @Delete("${sql}")
    int delete(@Param("sql") String sql);

    @Update("${sql}")
    int update(@Param("sql") String sql);

    @Select("${sql}")
    Map sqlQueryOne(@Param("sql") String sql);

    @Select("${sql}")
    int sqlCount(@Param("sql") String sql);

    @Select("${sql}")
    List<Map<String,Object>> sqlQueryList(@Param("sql") String sql);

    @Select("${sql}")
    List<String> sqlQueryWithUniqueColumnList(@Param("sql") String sql);

    @Select("${sql}")
    String sqlQueryWithUniqueResult(@Param("sql") String sql);

}
