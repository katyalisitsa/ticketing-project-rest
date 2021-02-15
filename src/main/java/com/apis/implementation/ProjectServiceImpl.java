package com.apis.implementation;

import com.apis.dto.ProjectDTO;
import com.apis.dto.UserDTO;
import com.apis.entity.Project;
import com.apis.entity.User;
import com.apis.enums.Status;
import com.apis.mapper.ProjectMapper;
import com.apis.mapper.UserMapper;
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
    private ProjectMapper projectMapper;
    private ProjectRepository projectRepository;
    private UserMapper userMapper;
    private UserService userService;
    private TaskService taskService;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectRepository projectRepository, UserMapper userMapper
            ,  UserService userService, TaskService taskService) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectDto(String code) {
        Project project=projectRepository.findByProjectCode(code);
        return projectMapper.convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects=projectRepository.findAll(Sort.by("projectCode"));
        return projects.stream()
                .map(projectMapper::convertToDTO).collect(Collectors.toList());
//        return projects.stream()
//                .map(prj->{
//                    return projectMapper.convertToDTO(prj);
//                }).collect(Collectors.toList());

    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        Project obj= projectMapper.convertToEntity(dto);
//        obj.setAssignedManager(userMapper.convertToEntity(dto.getAssignedManager()));
        projectRepository.save(obj);

    }

    @Override
    public void update(ProjectDTO dto) {
        Project project=projectRepository.findByProjectCode(dto.getProjectCode());
        Project convertedProject=projectMapper.convertToEntity(dto);
        convertedProject.setId(project.getId());
        convertedProject.setProjectStatus(project.getProjectStatus());
        projectRepository.save(convertedProject);
    }

    @Override
    public void delete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode); //get Entity from database
        project.setIsDeleted(true); //set isDeleted value true in the Entity
        // by changing project code, user can create a project with same project code
        project.setProjectCode("d-"+project.getProjectCode()+'-'+project.getId());
        projectRepository.save(project);//save the updated entity to data base
        taskService.deleteByProject(projectMapper.convertToDTO(project));

    }

    @Override
    public void complete(String projectCode) {
        Project project=projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO currentUserDTO = userService.findByUserName(username);

        //get manager, hard coded it for now, won't be needed when implement security
//        UserDTO currentUserDTO = userService.findByUserName("msmith@test.com");
        User user = userMapper.convertToEntity(currentUserDTO); //user == manager
        List<Project> projectList=projectRepository.findAllByAssignedManager(user);

        return projectList.stream().map(proj->{
            ProjectDTO obj=projectMapper.convertToDTO(proj);
            obj.setUnfinishedTaskCount(taskService.totalCountNonCompletedTasks(proj.getProjectCode()));
            obj.setCompletedTaskCount(taskService.totalCountCompletedTasks(proj.getProjectCode()));
            return obj;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User user) {

        List<Project> list=projectRepository.findAllByAssignedManager(user);
        return list.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> listAllNonCompletedProject() {
        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }
}
