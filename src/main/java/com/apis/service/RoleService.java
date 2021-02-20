package com.apis.service;

import com.apis.dto.RoleDTO;
import com.apis.exception.TicketingProjectException;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id) throws TicketingProjectException;



}
