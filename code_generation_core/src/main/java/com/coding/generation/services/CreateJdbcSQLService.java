package com.coding.generation.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.tools.CreateCodingFileTools;
import com.coding.jdbc.exception.SYHCException;

/**
 * 数据库表结构SQL语句生成工具
 *
 * @Copyright MacChen
 * @Project CodeGenerationTool
 * @Author MacChen
 * @timer 2017-12-01
 * @Version 1.0.0
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Repository
public class CreateJdbcSQLService {

    @Resource
    private CreateCodingFileTools fileTools;

    /**
     * 生成目标库表JDBC业务SQL语句
     *
     * @param databaseName  库实例名称
     * @param tableName     数据表名称
     * @param tableComment  数据表名称注解
     * @param columnMap     库表列字段定义
     * @param defaultMethod 默认方法定义
     * @return Boolean true/false=成功/失败
     * @throws SYHCException
     */
    public Boolean constractSql(String databaseName, String tableName, String tableComment, Map<String, TableColumnBean> columnMap, String defaultMethod[]) throws SYHCException {
        Boolean conditionFlag = false;
        Boolean statisticsFlag = false;
        // 1.生成SQL语句
        List<String> sqlList = new ArrayList<String>();
        if (defaultMethod != null) {
            for (String method : defaultMethod) {
                if (method.equals("insert")) {
                    sqlList.add("#新增" + tableComment + "信息实例");
                    sqlList.add(insertSQL(databaseName, tableName, columnMap.values()));
                } else if (method.equals("update")) {
                    sqlList.add("#更新" + tableComment + "信息实例");
                    sqlList.add(updateSQL(databaseName, tableName, columnMap.values(), false));
                } else if (method.equals("updateDynamicField")) {
                    sqlList.add("#动态更新" + tableComment + "列字段值");
                    sqlList.add(updateSQL(databaseName, tableName, columnMap.values(), true));
                } else if (method.equals("condition")) {
                    conditionFlag = true;
                } else if (method.equals("statistics")) {
                    statisticsFlag = true;
                }
            }
        }
        for (TableColumnBean column : columnMap.values()) {
            if (StringUtils.isNotBlank(column.getRemoveMothod())) {
                this.deleteSQL(databaseName, tableName, tableComment, column, sqlList);
            }
        }
        String basicSQL = this.queryForBean(databaseName, tableName, columnMap.values());
        sqlList.add("#获取" + tableComment + "信息实例【基础查询语句】");
        sqlList.add(this.propertyHead(tableName) + "_queryForBean=" + basicSQL);

        for (TableColumnBean column : columnMap.values()) {
            if (StringUtils.isNotBlank(column.getBeanMothod())) {
                this.queryForBean(tableName, tableComment, column, sqlList);
            }
        }
        for (TableColumnBean column : columnMap.values()) {
            if (StringUtils.isNotBlank(column.getListMothod())) {
                this.queryForList(tableName, tableComment, column, sqlList);
            }
        }
        if (conditionFlag) {
            this.conditionSQL(tableName, tableComment, sqlList);
        }
        if (statisticsFlag) {
            this.statisticsSQL(databaseName, tableName, tableComment, sqlList);
        }

        // 生成SQL语句
        fileTools.writeFile(sqlList, databaseName, tableName, tableName + ".sql");
        return true;
    }

    private String propertyHead(String tableName) {
        return tableName.replaceFirst("tb_", "").replaceFirst("v_", "").replaceFirst("mv_", "");
    }

    private String insertSQL(String databaseName, String tableName, Collection<TableColumnBean> columnList) {
        String propertyKey = this.propertyHead(tableName) + "_insert=";
        StringBuilder sql = new StringBuilder(propertyKey + "insert into " + tableName + " (");
        Boolean isFirst = true;
        for (TableColumnBean column : columnList) {
            if (isFirst) {
                sql.append(column.getColumnName());
            } else {
                sql.append("," + column.getColumnName());
            }
            isFirst = false;
        }
        sql.append(") values (");
        isFirst = true;
        for (TableColumnBean column : columnList) {
            if (isFirst == false) {
                sql.append(",");
            }
            sql.append(column.getVariableName());
            isFirst = false;
        }
        sql.append(")");
        return sql.toString();

    }

    private String updateSQL(String databaseName, String tableName, Collection<TableColumnBean> columnList, Boolean dynamic) {
        StringBuilder sql = null;
        Boolean isFirst = true;
        String propertyKey = this.propertyHead(tableName);
        if (dynamic == false) {
            propertyKey = propertyKey + "_update=";
            sql = new StringBuilder(propertyKey + "update " + tableName + " set ");
            for (TableColumnBean column : columnList) {
                if (column.getColumnName().toUpperCase().equals("CREATETIME") || column.getColumnName().toUpperCase().equals("INSERTTIME") || column.getPrimaryKey()) {
                    continue;
                }
                if (isFirst == false) {
                    sql.append(",");
                }
                isFirst = false;
                sql.append(column.getColumnName() + "=" + column.getVariableName());
            }
        } else {
            propertyKey = propertyKey + "_updateDynamicField=";
            sql = new StringBuilder(propertyKey + "upadte " + tableName + " set #condition# ");
        }
        sql.append(" where ");
        isFirst = true;
        for (TableColumnBean column : columnList) {
            if (column.getPrimaryKey()) {
                if (isFirst == false) {
                    sql.append(" and ");
                } else {
                    isFirst = false;
                }
                sql.append(column.getColumnName() + "=" + column.getVariableName());
            }
        }
        return sql.toString();
    }

    private void deleteSQL(String databaseName, String tableName, String tableComment, TableColumnBean column, List<String> sqlList) {
        String columnName = null;
        String columnComment = null;
        StringBuilder where = new StringBuilder(" where ");
        columnComment = column.getColumnComment();
        columnName = column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1, column.getColumnName().length());
        where.append(column.getColumnName() + "=" + column.getVariableName());
        sqlList.add("#根据" + columnComment + "删除" + tableComment + "信息实例");
        String propertyKey = this.propertyHead(tableName) + "_deleteBy";
        sqlList.add(propertyKey + columnName + "=delete from " + tableName + where);
    }

    private void queryForBean(String tableName, String tableComment, TableColumnBean column, List<String> sqlList) {
        String columnName = null;
        columnName = column.getColumnName();
        columnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
        String propertyKey = this.propertyHead(tableName) + "_queryForBeanBy" + columnName;

        sqlList.add("#根据" + column.getColumnComment() + "查询" + tableComment + "信息实例");
        sqlList.add(propertyKey + "=" + "where t." + column.getColumnName() + "=" + column.getVariableName());
    }

    private String queryForBean(String databaseName, String tableName, Collection<TableColumnBean> columnList) {
        Boolean isFirst = true;
        StringBuilder sql = new StringBuilder("select ");
        for (TableColumnBean column : columnList) {
            if (isFirst == false) {
                sql.append(",");
            }
            isFirst = false;
            sql.append(column.getQueryField());
        }
        sql.append(" from " + tableName + " t");
        return sql.toString();
    }

    private void queryForList(String tableName, String tableComment, TableColumnBean column, List<String> sqlList) {
        String columnName = null;
        columnName = column.getColumnName();
        columnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
        String propertyKey = this.propertyHead(tableName) + "_queryForListBy" + columnName;

        sqlList.add("#根据" + column.getColumnComment() + "查询" + tableComment + "信息实例列表");
        sqlList.add(propertyKey + "=" + "where t." + column.getColumnName() + "=" + column.getVariableName());
    }

    private void conditionSQL(String tableName, String tableComment, List<String> sqlList) {
        sqlList.add("#根据动态查询条件获取" + tableComment + "信息实例集合");
        String propertyKey = this.propertyHead(tableName) + "_queryForListByCondition=";
        sqlList.add(propertyKey + "#condition# limit #startIndex#,#pageSize#");
    }

    private void statisticsSQL(String databaseName, String tableName, String tableComment, List<String> sqlList) {
        sqlList.add("#根据动态查询条件统计" + tableComment + "信息实例记录数");
        String propertyKey = this.propertyHead(tableName) + "_statisticsForListByCondition=";
        sqlList.add(propertyKey + "select count(1) as totalno from " + tableName + " t #condition#");
    }

    private void groupByColumn(String tableName, TableColumnBean column, List<String> sqlList) {
        String columnName = null;
        columnName = column.getColumnName();
        columnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
        String propertyKey = this.propertyHead(tableName) + "_groupBy" + columnName;

        sqlList.add("#根据" + column.getColumnComment() + "集合进行分组统计存在 XXXXXXXX 数据记录条数");
        sqlList.add(propertyKey + "=" + "select t." + column.getColumnName() + ",count(1) as total from XXXXXXXX t where t." + column.getColumnName() + "in (#condition#) group by " + column.getColumnName());
    }

}
