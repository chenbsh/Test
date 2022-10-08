package com.coding.generation.domain;

import java.math.BigDecimal;

import com.coding.jdbc.domain.BasicBean;

/**
 * 数据库表结构字段属性定义信息Bean
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
public class TableColumnBean extends BasicBean {

    private static final long serialVersionUID = -4627960320684832978L;

    public TableColumnBean() {
        super();
    }

    /**
     * 数据库实例名称
     */
    private String databaseName;

    /**
     * 数据库表名称
     */
    private String tableName;

    /**
     * 列字段注解
     */
    private String columnComment;

    /**
     * 列字段名称
     */
    private String columnName;

    /**
     * 列字段别名【前端定义】
     */
    private String columnNameAlias;

    /**
     * 列字段类型
     */
    private String columnType;

    /**
     * 列对应Java类型
     */
    private String javaType;

    /**
     * 列对应Java类型
     */
    private String javaSimpleType;

    /**
     * 是否主键
     */
    private Boolean primaryKey = false;

    /**
     * 动态查询
     */
    private Boolean searchkey = false;

    /**
     * 列表显示
     */
    private Boolean displayList = false;

    /**
     * 表单显示
     */
    private Boolean displayForm = false;

    /**
     * 使用检查
     */
    private Boolean usedCheck = false;

    /**
     * 生成删除方法
     */
    private String removeMothod;

    /**
     * 生成单条记录查询方法
     */
    private String beanMothod;

    /**
     * 生成列表记录查询方法
     */
    private String listMothod;

    /**
     * 变量名称
     */
    private String variableName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnNameAlias() {
        return columnNameAlias;
    }

    public void setColumnNameAlias(String columnNameAlias) {
        this.columnNameAlias = columnNameAlias;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaSimpleType() {
        return javaSimpleType;
    }

    public void setJavaSimpleType(String javaSimpleType) {
        this.javaSimpleType = javaSimpleType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        columnType = columnType.toUpperCase();
        this.columnType = columnType;
        if (columnType.equals("BIT") || columnType.equals("TINYINT") || columnType.equals("SMALLINT") || columnType.equals("MEDIUMINT") || columnType.equals("INT") || columnType.equals("INTEGER")) {
            this.javaType = Integer.class.getName();
        } else if (columnType.equals("BIGINT")) {
            this.javaType = Long.class.getName();
        } else if (columnType.equals("FLOAT") || columnType.equals("REAL")) {
            this.javaType = Float.class.getName();
        } else if (columnType.equals("DOUBLE")) {
            this.javaType = Double.class.getName();
        } else if (columnType.equals("DECIMAL") || columnType.equals("NUMERIC")) {
            this.javaType = BigDecimal.class.getName();
        } else if (columnType.equals("CHAR") || columnType.equals("VARCHAR")) {
            this.javaType = String.class.getName();
        } else if (columnType.equals("YEAR")) {
            this.javaType = Integer.class.getName();
        } else if (columnType.equals("DATE") || columnType.equals("TIME") || columnType.equals("DATETIME") || columnType.equals("TIMESTAMP")) {
            this.javaType = String.class.getName();
        } else if (columnType.equals("ENUM")) {
            this.javaType = String.class.getName();
        } else {
            this.javaType = String.class.getName();
        }
        this.javaSimpleType = this.javaType.substring(this.javaType.lastIndexOf(".") + 1, this.javaType.length());

    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(Boolean searchkey) {
        this.searchkey = searchkey;
    }

    public Boolean getDisplayList() {
        return displayList;
    }

    public void setDisplayList(Boolean displayList) {
        this.displayList = displayList;
    }

    public Boolean getDisplayForm() {
        return displayForm;
    }

    public void setDisplayForm(Boolean displayForm) {
        this.displayForm = displayForm;
    }

    public Boolean getUsedCheck() {
        return usedCheck;
    }

    public void setUsedCheck(Boolean usedCheck) {
        this.usedCheck = usedCheck;
    }

    public String getRemoveMothod() {
        return removeMothod;
    }

    public void setRemoveMothod(String removeMothod) {
        this.removeMothod = removeMothod;
    }

    public String getBeanMothod() {
        return beanMothod;
    }

    public void setBeanMothod(String beanMothod) {
        this.beanMothod = beanMothod;
    }

    public String getListMothod() {
        return listMothod;
    }

    public void setListMothod(String listMothod) {
        this.listMothod = listMothod;
    }

    public String getVariableName() {
        if (this.columnName.toUpperCase().equals("INSERTTIME") || this.columnName.toUpperCase().equals("CREATETIME") || this.columnName.toUpperCase().equals("UPDATETIME")) {
            this.variableName = "now()";
        } else if (javaType.equals(Integer.class.getName()) || javaType.equals(Long.class.getName()) || javaType.equals(Float.class.getName()) || javaType.equals(Double.class.getName()) || javaType.equals(BigDecimal.class.getName())) {
            this.variableName = "#" + columnName + "#";
        } else if (columnType.equals("DATE")) {
            this.variableName = "str_to_date('#" + columnName + "#','%Y-%m-%d')";
        } else if (columnType.equals("DATETIME")) {
            if (columnName.toUpperCase().equals("CREATETIME") || columnName.toUpperCase().equals("UPDATETIME") || columnName.toUpperCase().equals("INSERTTIME")) {
                this.variableName = "now()";
            } else {
                this.variableName = "str_to_date('#" + columnName + "#','%Y-%m-%d %H:%i:%s')";
            }
        } else {
            this.variableName = "'#" + columnName + "#'";
        }
        return variableName;
    }

    public String getQueryField() {
        if (columnType.equals("DATE")) {
            return "str_to_date(t." + columnName + ",'%Y-%m-%d') as " + columnName;
        } else if (columnType.equals("DATETIME")) {
            return "str_to_date(t." + columnName + ",'%Y-%m-%d %H:%i:%s') as " + columnName;
        } else {
            return "t." + columnName;
        }
    }

    public String formatJava() {
        return columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
    }

    public String formatJavaByAlias() {
        return columnNameAlias.substring(0, 1).toUpperCase() + columnNameAlias.substring(1, columnNameAlias.length());
    }

}
