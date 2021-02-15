package com.apis.repository;

import com.apis.entity.Project;
import com.apis.entity.Task;
import com.apis.entity.User;
import com.apis.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //JP query
    @Query("select count(t) From Task t where t.project.projectCode=?1 and  t.status <> 'COMPLETE'")
    int totalCountNonCompletedTasks(String projectCode);

    //native query
    @Query(value = "select count(*)" +
            "from tasks t join projects p on t.project_id=p.id " +
            "Where p.project_code =?1 and t.status='COMPLETE'", nativeQuery = true)
    int totalCountCompletedTasks(String projectCode);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByStatusIsNotAndAssignedEmployee(Status status, User user);

    List<Task> findAllByProjectAssignedManager(User manager);

    List<Task> findAllByStatusAndAssignedEmployee(Status status, User user);

    List<Task> findAllByAssignedEmployee(User user);
}
