package com.fc.annotation;

import java.lang.annotation.*;

/**
 * @Description:    项目启动时自动扫描配置的目录中的mapper，创建存储过程语句
 *                      注解@Procedure中写入所需要创建存储过程的语句，置于对应方法之上
 *                      org.apache.ibatis.annotations中的注解集写入 可执行对应@Procedure中存储过程的语句
 *                      列如：
 *         --             @Procedure(" create proc findTableEnsembleByTableName(@TABLENAME VARCHAR(100))" +
 *       " as SELECT b.*,c.[ CONSTRAINT ] as constraint_name," +
 *       " CASE WHEN c.is_primary_key = 1 THEN c.is_primary_key ELSE 0 END AS is_primary_key," +
 *       " CASE WHEN c.is_unique_constraint = 1 THEN c.is_unique_constraint ELSE 0 END AS is_unique_constraint," +
 *       " CASE WHEN columnproperty( object_id( @TABLENAME ), '' + b.COLUMN_NAME, 'IsIdentity' ) = 1 " +
 *       " THEN 1 ELSE 0 END AS is_identity FROM" +
 *       " (SELECT * FROM information_schema.COLUMNS WHERE table_name = @TABLENAME AND " +
 *       CommonConfig.DATA_BASE_COLUMN + " = (SELECT DB_NAME())) AS b" +
 *       " LEFT JOIN (SELECT* FROM(SELECT idx.object_id AS id,tab.NAME AS tableName,idx.NAME AS [ CONSTRAINT ]," +
 *       " col.NAME AS conRow,idx.is_primary_key,idx.is_unique_constraint FROM sys.indexes idx" +
 *       " JOIN sys.TABLES tab ON ( idx.object_id = tab.object_id )" +
 *       " JOIN sys.index_columns idxCol ON ( idx.object_id = idxCol.object_id AND idx.index_id = idxCol.index_id )" +
 *       " JOIN sys.COLUMNS col ON ( idx.object_id = col.object_id AND idxCol.column_id = col.column_id ) ) AS z " +
 *       " WHERE z.id = object_id( @TABLENAME ) UNION" +
 *       " select sc.id,@TABLENAME AS tableName ,so.name AS [ CONSTRAINT ], sc.name AS conRow,0 AS is_primary_key," +
 *       " 0 AS is_unique_constraint from sysobjects so join syscolumns sc on so.id = sc.cdefault" +
 *       （" where sc.id = object_id(@TABLENAME )) AS c ON ( c.tableName = b.TABLE_NAME AND c.conRow = b.COLUMN_NAME )")
 *                                                                                   --存储过程语句
 *
 *        --   @Select("exec findTableEnsembleByTableName #{tableName}")    --执行存储过程语句
 *
 *           List<SysSqlServerColumns> findTableEnsembleByTableName(@Param("tableName") String tableName);  --mapper中方法
 * @Author:         gongwb
 * @Date:     2018/12/24
 */

//作用于方法
@Target(ElementType.METHOD)
//VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
//将此注解包含在javadoc中
@Documented
public @interface Procedure {
    /**
     * 存储过程语句
     * @return 存储过程语句
     */
    String[] value();
}
