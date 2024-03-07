package com.company.projectmanagement.app;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.ProjectStat;
import com.company.projectmanagement.entity.Task;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectStatsService {

    private final DataManager dataManager;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStat> fetchProjectStatistics(){

        List<Project> projects = dataManager.load(Project.class).all().fetchPlan("project-with-tasks").list();
        List<ProjectStat> projectStats = projects.stream().map(project -> {
            //create ProjectStatistics entity using map operation
            ProjectStat Stat = dataManager.create(ProjectStat.class);
            Stat.setProjectId(project.getId());
            Stat.setProjectName(project.getName());
            Stat.setTaskCount(project.getTasks().size());
            //calculation logic using task data
            Integer estimatedEfforts = project.getTasks().stream().map(Task::getEstimatedEfforts).reduce(0, Integer::sum);

            Stat.setPlannedEfforts(estimatedEfforts);
            Stat.setActualEfforts(getActualEfforts(project.getId()));
            return Stat;
        }).collect(Collectors.toList());

        return projectStats;
    }

    public Integer getActualEfforts(UUID projectId){
        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te " +
                "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId",projectId)
                .one();
    }

}