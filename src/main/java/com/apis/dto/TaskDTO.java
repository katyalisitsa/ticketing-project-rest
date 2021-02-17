package com.apis.dto;

import com.apis.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public class TaskDTO {

    private Long Id;
    private ProjectDTO project;
    private String taskSubject;
    private UserDTO assignedEmployee;
    @DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
    LocalDate assignedDate;

    private Status status;
    private String details;


}
