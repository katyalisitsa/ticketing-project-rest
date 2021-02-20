package com.apis.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {
    private ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    public <T> T convertToEntity(Object objectTobeConverted, T convertedObject){
//        return modelMapper.map(objectTobeConverted, (Type) convertedObject.getClass());
//    }
//
//    public <T> T converToDTO(Object objectTobeConverted, T convertedObject){
//        return modelMapper.map(objectTobeConverted, (Type) convertedObject.getClass());
//    }

    public <T> T convert(Object objectTobeConverted, T convertedObject){
        return modelMapper.map(objectTobeConverted, (Type) convertedObject.getClass());
    }

}
