package com.apis.controller;

import com.apis.annotation.DefaultExceptionMessage;
import com.apis.dto.ProjectDTO;
import com.apis.entity.ResponseWrapper;
import com.apis.service.ProjectService;
import com.apis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@Tag(name = "Project Controller", description = "Project API")
public class ProjectController {
    ProjectService projectService;
    UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @GetMapping
    @Operation(summary = "Read all projects")
    @PreAuthorize("hasAnyAuthority({'Admin','Manager'})")
    public ResponseEntity<ResponseWrapper> readAll() {
        List<ProjectDTO> projectDTOS = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved", projectDTOS));
    }

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @GetMapping("/projectcode")
    @Operation(summary = "Read by project code")
    @PreAuthorize("hasAnyAuthority({'Admin','Manager'})")
    public ResponseEntity<ResponseWrapper> readByProjectCode(@PathVariable("projectcode") String projectcode) {
        ProjectDTO projectDTO = projectService.getByProjectDto(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved", projectDTO));
    }


}
