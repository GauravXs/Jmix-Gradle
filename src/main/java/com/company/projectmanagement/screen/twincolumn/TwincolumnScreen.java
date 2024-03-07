package com.company.projectmanagement.screen.twincolumn;

import com.company.projectmanagement.entity.User;
import io.jmix.core.MetadataTools;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TwinColumn;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@UiController("TwincolumnScreen")
@UiDescriptor("twincolumn-screen.xml")
public class TwincolumnScreen extends Screen {
    @Autowired
    private TwinColumn<User> twinColumn;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MetadataTools metadataTools;

    @Subscribe("showValueButton")
    public void onShowValueButtonClick(final Button.ClickEvent event) {
        StringBuilder sb=new StringBuilder();
        Collection<User> value= twinColumn.getValue();
        if(value==null){
            sb.append("null");
        }
        else {
            for(User user : value){
                sb.append(metadataTools.getInstanceName(user))
                        .append("\n");
            }
        }
        notifications.create()
                .withCaption(sb.toString())
                .show();


    }


}