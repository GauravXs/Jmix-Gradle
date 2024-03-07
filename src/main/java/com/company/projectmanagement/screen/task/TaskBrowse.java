package com.company.projectmanagement.screen.task;

import com.company.projectmanagement.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UiComponents uiComponents;

    /*@Install(to = "tasksTable.username", subject = "columnGenerator")
    private String tasksTableUsernameColumnGenerator(DataGrid.ColumnGeneratorEvent<User> columnGeneratorEvent) {
        Label label = uiComponents.create(Label.NAME);

        List<String> user = dataManager.load(User.class).query("s")
    }*/

   
    



    
    



}