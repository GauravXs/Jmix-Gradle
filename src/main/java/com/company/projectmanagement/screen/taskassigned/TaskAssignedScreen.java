package com.company.projectmanagement.screen.taskassigned;

import com.company.projectmanagement.app.TaskService;
import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Tree;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@UiController("TaskAssignedScreen")
@UiDescriptor("task-assigned-screen.xml")
public class TaskAssignedScreen extends Screen {
    @Autowired
    private Tree<Task> tree;
    @Autowired
    private TaskService taskService;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Label<Object> showAssignee;
    @Autowired
    private Label<Object> showCount;
    @Autowired
    private Notifications notifications;
    @Autowired
    private CollectionContainer<Task> tasksDc;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Install(to = "tasksDl", target = Target.DATA_LOADER)
    private List<Task> tasksDlLoadDelegate(final LoadContext<Task> loadContext) {

        User loggedInUser = (User)currentAuthentication.getUser();

        List<Task> user = dataManager.load(Task.class)
                .query("select e from Task_ e where e.assignee = :loggedInUser")
                .parameter("loggedInUser",loggedInUser).list();

        showCount.setValue("("+taskService.makeCount()+")");
        return user;
    }


    @Install(to = "tree", subject = "descriptionProvider")
    private String treeDescriptionProvider(final Task task) {


        showAssignee.setValue("The assignee of the : "+task.getAssignee().getUsername()+" \ntasks : "+ task.getName()
        +"\nEstimated Efforts of task : "+task.getEstimatedEfforts());
        return "The assignee of the " + task.getAssignee().getUsername() +
                " task is " + task.getName();
        
    }


   
    



   
    
    

    

}