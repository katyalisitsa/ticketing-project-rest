package com.apis.controller;

import com.apis.dto.ProjectDTO;
import com.apis.service.ProjectService;
import com.apis.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {
    ProjectService projectService;
    UserService userService;
//    TaskService taskService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    //
    @GetMapping("/create")
    public String createProject(Model model){
        model.addAttribute("project", new ProjectDTO());
//        List<UserDTO> managerList=userService.findAll().stream()
//                .filter(user->user.getRole().getDescription().equals("Manager"))
//                .collect(Collectors.toList());
//        model.addAttribute("managerList", managerList);
        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("managerList", userService.listAllByRole("Manager"));
//        model.addAttribute("managerList", userService.listAllUsers().stream()
//                .filter(user->user.getRole().getDescription().equals("Manager"))
//                .collect(Collectors.toList()));
        model.addAttribute("projectlist", projectService.listAllProjects());

        return "/project/create";
    }
    @PostMapping("/create")
    public String insertProject(ProjectDTO project){
        projectService.save(project);
//        project.setProjectStatus(Status.OPEN);
        return "redirect:/project/create";
    }
    @GetMapping("/delete/{projectcode}")
    public String deleteProject(@PathVariable("projectcode") String projectcode){
        projectService.delete(projectcode);
        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectcode}")
    public String editProject(@PathVariable("projectcode") String projectcode, Model model){
        model.addAttribute("project", projectService.getByProjectDto(projectcode));
        model.addAttribute("managerlist", userService.listAllByRole("Manager"));
//        model.addAttribute("managerlist", userService.findAll().stream()
//                .filter(user->user.getRole().getDescription().equals("Manager"))
//                .collect(Collectors.toList()));
        model.addAttribute("projectlist", projectService.listAllProjects());
        return "/project/update";
    }

    @PostMapping("/update/{projectcode}")
    public String updateProject(@PathVariable("projectcode") String projectcode, ProjectDTO project){
//        project.setProjectStatus(projectService.findById(projectcode).getProjectStatus()); //implemented to service
        projectService.update(project);
        //redirect replaced below commented
//        model.addAttribute("project", new ProjectDTO());
//        model.addAttribute("managerlist", userService.findAll().stream()
//                .filter(user->user.getRole().getDescription().equals("Manager"))
//                .collect(Collectors.toList()));
//        model.addAttribute("projectlist", projectService.findAll());
        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable("projectcode") String projectcode){
//        projectService.findById(projectcode).setProjectStatus(Status.COMPLETE);
        projectService.complete(projectcode);
        return "redirect:/project/create";
    }
//
//
    @GetMapping("/manager/complete")
    public String getProjectByManager(Model model){
// for MVC
//        List<ProjectDTO> projects=projectService.findAll().stream()
//                .filter(p->p.getAssignedManager().equals(userService.findById("john@cybertek.com")))
//                .collect(Collectors.toList());
//        model.addAttribute("projects", getCountedListOfProjectDTOByManager(userService.findById("john@cybertek.com")));

        //ORM
        List<ProjectDTO> projects = projectService.listAllProjectDetails();
        model.addAttribute("projects", projects);


        return "/manager/project-status";
    }
//  do not need this method when we working with ORM
//    public List<ProjectDTO> getCountedListOfProjectDTOByManager(UserDTO manager){
//        List<ProjectDTO> list=projectService.findAll().stream()
//                .filter(p->p.getAssignedManager().equals(manager))
//                .map(p->{
//                    List<TaskDTO> taskList=taskService.findTaskByManager(manager);
//                    int completedCount=(int) taskList.stream()
//                            .filter(t->t.getProject().equals(p) && t.getStatus().equals(Status.COMPLETE)).count();
//                    int inCompleteCount=(int) taskList.stream()
//                            .filter(t->t.getProject().equals(p) && t.getStatus() != Status.COMPLETE).count();
//
//                    p.setCompletedTaskCount(completedCount);
//                    p.setUnfinishedTaskCount(inCompleteCount);
////                    return new ProjectDTO( p.getProjectName(),p.getProjectCode(),
////                            userService.findById(p.getAssignedManager().getUserName()),
////                            p.getStartDate(), p.getEndDate(), p.getProjectDetails(), p.getProjectStatus(),
////                            completedCount, inCompleteCount);
//                    return p;
//                }).collect(Collectors.toList());
//        return list;
//    }
//
//
//

    @GetMapping("/manager/complete/{projectCode}")
    public String manager_completed(@PathVariable("projectCode") String projectCode, Model model){
        projectService.complete(projectCode);
        return "redirect:/project/manager/complete";
    }




}
