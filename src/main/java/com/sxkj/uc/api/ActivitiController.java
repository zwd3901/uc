package com.sxkj.uc.api;

import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ActivitiController
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/27 0027
 **/
@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/hello")
    public MyResponse hello() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/simple.bpmn")
                .deploy();

        ProcessDefinition process = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();

        Map<String, Object> user = new HashMap<>(2);
        user.put("name", "haaaaa");

        ProcessInstance instance = runtimeService.startProcessInstanceById(process.getId(), "userKey1111");

        String processId = instance.getId();

        System.out.println("success,instance id is : " + processId);

        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();

        System.out.println("current task name is : " + task.getName());

        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();

        System.out.println("task is null,game over" + task);

        return MyResponseUtil.success();
    }
}
