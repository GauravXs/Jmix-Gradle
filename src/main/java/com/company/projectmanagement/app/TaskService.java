package com.company.projectmanagement.app;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.Task;
import com.company.projectmanagement.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Stores;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.data.StoreAwareLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;


@Service
@Repository
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final CurrentAuthentication currentAuthentication;
   /* @Autowired
    private EntityManager entityManager;*/

    @Autowired
    private StoreAwareLocator storeAwareLocator;

    public TaskService(DataManager dataManager, CurrentAuthentication currentAuthentication) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
    }

    private final DataManager dataManager;

    public void createTask(Project project, String name, LocalDateTime startDate, Integer estimatedEfforts) {

        Task task = dataManager.create(Task.class);
        task.setProject(project);
        task.setName(name);
        task.setStartDate(startDate);
        task.setEstimatedEfforts(estimatedEfforts);
        task.setAssignee((User) currentAuthentication.getUser());

        dataManager.save(task);

    }



    public Object makeCount(){

        User loggedInUserCountTask = (User) currentAuthentication.getUser();

        EntityManager entityManager = storeAwareLocator.getEntityManager(Stores.MAIN);

        Query query = entityManager.createQuery("SELECT COUNT(e) FROM Task_ e WHERE e.assignee = :loggedInUserCountTask");
//        query.setParameter(1,loggedInUserCountTask.getId());
        query.setParameter("loggedInUserCountTask",loggedInUserCountTask);

        Long taskCount = (Long) query.getSingleResult();
        log.info("query::::::" +query.toString());
        log.info("taskCount:::" +taskCount);

        return taskCount;



    }


}

