package com.apis.service;

import com.apis.dto.ProjectDTO;
import com.apis.entity.User;
import com.apis.exception.TicketingProjectException;

import java.util.List;

public interface ProjectService {
    ProjectDTO getByProjectDto(String code);
    List<ProjectDTO> listAllProjects();
    ProjectDTO save(ProjectDTO dto) throws TicketingProjectException;
    ProjectDTO update(ProjectDTO dto);
    void delete(String projectCode);

    ProjectDTO complete(String projectCode) throws TicketingProjectException;

    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> readAllByAssignedManager(User user);
    List<ProjectDTO> listAllNonCompletedProject();
}
