package com.company.projectmanagement.screen.project;

import com.company.projectmanagement.entity.User;
import com.vaadin.server.FontAwesome;
import io.jmix.core.FileRef;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.FileMultiUploadField;
import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.Project;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private ComboBox<FontAwesome> uploadDocument;
    @Autowired
    private Notifications notifications;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @Autowired
    private FileMultiUploadField fileMultiUploadField;


    @Subscribe
    public void onInit(final InitEvent event) {
        Map<String, FontAwesome> map = new HashMap<>();
        map.put("Archive file", FontAwesome.FILE_ARCHIVE_O);
        map.put("PDF file", FontAwesome.FILE_PDF_O);
        map.put("TXT file", FontAwesome.FILE_TEXT_O);
        uploadDocument.setOptionsMap(map);

        fileMultiUploadField.addQueueUploadCompleteListener(queueUploadCompleteEvent -> {
            for (Map.Entry<UUID, String> entry : fileMultiUploadField.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, fileName);
            }
            notifications.create()
                    .withCaption("Uploaded files: " + fileMultiUploadField.getUploadsMap().values())
                    .show();
            fileMultiUploadField.clearUploads();
        });
        fileMultiUploadField.addFileUploadErrorListener(queueFileUploadErrorEvent ->
                notifications.create()
                        .withCaption("File upload error")
                        .show());
    }

    @Install(to = "uploadDocument", subject = "optionIconProvider")
    private String uploadDocumentOptionIconProvider(FontAwesome icon) {
        return "font-icon:" + icon;
    }
    
    



    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        event.getEntity().setManager((User) currentAuthentication.getUser());

    }






}