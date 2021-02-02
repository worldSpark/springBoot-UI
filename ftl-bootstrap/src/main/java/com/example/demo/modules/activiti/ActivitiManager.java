package com.example.demo.modules.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工作流测试类
 * @author qinjianping
 */
public class ActivitiManager {
    private static final Logger logger= LoggerFactory.getLogger(ActivitiManager.class);

    public static void main(String[]args){
        logger.info("启动我们的程序");
        //创建流程引擎
        ProcessEngineConfiguration cfg=ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

        ProcessEngine processEngine = cfg.buildProcessEngine();
        String name=processEngine.getName();
        String version=processEngine.VERSION;
        logger.info("流程引擎名称{},版本{}",name,version);

        //部署流程定义文件
        RepositoryService repositoryService=processEngine.getRepositoryService();

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("Process_1.xml");
        Deployment deploy = deploymentBuilder.deploy();
        String deployId = deploy.getId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        logger.info("流程定义文件，流程ID{}",deploy);
        //启动运行流程

        //处理流程任务


        logger.info("结束我们的程序");
    }
}
