package com.company.projectmanagement.screen.user;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.entity.User;
import io.jmix.core.EntityStates;
import io.jmix.core.FileRef;
import io.jmix.core.Metadata;
import io.jmix.core.security.event.SingleUserPasswordChangeEvent;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.w3c.dom.events.Event;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.TimeZone;

@UiController("User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
@Route(value = "users/edit", parentPrefix = "users")
public class UserEdit extends StandardEditor<User> {
    @Autowired
    private EntityStates entityStates;
    @Autowired
    private TextField<String> emailField;
    @Autowired
    private TextField<String> firstNameField;
    @Autowired
    private CheckBox activeField;
    @Autowired
    private TextField<String> lastNameField;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @Autowired
    private Notifications notifications;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private PasswordField confirmPasswordField;

    @Autowired
    private MessageBundle messageBundle;

    @Autowired
    private ComboBox<String> timeZoneField;

    @Autowired
    private ScreenValidation screenValidation;
    private boolean isNewEntity;
    @Autowired
    private Metadata metadata;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private FileStorageUploadField imageField;

    @Subscribe
    public void onInit(InitEvent event) {
        timeZoneField.setOptionsList(Arrays.asList(TimeZone.getAvailableIDs()));
    }

    //Use this event listener to initialize default values in the new entity instance
    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        usernameField.setEditable(true);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
        isNewEntity = true;
    }

    //
    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messageBundle.getMessage("passwordsDoNotMatch"))
                        .show();
                event.preventCommit();
            }
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));

        }

    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) {
        if (isNewEntity) {
            getApplicationContext().publishEvent(new SingleUserPasswordChangeEvent(getEditedEntity().getUsername(), passwordField.getValue()));
        }
    }

    @Subscribe("imageField")
    public void onImageFieldFileUploadSucceed(final SingleFileUploadField.FileUploadSucceedEvent event) {
        File file = temporaryStorage.getFile(imageField.getFileId());
        if (file != null) {
            notifications.create()
                    .withCaption("File is uploaded to temporary storage at " + file.getAbsolutePath())
                    .show();
        }

        FileRef fileRef=temporaryStorage.putFileIntoStorage(imageField.getFileId(),imageField.getFileName());
        imageField.setValue(fileRef);
        notifications.create()
                .withCaption("Uploaded file: " + imageField.getFileName())
                .show();

    }

    @Subscribe("imageField")
    public void onImageFieldFileUploadError(final UploadField.FileUploadErrorEvent event) {
        notifications.create()
                .withCaption("File upload error")
                .show();
    }

    @Subscribe("clearAction")
    public void onClearAction(final Action.ActionPerformedEvent event) {
        valueClear();
    }

    public void valueClear(){

        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        imageField.clear();
        timeZoneField.clear();
        activeField.clear();

    }



}