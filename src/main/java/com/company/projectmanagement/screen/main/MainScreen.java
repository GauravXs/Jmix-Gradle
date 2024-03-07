package com.company.projectmanagement.screen.main;

import com.company.projectmanagement.app.TaskService;
import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.Task;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private CollectionLoader<Project> projectsDl;
    @Autowired
    private CollectionContainer<Task> tasksDc;
    @Autowired
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DateField<LocalDateTime> dateSelector;
    @Autowired
    private EntityComboBox<Project> projectSelector;
    @Autowired
    private TextField<String> nameSelector;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private TextField<Integer> estimateSelector;
    @Autowired
    private Calendar<LocalDateTime> tasksCalendar;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }

    @Subscribe("refreshAction")
    public void onRefreshAction(final Action.ActionPerformedEvent event) {
        projectsDl.load();
        tasksDl.load();
        
    }

    @Subscribe("addTaskAction")
    public void onAddTaskAction(final Action.ActionPerformedEvent event) {
        if(projectSelector.getValue() == null || nameSelector.getValue() == null || dateSelector.getValue() == null || estimateSelector.getValue() == null){
            notifications.create().withCaption(messageBundle.getMessage("validation.fieldsNotFilled.message"))
                    .withType(Notifications.NotificationType.WARNING)
                    .show();

            projectSelector.focus();
            return;
        }
        taskService.createTask(projectSelector.getValue(),nameSelector.getValue(),dateSelector.getValue(),estimateSelector.getValue());
        tasksDl.load();

        projectSelector.clear();
        nameSelector.clear();
        dateSelector.clear();
        estimateSelector.clear();
    }

    @Subscribe("tasksCalendar")
    public void onTasksCalendarCalendarEventClick(final Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        Task task = (Task) event.getEntity();

        Screen screen= screenBuilders.editor(Task.class,this)
                .editEntity(task)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        screen.addAfterCloseListener(afterCloseEvent -> {
            if(afterCloseEvent.closedWith(StandardOutcome.COMMIT)){
                tasksDl.load();
            }
        });
        
        screen.show();
    }

   /* @Subscribe("navigatorPrevious")
    public void onNavigatorPreviousClick(Button.ClickEvent event) {
        LocalDateTime currentDate = tasksCalendar.getStartDate();


        LocalDateTime pastMonthsDate = currentDate.minusMonths(1);


        tasksCalendar.setStartDate(pastMonthsDate);

    }

    @Subscribe("navigatorNext")
    protected void onNavigatorNextClick(Button.ClickEvent event) {
        LocalDateTime currentNextDate = tasksCalendar.getStartDate();


        LocalDateTime nextMonthsDate = currentNextDate.plusMonths(1);


        tasksCalendar.setStartDate(nextMonthsDate);
    }
*/



}
