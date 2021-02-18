package com.apis.implementation;

import com.apis.dto.ProjectDTO;
import com.apis.dto.UserDTO;
import com.apis.entity.Project;
import com.apis.entity.User;
import com.apis.enums.Status;
import com.apis.exception.TicketingProjectException;
import com.apis.mapper.MapperUtil;
import com.apis.repository.ProjectRepository;
import com.apis.service.ProjectService;
import com.apis.service.TaskService;
import com.apis.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private UserService userService;
    private TaskService taskService;
    private MapperUtil mapperUtil;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, TaskService taskService, MapperUtil mapperUtil) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.taskService = taskService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProjectDTO getByProjectDto(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return mapperUtil.convert(project, new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects = projectRepository.findAll(Sort.by("projectCode"));
        return projects.stream()
                .map(obj -> mapperUtil.convert(obj, new ProjectDTO())).collect(Collectors.toList());

    }

    @Override
    public ProjectDTO save(ProjectDTO dto) throws TicketingProjectException {
        Project foundProject = projectRepository.findByProjectCode(dto.getProjectCode());

        if (foundProject != null) {
            throw new TicketingProjectException("Project with this code already exists");
        }

        Project obj = mapperUtil.convert(dto, new Project());

        Project createdProject = projectRepository.save(obj);

        return mapperUtil.convert(createdProject, new ProjectDTO());

    }

    @Override
    public ProjectDTO update(ProjectDTO dto) {
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());
        Project convertedProject = mapperUtil.convert(dto, new Project());
        convertedProject.setId(project.getId());
        convertedProject.setProjectStatus(project.getProjectStatus());
        projectRepository.save(convertedProject);

        return mapperUtil.convert(convertedProject, new ProjectDTO());
    }

    @Override
    public void delete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode); //get Entity from database
        project.setIsDeleted(true); //set isDeleted value true in the Entity
        project.setProjectCode("d-" + project.getProjectCode() + '-' + project.getId());
        projectRepository.save(project);//save the updated entity to data base
        taskService.deleteByProject(mapperUtil.convert(project, new ProjectDTO()));

    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO currentUserDTO = userService.findByUserName(username);

        User user = mapperUtil.convert(currentUserDTO, new User()); //user == manager
        List<Project> projectList = projectRepository.findAllByAssignedManager(user);

        return projectList.stream().map(proj -> {
            ProjectDTO obj = mapperUtil.convert(proj, new ProjectDTO());
            obj.setUnfinishedTaskCount(taskService.totalCountNonCompletedTasks(proj.getProjectCode()));
            obj.setCompletedTaskCount(taskService.totalCountCompletedTasks(proj.getProjectCode()));
            return obj;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User user) {

        List<Project> list = projectRepository.findAllByAssignedManager(user);
        return list.stream().map(obj -> mapperUtil.convert(obj, new ProjectDTO())).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> listAllNonCompletedProject() {
        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream().map(obj -> mapperUtil.convert(obj, new ProjectDTO())).collect(Collectors.toList());
    }
}
