package com.apis.mapper;

import com.apis.dto.UserDTO;
import com.apis.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {//injection using constructor instead of @Autowire

        this.modelMapper = modelMapper;
    }

    public User convertToEntity(UserDTO dto){
        return modelMapper.map(dto, User.class);
    }

    public UserDTO convertToDTO(User entity){

        return modelMapper.map(entity, UserDTO.class);
    }



}
