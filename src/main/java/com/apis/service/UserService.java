package com.apis.service;

import com.apis.exception.TicketingProjectException;
import com.apis.dto.UserDTO;
import com.apis.entity.User;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers(); //service communicate with DTO, and DTO communicate with Entity
    UserDTO findByUserName(String userName);
    UserDTO save(UserDTO dto) throws TicketingProjectException;
    UserDTO update(UserDTO dto);
    void delete(String username) throws TicketingProjectException;

    void deleteByUserName(String username);

    List<UserDTO> listAllByRole(String role);

    Boolean checkIfUserCanBeDeleted(User user);

    UserDTO confirm(User user);
    

}
