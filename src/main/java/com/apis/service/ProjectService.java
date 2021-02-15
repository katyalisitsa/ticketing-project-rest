package com.apis.service;

import com.apis.dto.ProjectDTO;
import com.apis.entity.User;

import java.util.List;

public interface ProjectService {
    ProjectDTO getByProjectDto(String code);
    List<ProjectDTO> listAllProjects();
    void save(ProjectDTO dto);
    void update(ProjectDTO dto);
    void delete(String projectCode);

    void complete(String projectCode);

    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> readAllByAssignedManager(User user);
    //controller is calling service, so it is ProjectDTO
    List<ProjectDTO> listAllNonCompletedProject();
}
