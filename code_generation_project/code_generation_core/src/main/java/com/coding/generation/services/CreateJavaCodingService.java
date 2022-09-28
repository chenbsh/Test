package com.coding.generation.services;

import com.coding.generation.domain.TableColumnBean;
import com.coding.generation.tools.CreateCodingFileTools;
import com.coding.jdbc.exception.SYHCException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

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
public class CreateJavaCodingService {

    @Resource
    private CreateCodingFileTools fileTools;

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

    @Resource
    private CreateContollerAPIService webAPILogic;

    @Resource
    private CreateContollerLogicService controllerLogic;

    @Resource
    private CreateExternalLogicService externalLogic;

    @Resource
    private CreateInternalLogicService internalLogic;

    @Resource
    private CreateJavaBeanService beanLogic;

    @Resource
    private CreateJdbcLogicService jdbcLogic;

    @Resource
    private CreateJdbcSQLService sqlLogic;

    @Resource
    private CreateJUnitTestLogicService junitTest;

    @Resource
    private CreateMainTestLogicService mainTest;

    /**
     * 生成Java代码【Bean、持久化、内部调用、外部调用、控制层】
     *
     * @param databaseName 库实例名称 @param tableName 数据表名称 @param tableComment 数据表名称注解 @param packageName 代码包名称 @param columnMap 库表列字段定义 @param defaultMethod 默认方法定义 @return Boolean true/false=成功/失败 @throws
     */
    public Boolean javaCoding(String databaseName, String tableName, String tableComment, String packageName, Map<String, TableColumnBean> columnMap, String defaultMethod[]) throws SYHCException {
        String table[] = tableName.split("_");
        String codingHead = null;
        for (int i = 2; i < table.length; i++) {
            if (codingHead == null) {
                codingHead = table[i].substring(0, 1).toUpperCase() + table[i].substring(1, table[i].length());
            } else {
                codingHead = codingHead + table[i].substring(0, 1).toUpperCase() + table[i].substring(1, table[i].length());
            }
        }
        sqlLogic.constractSql(databaseName, tableName, tableComment, columnMap, defaultMethod);

        String fileContent = beanLogic.javaBean(tableComment, packageName, codingHead, columnMap.values());
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "Bean.java");

        fileContent = jdbcLogic.persistence(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "JdbcService.java");

        fileContent = internalLogic.internal(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "InternalService.java");

        fileContent = externalLogic.external(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "ExternalService.java");

        fileContent = mainTest.test(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "ExternalServiceTest.java");

        fileContent = junitTest.test(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "ExternalServiceJunitTest.java");

        fileContent = controllerLogic.controllerService(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "ControllerService.java");

        fileContent = webAPILogic.webAPI(tableName, tableComment, packageName, codingHead, columnMap.values(), defaultMethod);
        fileTools.writeFile(fileContent, databaseName, tableName, codingHead + "Controller.java");

        return false;
    }

}
