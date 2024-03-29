package com.company.projectmanagement.screen.projectstat;

import com.company.projectmanagement.app.ProjectStatsService;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.ProjectStat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@UiController("ProjectStat.browse")
@UiDescriptor("project-stat-browse.xml")
@LookupComponent("projectStatsTable")
public class ProjectStatBrowse extends StandardLookup<ProjectStat> {

    @Autowired
    private ProjectStatsService projectStatsService;

    @Install(to = "projectStatsDl", target = Target.DATA_LOADER)
    private List<ProjectStat> projectStatsDlLoadDelegate(LoadContext<ProjectStat> loadContext) {
        // Here you can load entities from an external store
        return projectStatsService.fetchProjectStatistics();
    }
}