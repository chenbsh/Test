package com.coding.generation.services;

import com.coding.generation.domain.TableColumnBean;
import com.coding.jdbc.tools.GeneralUtilTools;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Collection;

/**
 * 数据库表结构对应JavaBean生成工具
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
public class CreateJavaBeanService {

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

    public String javaBean(String tableComment, String packageName, String codingHead, Collection<TableColumnBean> columnList) {
        StringBuilder bean = new StringBuilder("package " + packageName + ".domain;\n\n");
        bean.append("import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;\n");
        bean.append("import com.syhc.jdbc.domain.BasicBean;\n");

        bean.append("import lombok.Data;\n");
        bean.append("import lombok.EqualsAndHashCode;\n");
        bean.append("import lombok.experimental.Accessors;\n");


        bean.append("/**\n");
        bean.append("* " + tableComment + "信息实体JavaBean\n");
        bean.append("* @Copyright " + this.coding_copyright + "\n");
        bean.append("* @Project " + this.coding_project + "\n");
        bean.append("* @Author " + this.coding_author + "\n");
        bean.append("* @timer " + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd") + "\n");
        bean.append("* @Version 1.0.0\n");
        bean.append("* @DK version used " + this.coding_JDK_version + "\n");
        bean.append("* @Modification history none\n");
        bean.append("* @Modified by none\n");
        bean.append("*/\n\n");

        bean.append("@Data\n");
        bean.append("@Accessors(chain = true)\n");
        bean.append("@EqualsAndHashCode(callSuper = false)\n");

        bean.append("public class " + codingHead + "Bean extends BasicBean {\n\n");
        bean.append("\n");
        bean.append("private static final long serialVersionUID = " + System.currentTimeMillis()
                + GeneralUtilTools.getRandomNumericString(4) + "L;");
        bean.append("\n");
        bean.append("public " + codingHead + "Bean() {\n");
        bean.append("super();\n");
        bean.append("}\n\n");

        for (TableColumnBean column : columnList) {
            if (column.getColumnName().toLowerCase().equals("keyword".toLowerCase())
                    || column.getColumnName().toLowerCase().equals("remark".toLowerCase())
                    || column.getColumnName().toLowerCase().equals("operatorId".toLowerCase())
                    || column.getColumnName().toLowerCase().equals("userName".toLowerCase())
                    || column.getColumnName().toLowerCase().equals("realName".toLowerCase())
                    || column.getColumnName().toLowerCase().equals("insertTime".toLowerCase())
                    || column.getColumnName().toLowerCase().equals("updateTime".toLowerCase())) {
                continue;
            }
            bean.append("/**" + column.getColumnComment() + "*/\n");
            bean.append("private " + column.getJavaSimpleType() + " " + column.getColumnName() + ";\n\n");
        }

        bean.append("\n\n//*****************************************非数据库字段 ***********************************\n");

        for (TableColumnBean column : columnList) {
            if (StringUtils.isNotBlank(column.getColumnNameAlias())) {
                bean.append("/**" + column.getColumnComment().replaceAll("序号", "").replaceAll("编号", "") + "名称*/\n");
                bean.append("private String " + column.getColumnNameAlias() + ";\n\n");
            }
        }

        bean.append("\n\n//*****************************************JSON过滤器 ***********************************\n");
        bean.append("/**列表字段过滤器*/\n");
        bean.append("public static SimplePropertyPreFilter listFilter(){\n");
        bean.append("SimplePropertyPreFilter filter = getFilter();\n");

        for (TableColumnBean column : columnList) {
            if (column.getDisplayList()) {
                bean.append("filter.getIncludes().add(\"" + column.getColumnName() + "\");\n");
                if (StringUtils.isNotBlank(column.getColumnNameAlias())) {
                    bean.append("filter.getIncludes().add(\"" + column.getColumnNameAlias() + "\");\n");
                }
            }
        }

        bean.append("return filter;\n");
        bean.append("}\n\n");
        bean.append("/**表单字段过滤器*/\n");
        bean.append("public static SimplePropertyPreFilter formFilter(){\n");
        bean.append("SimplePropertyPreFilter filter = getFilter();\n");
        bean.append("filter.getExcludes().add(\"keyword\");\n");
        bean.append("filter.getExcludes().add(\"operatorId\");\n");
        bean.append("filter.getExcludes().add(\"userName\");\n");
        bean.append("filter.getExcludes().add(\"realName\");\n");
        bean.append("filter.getExcludes().add(\"insertTime\");\n");
        bean.append("filter.getExcludes().add(\"updateTime\");\n");

        for (TableColumnBean column : columnList) {
            if (column.getColumnName().equals("keyword") || column.getColumnName().equals("operatorId")
                    || column.getColumnName().equals("userName") || column.getColumnName().equals("realName")
                    || column.getColumnName().equals("insertTime") || column.getColumnName().equals("updateTime")) {
                continue;
            }
            if (column.getDisplayForm()) {
                bean.append("filter.getExcludes().add(\"" + column.getColumnName() + "\");\n");
                if (StringUtils.isNotBlank(column.getColumnNameAlias())) {
                    bean.append("filter.getExcludes().add(\"" + column.getColumnNameAlias() + "\");\n");
                }
            }
        }

        bean.append("return filter;\n");
        bean.append("}\n\n");

        bean.append("}\n");

        return bean.toString();
    }

}
