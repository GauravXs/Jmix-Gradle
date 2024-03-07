package com.company.projectmanagement.screen.bugerror;

import com.company.projectmanagement.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import io.jmix.core.Metadata;
import io.jmix.ui.Actions;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.BugError;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("BugError.browse")
@UiDescriptor("bug-error-browse.xml")
@LookupComponent("bugErrorsTable")
public class BugErrorBrowse extends StandardLookup<BugError> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private EntityPicker<Object> developerEntityPicker;
    @Autowired
    private InstanceContainer<BugError> bugErrorDc;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Metadata metadata;
    @Autowired
    private Actions actions;

    @Subscribe
    public void onInit(final InitEvent event) {

        BugError bugError=metadata.create(BugError.class);


        bugErrorDc.setItem(bugError);

        developerEntityPicker.addAction(new BaseAction("title")
                .withHandler(actionPerformedEvent -> {
                    User user = (User) developerEntityPicker.getValue();
                    if (user != null) {
                        notifications.create()
                                .withCaption(user.getFirstName())
                                .show();
                    } else {
                        notifications.create()
                                .withCaption("Choose a customer")
                                .show();
                    }
                })
                .withIcon("font-icon:USERS")
        );


        Action clearAction = actions.create(EntityClearAction.ID);
                    clearAction.setIcon("font-icon:BAN");
                    developerEntityPicker.addAction(clearAction);
                }

    @Subscribe("developerEntityPicker.greeting")
    public void onDeveloperEntityPickerGreeting(final Action.ActionPerformedEvent event) {
        User user = (User) developerEntityPicker.getValue();

        if(user != null){
            notifications.create()
                    .withCaption("Hello "+ user.getFirstName())
                    .show();
        }else {
            notifications.create()
                    .withCaption("Choose a Developer")
                    .show();
        }

    }




    }

