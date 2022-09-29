package com.coding.test;

import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.services.CreateJdbcSQLService;
import com.coding.generation.services.TableConfigurationService;
import com.coding.jdbc.exception.SYHCException;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Map;

/**
 * 数据库表结构SQL语句业务逻辑单元测试类
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
public class CreateJdbcSQLServiceTest {

    public CreateJdbcSQLServiceTest() {
        super();
    }

    ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-core.xml");
    CreateJdbcSQLService external = (CreateJdbcSQLService) ctx.getBean("createJdbcSQLService");
    TableConfigurationService tableConfig = (TableConfigurationService) ctx.getBean("tableConfigurationService");

    public static void main(String[] args) {
        Configurator.initialize("log4j2", "classpath:code_generation_core_log4j2.xml");
        CreateJdbcSQLServiceTest test = new CreateJdbcSQLServiceTest();
        test.constractSql();
        System.exit(0);
    }

    /**
     * 生成目标库表JDBC业务SQL语句
     */
    public void constractSql() {
        String databaseName = "syhc_iot_basic";
        String tableName = "tb_basic_dictionary";
        String tableComment = "数字字典信息表";
        String defaultMethod[] = new String[]{"insert", "update", "updateDynamicField", "condition", "statistics"};
        Map<String, TableColumnBean> columnMap = null;
        try {
            columnMap = tableConfig.queryForColumnMap(tableName);
            for (TableColumnBean column : columnMap.values()) {
                if (column.getColumnName().equals("dictionaryId")) {
                    column.setPrimaryKey(true);
                    column.setRemoveMothod("deleteByDictionaryId");
                }
                if (column.getColumnName().equals("typeCode")) {
                    column.setListMothod("queryForListByTypeCode");
                }
                if (column.getColumnName().equals("classCode")) {
                    column.setBeanMothod("queryForBeanByClassCode");
                }
                if (column.getColumnName().equals("valueCode")) {
                    column.setBeanMothod("queryForListByClassCode");
                }
            }
            external.constractSql(databaseName, tableName, tableComment, columnMap, defaultMethod);
        } catch (SYHCException e) {
            e.printStackTrace();
        }
    }

}
