package com.company.projectmanagement.screen.bugs;

import com.company.projectmanagement.entity.BugError;
import groovy.transform.Undefined;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.icon.Icons;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import io.jmix.ui.theme.ThemeClassNames;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@UiController("BugsScreen")
@UiDescriptor("bugs-screen.xml")
public class BugsScreen extends Screen {

//    private Persistence persistence;
    @Autowired
    private GroupTable<BugError> BugsErrorTable;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Icons icons;
    @Autowired
    private DataManager dataManager;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
/*@Subscribe
    public void onInit(final InitEvent event) {

        try{
            BugsErrorTable.addGeneratedColumn("done",entity -> {
                Button doneBtn= uiComponents.create(Button.class);
                doneBtn.setCaption("Done");
                doneBtn.setStyleName(ThemeClassNames.BUTTON_PRIMARY);
                doneBtn.addClickListener( clickEvent -> {

                    String result = "Resolved";

                    //BugError bugs=dataManager.create(BugError.class);
                    BugError bugError = (BugError) entity;


                    Query query = entityManager.createNativeQuery("update BUG_ERROR  set status =?1 where id =?2");
                    query.setParameter(1, "Resolved");
                    query.setParameter(2, entity.getId());
                    query.executeUpdate();

                    *//*BugError bugStatusUpdate = dataManager.load(BugError.class).id(bugError.getId()).one();
                    //.query("update BUG_ERROR be set be.status = :status where be.id = :id")
                    bugStatusUpdate.setStatus(result);
                    dataManager.save(bugStatusUpdate);*//*


                });
                return doneBtn;
            });
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    @Install(to = "BugsErrorTable.done", subject = "columnGenerator")
    private Component bugsErrorTableDoneColumnGenerator(final BugError bugError) {

        try{
                Button doneBtn= uiComponents.create(Button.class);
                doneBtn.setCaption("Done");
                doneBtn.setStyleName(ThemeClassNames.BUTTON_PRIMARY);
                doneBtn.addClickListener( clickEvent -> {
                    dialogs.createOptionDialog()
                            .withCaption("Please Confirm")
                            .withMessage("Do you really want to edit the status?")
                            .withActions(
                                    new DialogAction(DialogAction.Type.YES)
                                            .withHandler(e -> {

                                                String result = "Resolved";

                                                BugError bugStatusUpdate = dataManager.load(BugError.class).id(bugError.getId()).one();
                                                bugStatusUpdate.setStatus(result);
                                                dataManager.save(bugStatusUpdate);
                                                notifications.create().withCaption(messageBundle.getMessage("key.status"))
                                                                .withType(Notifications.NotificationType.HUMANIZED)
                                                                        .show();
                                                getScreenData().loadAll();


                                                //})
                                                //     return doneBtn;
                                            }),
                                    new DialogAction(DialogAction.Type.NO)
                                            .withHandler(e -> {
                                                notifications.create().withCaption(messageBundle.getMessage("key.status.no"))
                                                        .withType(Notifications.NotificationType.HUMANIZED)
                                                        .show();

                                            })

                            )
                            .show();

                });
                return doneBtn;
        }

        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


        /*   BugsErrorTable.addGeneratedColumn("done",entity -> {
                Button doneBtn= uiComponents.create(Button.class);
                doneBtn.setCaption("Done");
                doneBtn.setStyleName(ThemeClassNames.BUTTON_PRIMARY);
                doneBtn.addClickListener( clickEvent -> {

                    //String result = "Resolved";

                    //BugError bugs=dataManager.create(BugError.class);
                    //BugError bugError = (BugError) entity;


                    Query query = entityManager.createNativeQuery("update BUG_ERROR  set status =?1 where id =?2");
                    query.setParameter(1, "Resolved");
                    query.setParameter(2, entity.getId());
                    query.executeUpdate();

                    *//*BugError bugStatusUpdate = dataManager.load(BugError.class).id(bugError.getId()).one();
                    //.query("update BUG_ERROR be set be.status = :status where be.id = :id")
                    bugStatusUpdate.setStatus(result);
                    dataManager.save(bugStatusUpdate);*//*





                });
                return doneBtn;
            });*/
/*
    String result = "Resolved";

    //BugError bugs=dataManager.create(BugError.class);
    //BugError bugError = (BugError) entity;
    BugError bugErrors = dataManager.create(BugError.class);

    BugError bugStatusUpdate = dataManager.load(BugError.class).id(bugError.getId()).one();
                    bugStatusUpdate.setStatus(result);
                    dataManager.save(bugStatusUpdate);

    getScreenData().loadAll();

                    *//*Query query = entityManager.createNativeQuery("update BUG_ERROR  set status =?1 where id =?2");
                    query.setParameter(1, "Resolved");
                    query.setParameter(2, bugErrors.getId());
                    query.executeUpdate();*//*
});
        return doneBtn;
        }
        catch(Exception e) {
        e.printStackTrace();
        }
        return null;
    */
}  