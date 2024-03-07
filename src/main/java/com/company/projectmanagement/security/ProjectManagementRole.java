package com.company.projectmanagement.security;

import com.company.projectmanagement.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "ProjectManagement", code = ProjectManagementRole.CODE)
public interface ProjectManagementRole {
    String CODE = "project-management";

    @EntityAttributePolicy(entityClass = Project.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Project.class, actions = EntityPolicyAction.ALL)
    void project();

    @EntityAttributePolicy(entityClass = ProjectStat.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ProjectStat.class, actions = EntityPolicyAction.ALL)
    void projectStat();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "parentTask", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = Task.class, attributes = {"id", "name", "startDate", "estimatedEfforts", "assignee", "version", "project", "caption", "endDate"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.ALL)
    void task();

    @EntityAttributePolicy(entityClass = TimeEntry.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TimeEntry.class, actions = EntityPolicyAction.ALL)
    void timeEntry();

    @EntityAttributePolicy(entityClass = User.class, attributes = {"username", "id", "version", "password", "active"}, action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"firstName", "lastName", "email"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @MenuPolicy(menuIds = {"User.browse", "Project.browse", "Task_.browse", "TimeEntry.browse", "ProjectStat.browse"})
    @ScreenPolicy(screenIds = {"User.browse", "Project.browse", "Task_.browse", "TimeEntry.browse", "ProjectStat.browse", "Project.edit", "Task_.edit", "TimeEntry.edit", "User.edit"})
    void screens();
}