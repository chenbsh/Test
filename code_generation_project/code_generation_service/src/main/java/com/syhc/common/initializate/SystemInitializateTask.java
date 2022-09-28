package com.syhc.common.initializate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Repository;

/**
 * 系统启动时立即执行执行任务
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
@Repository
public class SystemInitializateTask<T> {

	public SystemInitializateTask() {
		super();
	}

	@PostConstruct
	public void initializate() {
	}

	@PreDestroy
	public void destory() {
	}
}