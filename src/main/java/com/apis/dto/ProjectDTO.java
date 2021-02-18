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
public class ProjectDTO {
    private Long id;

    private String projectName;
    private String projectCode;
    private UserDTO assignedManager;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String projectDetails;
    private Status projectStatus;

    private int completedTaskCount;
    private int unfinishedTaskCount;

}
