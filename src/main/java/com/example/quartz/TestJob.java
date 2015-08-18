package com.example.quartz;

import org.apache.log4j.Logger;

//使用quartz执行调度任务
public class TestJob {
	public static final Logger logger = Logger.getLogger(TestJob.class);

	public void doBiz() throws Exception {
		// logger.info("定时任务执行中…");
	}
}
