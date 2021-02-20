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

    TaskDTO update(TaskDTO dto) throws TicketingProjectException;

    void delete(Long taskId) throws TicketingProjectException;

    int totalCountNonCompletedTasks(String projectCode);

    int totalCountCompletedTasks(String projectCode);

    List<TaskDTO> listAllTasksByProject(ProjectDTO project);

    void deleteByProject(ProjectDTO project);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status) throws TicketingProjectException;

    List<TaskDTO> listAllTasksByProjectManager() throws TicketingProjectException;

    TaskDTO updateStatus(TaskDTO dto) throws TicketingProjectException;

    //List<TaskDTO> listAllTasksByStatus(Status status);

    List<TaskDTO> readAllByEmployee(User assignedEmployee);
}
