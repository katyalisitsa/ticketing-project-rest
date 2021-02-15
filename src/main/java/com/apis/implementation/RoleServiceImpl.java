package com.apis.implementation;

import com.apis.repository.RoleRepository;
import com.apis.dto.RoleDTO;
import com.apis.entity.Role;
import com.apis.mapper.RoleMapper;
import com.apis.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
//    @Autowired
    private RoleRepository roleRepository;
//    @Autowired
    private RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        List<Role> list=roleRepository.findAll();
        //convert Role Entities to Role DTO and return it
        return list.stream()
                .map(obj -> { return roleMapper.convertToDTO(obj); })
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role=roleRepository.findById(id).get();
        return roleMapper.convertToDTO(role);
    }
}
