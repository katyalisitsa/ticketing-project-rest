package com.apis.dto;

import com.apis.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

    //no need constructor
//    public ProjectDTO(String projectName, String projectCode, UserDTO assignedManager, LocalDate startDate, LocalDate endDate, String projectDetails, Status projectStatus) {
//        this.projectName = projectName;
//        this.projectCode = projectCode;
//        this.assignedManager = assignedManager;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.projectDetails = projectDetails;
//        this.projectStatus = projectStatus;
//    }
}
