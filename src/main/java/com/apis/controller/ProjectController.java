package com.apis.controller;

import com.apis.annotation.DefaultExceptionMessage;
import com.apis.dto.ProjectDTO;
import com.apis.entity.ResponseWrapper;
import com.apis.exception.TicketingProjectException;
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

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @PostMapping
    @Operation(summary = "Create project")
    @PreAuthorize("hasAnyAuthority({'Admin','Manager'})")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) throws TicketingProjectException {
        ProjectDTO createdProject = projectService.save(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is created", projectDTO));
    }

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @PutMapping
    @Operation(summary = "Update project")
    @PreAuthorize("hasAnyAuthority({'Admin','Manager'})")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) throws TicketingProjectException {
        ProjectDTO updatedProject = projectService.update(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Project is updated", projectDTO));
    }

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @DeleteMapping("/{projectcode}")
    @Operation(summary = "Delete project")
    @PreAuthorize("hasAnyAuthority({'Admin','Manager'})")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectcode") String projectcode) throws TicketingProjectException {
        projectService.delete(projectcode);

        return ResponseEntity.ok(new ResponseWrapper("Project is deleted"));
    }

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @PutMapping("/complete/{projectcode}")
    @Operation(summary = "Complete project")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> completeProject(@PathVariable("projectcode") String projectcode) throws TicketingProjectException {

        ProjectDTO projectDTO = projectService.complete(projectcode);

        return ResponseEntity.ok(new ResponseWrapper("Project is completed",projectDTO));
    }

}
