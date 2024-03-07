package com.company.projectmanagement.screen.user;

import com.company.projectmanagement.entity.User;
import io.jmix.core.DataManager;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.HasContextHelp;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.data.value.ContainerValueSource;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;


@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {

    @Autowired
    private GroupTable<User> usersTable;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(InitEvent event) {
        usersTable.addGeneratedColumn("image", entity -> {
            Image<User> image = uiComponents.create(Image.NAME);
            image.setValueSource(
                    new ContainerValueSource<>(
                            usersTable.getInstanceContainer(entity),
                            "image"));
            image.setHeight("100px");
            image.setScaleMode(Image.ScaleMode.CONTAIN);
            return image;
        });
    }

}