package com.apis.service;

import com.apis.dto.ProjectDTO;
import com.apis.dto.TaskDTO;
import com.apis.entity.Task;
import com.apis.entity.User;
import com.apis.enums.Status;
import com.apis.exception.TicketingProjectException;

import java.util.List;

public interface TaskService {
    TaskDTO findById(Long taskId);

    List<TaskDTO> listAllTasks();

    TaskDTO save(TaskDTO dto);

    void update(TaskDTO dto);

    void delete(Long taskId);

    int totalCountNonCompletedTasks(String projectCode);

    int totalCountCompletedTasks(String projectCode);

    List<TaskDTO> listAllTasksByProject(ProjectDTO project);

    void deleteByProject(ProjectDTO project);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);

    List<TaskDTO> listAllTasksByProjectManager() throws TicketingProjectException;

    void updateStatus(TaskDTO dto);

    List<TaskDTO> listAllTasksByStatus(Status status);

    List<TaskDTO> readAllByEmployee(User assignedEmployee);
}
