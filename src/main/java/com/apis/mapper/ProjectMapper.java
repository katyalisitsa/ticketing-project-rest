package com.apis.mapper;

import com.apis.dto.ProjectDTO;
import com.apis.entity.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    private ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Project convertToEntity(ProjectDTO dto){
        return modelMapper.map(dto, Project.class);
    }

    public ProjectDTO convertToDTO(Project entity){
        return modelMapper.map(entity, ProjectDTO.class);
    }


}
