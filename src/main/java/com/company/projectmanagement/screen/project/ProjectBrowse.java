package com.company.projectmanagement.screen.project;

import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.Project;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {

}