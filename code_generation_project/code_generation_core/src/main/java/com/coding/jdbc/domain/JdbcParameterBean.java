package com.coding.jdbc.domain;

/**
 * JDBC动态查询参数信息封装实体Bean
 * 
 * @Copyright MacChen
 * 
 * @Project CodeGenerationTool
 * 
 * @Author MacChen
 * 
 * @timer 2017-12-01
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 8.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public final class JdbcParameterBean extends BasicBean {

    /**
     * 数字类型
     */
    public static final int NUMERIC = 0;
    /**
     * 字符串类型
     */
    public static final int STRING = 1;
    /**
     * 日期类型［YYYY-MM-DD］
     */
    public static final int DATE = 2;
    /**
     * 时间类型［YYYY-MM-DD HH24:MI:SS］
     */
    public static final int DATETIME = 3;
    /**
     * 等号
     */
    public static final String EQUALS = "=";
    /**
     * 不等号
     */
    public static final String NOTEQUALS = "!=";
    /**
     * 小于号
     */
    public static final String LESSTHAN = "<";
    /**
     * 小于等于号
     */
    public static final String LESSOREQUALS = "<=";
    /**
     * 大于号
     */
    public static final String MORETHAN = ">";
    /**
     * 大于等于号
     */
    public static final String MOREOREQUALS = ">=";
    /**
     * 值为空
     */
    public static final String ISNULL = "is null";
    /**
     * 值不为空
     */
    public static final String ISNOTNULL = "is not null";
    /**
     * LIKE
     */
    public static final String LIKE = "like";
    /**
     * LIKE
     */
    public static final String NOTLIKE = "not like";
    /**
     * AND
     */
    public static final String AND = "and";
    /**
     * OR
     */
    public static final String OR = "or";
    /**
     * ASC
     */
    public static final String ASC = "asc";
    /**
     * DESC
     */
    public static final String DESC = "desc";
    private static final long serialVersionUID = -789246597368814681L;
    /**
     * 参数名称［数据库字段名称］
     */
    private String name;
    /**
     * 参数值
     */
    private String value;

    // -------------------------------------------------------------------------------------------------------------------
    /**
     * 值数据类型［NUMERIC、STRING、DATE、DATETIME］
     */
    private int typecode;
    /**
     * 运算符号［EQUALS、NOTEQUALS、LESSTHAN、LESSOREQUALS、MORETHAN、MOREOREQUALS、ISNULL、ISNOTNULL、LIKE］
     */
    private String operator;
    /**
     * 关系运算符［AND、OR］
     */
    private String relation;
    /**
     * 前括弧
     */
    private int startBracket = 0;
    /**
     * 后括弧
     */
    private int endBracket = 0;

    /**
     * 动态查询参数信息实例构造函数
     *
     * @param name         参数名称
     * @param value        参数值
     * @param typecode     参数值类型
     * @param operator     运算符
     * @param relation     与后面一个参数关系符
     * @param startBracket 起始左小括号个数
     * @param endBracket   结束右小括号个数
     * @return 参数信息实例
     * @Author MacChen
     * @time 2018-03-28
     */
    public JdbcParameterBean(String name, String value, int typecode, String operator, String relation, int startBracket, int endBracket) {
        super();
        this.setName(name);
        this.setValue(value);
        this.setTypecode(typecode);
        this.setOperator(operator);
        this.setRelation(relation);
        this.setStartBracket(startBracket);
        this.setEndBracket(endBracket);
    }

    /**
     * 动态查询参数信息实例构造函数
     *
     * @param name     参数名称
     * @param value    参数值
     * @param typecode 参数值类型
     * @param operator 运算符
     * @param relation 与后面一个参数关系符
     * @return 参数信息实例
     * @Author MacChen
     * @time 2018-03-28
     */
    public JdbcParameterBean(String name, String value, int typecode, String operator, String relation) {
        super();
        this.setName(name);
        this.setValue(value);
        this.setTypecode(typecode);
        this.setOperator(operator);
        this.setRelation(relation);
    }


    /**
     * 动态查询参数信息实例构造函数【动态更新字段】
     *
     * @param name     参数名称
     * @param value    参数值
     * @param typecode 参数值类型
     * @return 参数信息实例
     * @Author MacChen
     * @time 2018-03-28
     */
    public JdbcParameterBean(String name, String value, int typecode) {
        super();
        this.setName(name);
        this.setValue(value);
        this.setTypecode(typecode);
        this.setOperator(JdbcParameterBean.EQUALS);
        this.setRelation(JdbcParameterBean.AND);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTypecode() {
        return typecode;
    }

    public void setTypecode(int typecode) {
        this.typecode = typecode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getStartBracket() {
        return startBracket;
    }

    public void setStartBracket(int startBracket) {
        this.startBracket = startBracket;
    }

    public int getEndBracket() {
        return endBracket;
    }

    public void setEndBracket(int endBracket) {
        this.endBracket = endBracket;
    }

    public String toString() {
        String valuecode = null;
        if (this.operator.equals(JdbcParameterBean.ISNULL) || this.operator.equals(JdbcParameterBean.ISNOTNULL)) {
            return this.name + " " + this.operator;
        } else if (this.typecode == JdbcParameterBean.NUMERIC) {
            valuecode = this.value;
        } else if (this.typecode == JdbcParameterBean.STRING) {
            if (this.value != null) {
                valuecode = "'" + this.value.replaceAll("'", "''") + "'";
            } else {
                valuecode = "'" + this.value + "'";
            }
        } else if (this.typecode == JdbcParameterBean.DATE) {
            valuecode = "str_to_date('" + this.value + "','%Y-%m-%d')";
        } else if (this.typecode == JdbcParameterBean.DATETIME) {
            valuecode = "str_to_date('" + this.value + "','%Y-%m-%d %H:%i:%s')";
        }
        return this.name + " " + this.operator + " " + valuecode;
    }
}
