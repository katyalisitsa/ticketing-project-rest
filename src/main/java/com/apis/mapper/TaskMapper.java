package com.apis.mapper;

import com.apis.dto.TaskDTO;
import com.apis.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task convertToEntity(TaskDTO dto){
        return modelMapper.map(dto, Task.class);
    }

    public TaskDTO converToDTO(Task entity){
        return modelMapper.map(entity, TaskDTO.class);
    }
}
