package com.apis.implementation;

import com.apis.repository.TaskRepository;
import com.apis.repository.UserRepository;
import com.apis.dto.ProjectDTO;
import com.apis.dto.TaskDTO;
import com.apis.entity.Task;
import com.apis.entity.User;
import com.apis.enums.Status;
import com.apis.mapper.ProjectMapper;
import com.apis.mapper.TaskMapper;
import com.apis.service.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskMapper taskMapper;
    private TaskRepository taskRepository;
    private ProjectMapper projectMapper;
    private UserRepository userRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO findById(Long taskId) {
        Optional<Task> task=taskRepository.findById(taskId);
        if (task.isPresent()){
            return taskMapper.converToDTO(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks=taskRepository.findAll();
//        List<Task> tasks=taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::converToDTO).collect(Collectors.toList());
//        return tasks.stream()
//                .map(task-> {
//                    return taskMapper.converToDTO(task);
//                }).collect(Collectors.toList());

    }

    @Override
    public Task save(TaskDTO dto) {
        dto.setStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task=taskMapper.convertToEntity(dto);

        return taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO dto) {
        Optional <Task> task=taskRepository.findById(dto.getId());
        Task convertedTask=taskMapper.convertToEntity(dto);
        if(task.isPresent()){
            convertedTask.setId(task.get().getId());
            convertedTask.setStatus(task.get().getStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }
    }

    @Override
    public void delete(Long taskId) {
       Optional<Task> foundTask=taskRepository.findById(taskId);
//       Task foundTask= taskRepository.findById(taskId).get();
       if (foundTask.isPresent()){
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
       }

    }

    @Override
    public int totalCountNonCompletedTasks(String projectCode) {

        return taskRepository.totalCountNonCompletedTasks(projectCode);

    }

    @Override
    public int totalCountCompletedTasks(String projectCode) {

        return taskRepository.totalCountCompletedTasks(projectCode);
    }


    @Override
    public void deleteByProject(ProjectDTO project) {
        List<TaskDTO> taskDTOS=listAllTasksByProject(project);
        taskDTOS.forEach(taskDTO -> delete(taskDTO.getId()));


    }


    @Override
    public List<TaskDTO> listAllTasksByProject(ProjectDTO project) {
        List<Task> list=taskRepository.findAllByProject(projectMapper.convertToEntity(project));

        return list.stream().map(obj->{
            return taskMapper.converToDTO(obj);
        }).collect(Collectors.toList());
    }
    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user =userRepository.findByUserName(username);

//        User user =userRepository.findByUserName("jalba@test.com"); //hard coded, and will be dynamic later
        List<Task> list = taskRepository.findAllByStatusIsNotAndAssignedEmployee(status, user);

        return list.stream().map(taskMapper::converToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByProjectManager() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(username);

//        User user = userRepository.findByUserName("msmith@test.com");
        List<Task> tasks=taskRepository.findAllByProjectAssignedManager(user);
        return tasks.stream().map(taskMapper::converToDTO).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO dto) {
        Optional<Task> task=taskRepository.findById(dto.getId());
        if(task.isPresent()){
            task.get().setStatus(dto.getStatus());
            taskRepository.save(task.get());
        }
    }


    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(username);

//        User user = userRepository.findByUserName("jalba@test.com");
        List<Task> list=taskRepository.findAllByStatusAndAssignedEmployee(status, user);

        return list.stream().map(taskMapper::converToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> readAllByEmployee(User assignedEmployee) {
        List<Task> list = taskRepository.findAllByAssignedEmployee( assignedEmployee);
        return list.stream().map(taskMapper::converToDTO).collect(Collectors.toList());

    }
}
