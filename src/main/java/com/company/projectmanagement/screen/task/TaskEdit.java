package com.company.projectmanagement.screen.task;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.component.validation.NotBlankValidator;
import io.jmix.ui.component.validation.SizeValidator;
import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.events.Event;

import java.time.LocalDateTime;

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {

    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TextField<Integer> estimatedEffortsField;
    @Autowired
    private Metadata metadata;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Dialogs dialogs;


    @Subscribe
    public void onBeforeCommitChanges(final BeforeCommitChangesEvent event) {
      /*  try {
        event.preventCommit();
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to edit the task?")

            .withActions(
                    new DialogAction(DialogAction.Type.YES)
                            .withHandler(e -> {

                                commitChanges(event);
                            }),
                    new DialogAction(DialogAction.Type.NO)
                            .withHandler(e -> {

                                event.preventCommit();
                            })
            )
            .show();

*/
        try{
            if (estimatedEffortsField.getValue() == null) {
                notifications.create().withCaption(messageBundle.getMessage("validation.fieldsNotFilled.message"))
                        .withType(Notifications.NotificationType.WARNING)
                        .show();
                event.preventCommit();
            }
            else if (estimatedEffortsField.getValue() <= 0) {
                notifications.create().withCaption(messageBundle.getMessage("validation.fieldsContainIncorrectValue.message"))
                        .withType(Notifications.NotificationType.WARNING)
                        .show();
                event.preventCommit();
            } else {
                event.preventCommit();
                dialogs.createOptionDialog()
                        .withCaption("Please confirm")
                        .withMessage("Do you really want to edit the task?")

                        .withActions(
                                new DialogAction(DialogAction.Type.YES)
                                        .withHandler(e -> {

                                            commitChanges(event);
                                        }),
                                new DialogAction(DialogAction.Type.NO)
                                        .withHandler(e -> {

                                            event.preventCommit();
                                        })
                        )
                        .show();

                                            //  event.resume();
            }
        }catch (Exception exception){
            event.preventCommit();
        }

    }



private void commitChanges(BeforeCommitChangesEvent event) {

    event.resume();
}
                /*
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(event.),
                        new DialogAction(DialogAction.Type.NO)
                                .withHandler(event.)
                );*/


}
