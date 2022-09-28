package com.coding.generation.services;

import com.coding.generation.domain.TableColumnBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Collection;

/**
 * 业务逻辑内部调用类生成工具
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
public class CreateInternalLogicService {

    // 版权所有人
    @Value("${coding_copyright}")
    private String coding_copyright;

    // 工程项目
    @Value("${coding_project}")
    private String coding_project;

    // 代码编写工程师
    @Value("${coding_author}")
    private String coding_author;

    // JDK版本
    @Value("${coding_JDK_version}")
    private String coding_JDK_version;

    public String internal(String tableName, String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList, String defaultMethod[]) {
        Boolean conditionFlag = false;
        Boolean statisticsFlag = false;

        StringBuilder persistence = new StringBuilder("package " + packageName + ".internal;\n\n");
        persistence.append("import " + packageName + ".domain." + codingHead + "Bean;\n");

        persistence.append("import " + packageName + ".jdbc." + codingHead + "JdbcService;\n");
        persistence.append("import com.syhc.jdbc.domain.JdbcParameterBean;\n");
        persistence.append("import com.syhc.jdbc.exception.SYHCIoTException;\n");
        persistence.append("import com.syhc.common.utils.GeneralUtilTools;\n");
        persistence.append("import com.syhc.common.constant.SYHCIoTExceptionEnum;\n");
        persistence.append("import org.apache.commons.lang3.StringUtils;\n");
        persistence.append("import org.springframework.stereotype.Repository;\n");
        persistence.append("import javax.annotation.Resource;\n");
        persistence.append("import java.util.ArrayList;\n");
        persistence.append("import java.util.List;\n\n");


        persistence.append("/**\n");
        persistence.append(tableComment + "业务逻辑内部调用类\n");
        persistence.append("*\n");
        persistence.append("* @Copyright " + this.coding_copyright + "\n");
        persistence.append("* @Project " + this.coding_project + "\n");
        persistence.append("* @Author " + this.coding_author + "\n");
        persistence.append("* @timer " + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd") + "\n");
        persistence.append("* @Version 1.0.0\n");
        persistence.append("* @DK version used " + this.coding_JDK_version + "\n");
        persistence.append("* @Modification history none\n");
        persistence.append("* @Modified by none\n");
        persistence.append("*/\n\n");

        persistence.append("@Repository\n");
        persistence.append("public class " + codingHead + "InternalService {\n\n");

        persistence.append("public " + codingHead + "InternalService() {\n");
        persistence.append("super();\n");
        persistence.append("}\n\n");

        persistence.append("@Resource\n");
        persistence.append("private " + codingHead + "JdbcService jdbc;\n\n");

        if (defaultMethod != null) {
            for (String method : defaultMethod) {
                if (method.equals("insert")) {
                    persistence.append(this.internalInsert(tableComment, codingHead, columnList));
                    persistence.append("\n\n");
                } else if (method.equals("insertBatch")) {
                    persistence.append(this.internalInsertBatch(tableComment, codingHead, columnList));
                    persistence.append("\n\n");
                } else if (method.equals("update")) {
                    persistence.append(this.internalUpdate(tableComment, codingHead, columnList));
                    persistence.append("\n\n");
                } else if (method.equals("condition")) {
                    conditionFlag = true;
                } else if (method.equals("statistics")) {
                    statisticsFlag = true;
                }
            }
        }

        // 生成delete方法
        for (TableColumnBean column : columnList) {
            if (StringUtils.isNotBlank(column.getRemoveMothod())) {
                persistence.append(this.internalDelete(tableComment, codingHead, column));
                persistence.append("\n\n");
            }
        }
        // 生成queryForBean方法
        for (TableColumnBean column : columnList) {
            if (StringUtils.isNotBlank(column.getBeanMothod())) {
                persistence.append(this.internalBean(tableComment, codingHead, column));
                persistence.append("\n\n");
            }
        }
        // 生成queryForList方法
        for (TableColumnBean column : columnList) {
            if (StringUtils.isNotBlank(column.getListMothod())) {
                persistence.append(this.internalList(tableComment, codingHead, column));
                persistence.append("\n\n");
            }
        }

        if (conditionFlag) {
            persistence.append(this.internalCondition(tableComment, codingHead));
            persistence.append("\n\n");
        }
        if (statisticsFlag) {
            persistence.append(this.internalStatistics(tableComment, codingHead));
            persistence.append("\n\n");
        }
        if (conditionFlag) {
            persistence.append(this.internalConstract(tableComment, codingHead, columnList));
            persistence.append("\n\n");
        }

        persistence.append("}");
        return persistence.toString();
    }

    private String internalInsert(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
        String PrimaryKey = null;
        for (TableColumnBean column : columnList) {
            if (column.getPrimaryKey()) {
                PrimaryKey = column.formatJava();
                break;
            }
        }
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("新增" + tableComment + "信息实例\n");
        insert.append("*\n");
        insert.append("@param entity " + tableComment + "信息实例\n");
        insert.append("@return Boolean true:false=成功:失败\n");
        insert.append("@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public Boolean insert(" + codingHead + "Bean entity) throws SYHCIoTException {\n");
        // 唯一性校验
        for (TableColumnBean column : columnList) {
            String columnName = column.formatJava();
            if (column.getPrimaryKey() == false && StringUtils.isNotBlank(column.getBeanMothod())) {
                insert.append(codingHead + "Bean checkUnique = this.queryForBeanBy" + columnName + "(entity.get" + columnName + "());\n");
                insert.append("if (checkUnique != null && StringUtils.isNotBlank(checkUnique.get" + PrimaryKey + "())) {\n");
                insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_EXIST.getCode(), \"" + column.getColumnComment() + "已存在 [\" + entity.get" + columnName + "() + \"]\");\n");
                insert.append("}\n");
            }
        }
        // 设置主键
        insert.append("if (StringUtils.isBlank(entity.get" + PrimaryKey + "())) {\n");
        insert.append("entity.set" + PrimaryKey + "(GeneralUtilTools.getPrimaryKey());\n");
        insert.append("}\n");

        insert.append("return jdbc.insert(entity);\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalInsertBatch(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("批量新增" + tableComment + "信息实例\n");
        insert.append("*\n");
        insert.append("*@param entityList " + tableComment + "信息实例集合\n");
        insert.append("*@return Boolean true:false=成功:失败\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public Boolean insert(List <" + codingHead + "Bean> entityList) throws SYHCIoTException {\n");
        insert.append("for (" + codingHead + "Bean entity : entityList) {\n");
        for (TableColumnBean column : columnList) {
            if (column.getPrimaryKey()) {
                String columnName = column.formatJava();
                insert.append("if (StringUtils.isBlank(entity.get" + columnName + "())) {\n");
                insert.append("entity.set" + columnName + "(GeneralUtilTools.getPrimaryKey());\n");
                insert.append("}\n");
            }
        }
        insert.append("}\n");
        insert.append("return jdbc.insert(entityList);\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalUpdate(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("更新" + tableComment + "信息实例\n");
        insert.append("*\n");
        insert.append("@param entity " + tableComment + "信息实例\n");
        insert.append("@return Boolean true:false=成功:失败\n");
        insert.append("@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public Boolean update(" + codingHead + "Bean entity) throws SYHCIoTException {\n");

        String primaryKey = null;
        for (TableColumnBean column : columnList) {
            if (column.getPrimaryKey()) {
                primaryKey = column.formatJava();
            }
        }
        if (StringUtils.isNotBlank(primaryKey)) {
            // 唯一性校验
            for (TableColumnBean column : columnList) {
                String columnName = column.formatJava();
                if (column.getPrimaryKey() == false && StringUtils.isNotBlank(column.getBeanMothod())) {
                    insert.append(codingHead + "Bean checkUnique = this.queryForBeanBy" + columnName + "(entity.get" + columnName + "());\n");
                    insert.append("if (checkUnique != null && checkUnique.get" + primaryKey + "().equals(entity.get" + primaryKey + "()) == false) {\n");
                    insert.append("throw new SYHCIoTException(SYHCIoTExceptionEnum.OPERATION_CODE_EXIST.getCode(), \"" + column.getColumnComment() + "已存在 [\" + entity.get" + columnName + "() + \"]\");\n");
                    insert.append("}\n");
                }
            }
        }

        insert.append("return jdbc.update(entity);\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalDelete(String tableComment, String codingHead, TableColumnBean column) {
        String mothedName = "deleteBy" + column.formatJava();

        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("根据" + column.getColumnComment() + "删除" + tableComment + "信息实例\n");
        insert.append("*\n");
        insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
        insert.append("*@return Boolean true:false=成功:失败\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public Boolean " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") throws SYHCIoTException {\n");
        insert.append("return jdbc." + mothedName + "(" + column.getColumnName() + ");\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalBean(String tableComment, String codingHead, TableColumnBean column) {
        String mothedName = "queryForBeanBy" + column.formatJava();
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例\n");
        insert.append("*\n");
        insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
        insert.append("*@return " + codingHead + "Bean entity " + tableComment + "信息实例\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public " + codingHead + "Bean " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") throws SYHCIoTException {\n");
        insert.append("return jdbc." + mothedName + "(" + column.getColumnName() + ");\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalList(String tableComment, String codingHead, TableColumnBean column) {
        String mothedName = "queryForListBy" + column.formatJava();

        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("根据" + column.getColumnComment() + "获取" + tableComment + "信息实例集合\n");
        insert.append("*\n");
        insert.append("*@param " + column.getColumnName() + " " + column.getColumnComment() + "\n");
        insert.append("*@return List<" + codingHead + "Bean> entityList " + tableComment + "信息实例集合\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public List<" + codingHead + "Bean> " + mothedName + "(" + column.getJavaSimpleType() + " " + column.getColumnName() + ") throws SYHCIoTException {\n");
        insert.append("return jdbc." + mothedName + "(" + column.getColumnName() + ");\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalCondition(String tableComment, String codingHead) {
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("根据动态查询条件获取" + tableComment + "信息实例集合\n");
        insert.append("*\n");
        insert.append("*@param paramList 动态查询条件定义\n");
        insert.append("*@param startIndex 查询起始记录行号\n");
        insert.append("*@param pageSize 一次读取记录数\n");
        insert.append("*@param countFlag  是否统计关联记录【true=需要:false=不需要】\n");
        insert.append("*@return List<" + codingHead + "Bean> entityList 满足条件的" + tableComment + "信息实例集合\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public List<" + codingHead + "Bean> queryForListByCondition(List <JdbcParameterBean> paramList, int startIndex, int pageSize, Boolean countFlag) throws SYHCIoTException {\n");
        insert.append("List<" + codingHead + "Bean> resultList = jdbc.queryForListByCondition(paramList,startIndex,pageSize);\n");
        insert.append("if (countFlag) {\n");
        insert.append(" this.setPageDeleteFlag(resultList);\n");
        insert.append("}\n");
        insert.append("return resultList;\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalStatistics(String tableComment, String codingHead) {
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("根据动态查询条件统计" + tableComment + "信息实例记录数\n");
        insert.append("*\n");
        insert.append("*@param paramList 动态查询条件定义\n");
        insert.append("*@return Integer totalno 满足条件的" + tableComment + "信息实例记录条数\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public Integer statisticsForListByCondition(List <JdbcParameterBean> paramList) throws SYHCIoTException {\n");
        insert.append("return jdbc.statisticsForListByCondition(paramList);\n");
        insert.append("}\n\n");
        return insert.toString();
    }

    private String internalConstract(String tableComment, String codingHead, Collection<TableColumnBean> columnList) {
        StringBuilder insert = new StringBuilder("/**\n");
        insert.append("构建" + tableComment + "动态查询参数条件语句\n");
        insert.append("*\n");
        insert.append("*@param search 动态查询条件实例\n");
        insert.append("*@return List<JdbcParameterBean> parameterList 动态查询条件集合\n");
        insert.append("*@throws SYHCIoTException\n");
        insert.append("*/\n");
        insert.append("public List <JdbcParameterBean> constract(" + codingHead + "Bean search) {\n");
        insert.append("List <JdbcParameterBean> paramList = null;\n");
        insert.append("if (search != null) {\n");
        insert.append("paramList = new ArrayList <JdbcParameterBean>();\n");
        String columnName = null;
        String mothedName = null;
        for (TableColumnBean column : columnList) {
            if (column.getSearchkey()) {
                columnName = column.getColumnName();
                mothedName = "get" + column.formatJava();
                if (column.getJavaType().equals(Integer.class.getName()) || column.getJavaType().equals(Long.class.getName()) || column.getJavaType().equals(Float.class.getName()) || column.getJavaType().equals(Double.class.getName())) {
                    insert.append("if (search." + mothedName + "()!=null) {\n");
                    insert.append("paramList.add(new JdbcParameterBean(\"t." + columnName + "\",search." + mothedName + "().toString(), JdbcParameterBean.NUMERIC, JdbcParameterBean.EQUALS, JdbcParameterBean.AND));\n");
                    insert.append("}\n");
                } else if (column.getColumnType().equals("DATE")) {
                    insert.append("if (StringUtils.isNotBlank(search." + mothedName + "()) && search." + mothedName + "().length()==10) {\n");
                    insert.append("paramList.add(new JdbcParameterBean(\"t." + columnName + "\",search." + mothedName + "(), JdbcParameterBean.DATE, JdbcParameterBean.EQUALS, JdbcParameterBean.AND));\n");
                    insert.append("}\n");
                } else if (column.getColumnType().equals("DATETIME")) {
                    insert.append("if (StringUtils.isNotBlank(search." + mothedName + "()) && search." + mothedName + "().length()==19) {\n");
                    insert.append("paramList.add(new JdbcParameterBean(\"t." + columnName + "\",search." + mothedName + "(), JdbcParameterBean.DATETIME, JdbcParameterBean.EQUALS, JdbcParameterBean.AND));\n");
                    insert.append("}\n");
                } else {
                    insert.append("if (StringUtils.isNotBlank(search." + mothedName + "())) {\n");
                    insert.append("paramList.add(new JdbcParameterBean(\"t." + columnName + "\",search." + mothedName + "(), JdbcParameterBean.STRING, JdbcParameterBean.EQUALS, JdbcParameterBean.AND));\n");
                    insert.append("}\n");
                }
            }
        }
        insert.append("}\n");
        insert.append("return paramList;\n");
        insert.append("}\n\n");
        return insert.toString();
    }
}
