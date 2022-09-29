package com.coding.test;

import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.domain.TableCommentBean;
import com.coding.generation.services.TableConfigurationService;
import com.coding.jdbc.exception.SYHCException;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 数据库表结构信息业务逻辑单元测试类
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
public class TableConfigurationServiceTest {

    public TableConfigurationServiceTest() {
        super();
    }

    ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-core.xml");
    TableConfigurationService external = (TableConfigurationService) ctx.getBean("tableConfigurationService");

    public static void main(String[] args) {
        Configurator.initialize("log4j2", "classpath:code_generation_core_log4j2.xml");
        TableConfigurationServiceTest test = new TableConfigurationServiceTest();
//        test.queryForTableList();
//        test.queryForColumnList();
        test.queryForColumnMap();
        System.exit(0);
    }

    /**
     * 获取数据库表信息列表
     */
    public void queryForTableList() {
        List<TableCommentBean> tableList = null;
        try {
            tableList = external.queryForTableList();
            for (TableCommentBean table : tableList) {
                System.out.println(table.getTableName());
            }
        } catch (SYHCException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据表名称获取表结构基本信息
     */
    public void queryForColumnList() {
        String tableName = "tb_basic_role";
        List<TableColumnBean> columnList = null;
        try {
            columnList = external.queryForColumnList(tableName);
            for (TableColumnBean column : columnList) {
                System.out.println(column.formatJava());
            }
        } catch (SYHCException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据表名称获取表结构基本信息
     */
    public void queryForColumnMap() {
        String tableName = "tb_defect_distributor";
        Map<String, TableColumnBean> columnMap = null;
        try {
            columnMap = external.queryForColumnMap(tableName);
            for (String columnKey : columnMap.keySet()) {
                System.out.println(columnKey + " = " + columnMap.get(columnKey).getColumnComment());
            }
        } catch (SYHCException e) {
            e.printStackTrace();
        }
    }

}
