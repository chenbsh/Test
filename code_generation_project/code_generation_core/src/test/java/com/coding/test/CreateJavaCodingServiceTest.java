package com.coding.test;

import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.services.CreateJavaCodingService;
import com.coding.generation.services.TableConfigurationService;
import com.coding.jdbc.exception.SYHCException;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Map;

public class CreateJavaCodingServiceTest {
    public CreateJavaCodingServiceTest() {
        super();
    }

    ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-core.xml");
    CreateJavaCodingService external = (CreateJavaCodingService) ctx.getBean("createJavaCodingService");
    TableConfigurationService columnExternal = (TableConfigurationService) ctx.getBean("tableConfigurationService");


    public static void main(String[] args) {
        Configurator.initialize("log4j2", "classpath:code_generation_core_log4j2.xml");
        CreateJavaCodingServiceTest test = new CreateJavaCodingServiceTest();
        String databaseName = "syhc_iot_basic";
        String tableName = "tb_defect_distributor";
        String tableComment = "原料供应商";
        String packageName = "com.syhc.defect";
        Map<String, TableColumnBean> columnMap = null;
        String defaultMethod[] = CreateJavaCodingServiceTest.getDefaultMethod();
        try {
            columnMap = test.columnExternal.queryForColumnMap(tableName);
            TableColumnBean column=columnMap.get("distributorId");
            column.setPrimaryKey(Boolean.TRUE);
            column.setBeanMothod("1");
            column.setRemoveMothod("1");

            column=columnMap.get("distributorName");
            column.setBeanMothod("1");

            column=columnMap.get("customerId");
            column.setListMothod("1");

            test.external.javaCoding(databaseName, tableName, tableComment, packageName, columnMap, defaultMethod);
        } catch (SYHCException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    private static String[] getDefaultMethod() {
        return new String[]{"insert","update","updateDynamicField","condition","statistics"};
    }

}
