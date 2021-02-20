package com.apis.implementation;

import com.apis.entity.Project;
import com.apis.exception.TicketingProjectException;
import com.apis.mapper.MapperUtil;
import com.apis.repository.TaskRepository;
import com.apis.repository.UserRepository;
import com.apis.dto.ProjectDTO;
import com.apis.dto.TaskDTO;
import com.apis.entity.Task;
import com.apis.entity.User;
import com.apis.enums.Status;
import com.apis.service.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private MapperUtil mapperUtil;
    private UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO findById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(value -> mapperUtil.convert(value, new TaskDTO())).orElse(null);
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(task -> {
                    return mapperUtil.convert(task, new TaskDTO());
                }).collect(Collectors.toList());

    }

    @Override
    public TaskDTO save(TaskDTO dto) {
        dto.setStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = mapperUtil.convert(dto, new Task());
        Task save = taskRepository.save(task);
        return mapperUtil.convert(save, new TaskDTO());
    }

    @Override
    public TaskDTO update(TaskDTO dto) throws TicketingProjectException {

        Task task = taskRepository.findById(dto.getId()).orElseThrow(() -> new TicketingProjectException("Task does not exist"));
        Task convertedTask = mapperUtil.convert(task, new Task());

        Task save = taskRepository.save(convertedTask);
        return mapperUtil.convert(save, new TaskDTO());
    }

    @Override
    public void delete(Long id) throws TicketingProjectException {
        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new TicketingProjectException("Task does not exist"));
        foundTask.setIsDeleted(true);
        taskRepository.save(foundTask);
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
        List<TaskDTO> taskDTOS = listAllTasksByProject(project);
        taskDTOS.forEach(taskDTO -> {
            try {
                delete(taskDTO.getId());
            } catch (TicketingProjectException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public List<TaskDTO> listAllTasksByProject(ProjectDTO project) {
        List<Task> list = taskRepository.findAllByProject(mapperUtil.convert(project, new Project()));

        return list.stream().map(obj -> {
            return mapperUtil.convert(obj, new TaskDTO());
        }).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) throws TicketingProjectException {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new TicketingProjectException("This user does not exist"));


        List<Task> list = taskRepository.findAllByStatusIsNotAndAssignedEmployee(status, user);

        return list.stream().map(obj -> mapperUtil.convert(obj, new TaskDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByProjectManager() throws TicketingProjectException {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new TicketingProjectException("This user does not exist"));

        List<Task> tasks = taskRepository.findAllByProjectAssignedManager(user);
        return tasks.stream().map(obj -> mapperUtil.convert(obj, new TaskDTO())).collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateStatus(TaskDTO dto) throws TicketingProjectException {
        Task task = taskRepository.findById(dto.getId()).orElseThrow(() -> new TicketingProjectException("Task does not exist"));
        task.setStatus(dto.getStatus());
        Task save = taskRepository.save(task);
        return mapperUtil.convert(save, new TaskDTO());
    }


//    @Override
//    public List<TaskDTO> listAllTasksByStatus(Status status) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUserName(username);
//        List<Task> list = taskRepository.findAllByStatusAndAssignedEmployee(status, user);
//
//        return list.stream().map(obj -> mapperUtil.convert(obj, new TaskDTO())).collect(Collectors.toList());
//    }

    @Override
    public List<TaskDTO> readAllByEmployee(User assignedEmployee) {
        List<Task> list = taskRepository.findAllByAssignedEmployee(assignedEmployee);
        return list.stream().map(obj -> mapperUtil.convert(obj, new TaskDTO())).collect(Collectors.toList());

    }
}
