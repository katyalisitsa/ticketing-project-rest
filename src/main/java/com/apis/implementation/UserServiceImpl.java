package com.apis.implementation;

import com.apis.dto.ProjectDTO;
import com.apis.dto.TaskDTO;
import com.apis.dto.UserDTO;
import com.apis.entity.User;
import com.apis.exception.TicketingProjectException;
import com.apis.mapper.MapperUtil;
import com.apis.repository.UserRepository;
import com.apis.service.ProjectService;
import com.apis.service.TaskService;
import com.apis.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    //    UserMapper userMapper;
    private ProjectService projectService;
    private TaskService taskService;
    private MapperUtil mapperUtil;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy ProjectService projectService, TaskService taskService,
                           MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.taskService = taskService;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> list = userRepository.findAll(Sort.by("firstName"));
        //convert entity to DTO
        return list.stream()
//                .map(obj-> {return userMapper.convertToDTO(obj);})
                .map(obj -> {
                    return mapperUtil.convert(obj, new UserDTO());
                })

                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
//        return userMapper.convertToDTO(user);
        return mapperUtil.convert(user, new UserDTO());

    }

    @Override
    public UserDTO save(UserDTO dto) throws TicketingProjectException {

        User foundUser = userRepository.findByUserName(dto.getUserName());

        if (foundUser != null) {
            throw new TicketingProjectException("User already exists");
        }

        User user = mapperUtil.convert(dto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);

        return mapperUtil.convert(save, new UserDTO());

    }

    @Override
    public UserDTO update(UserDTO dto) {
        //Find current user
        User user = userRepository.findByUserName(dto.getUserName());
        //Map update user dto to entity object
//        User convertedUser= userMapper.convertToEntity(dto);
        User convertedUser = mapperUtil.convert(dto, new User());

        //set id to the converted object
        convertedUser.setId(user.getId());
        convertedUser.setEnabled(true);
        convertedUser.setPassword(passwordEncoder.encode(convertedUser.getPassword()));
        //save updated user
        userRepository.save(convertedUser);
        return findByUserName(dto.getUserName());
    }

    @Override
    public void delete(String username) throws TicketingProjectException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new TicketingProjectException("User does not exists");
        }
        if (!checkIfUserCanBeDeleted(user)) {
            throw new TicketingProjectException("User can not be deleted.  It is linked by a project or task");
        }
        user.setUserName("d-" + user.getUserName() + "-" + user.getId());

        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void deleteByUserName(String username) {
        //hard delete
        userRepository.deleteByUserName(username);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream()
                .map(user -> {
//                    return userMapper.convertToDTO(user);
                    return mapperUtil.convert(user, new UserDTO());

                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkIfUserCanBeDeleted(User user) {

        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectList = projectService.readAllByAssignedManager(user);
                return projectList.size() == 0;
            case "Employee":
                List<TaskDTO> taskList = taskService.readAllByEmployee(user);
                return taskList.size() == 0;
            default:// admin
                return true;
        }

//        return null;
    }
}
