package com.apis.service;

import com.apis.dto.ProjectDTO;
import com.apis.entity.Project;
import com.apis.entity.User;

import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode(String code);
    List<ProjectDTO> listAllProjects();
    Project save(ProjectDTO dto);
    void update(ProjectDTO dto);
    void delete(String code);

    void complete(String projectCode);

    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> readAllByAssignedManager(User user);
}
