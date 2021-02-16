package com.apis.controller;

import com.apis.exception.TicketingProjectException;
import com.apis.dto.UserDTO;

import com.apis.service.RoleService;
import com.apis.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    RoleService roleService;
    UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")  //{} represent list
    public String createUser(Model model) {

        model.addAttribute("user", new UserDTO());
        model.addAttribute("rolelist",roleService.listAllRoles());
        model.addAttribute("userlist", userService.listAllUsers());

        return ("/user/create");

    }
//
    @PostMapping("/create")
    public String insertUser(UserDTO user,  Model model){

        userService.save(user);
        return("redirect:/user/create"); //redirect and removed redundancy of same code block
    }
//
    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username") String username, Model model){
        model.addAttribute("user", userService.findByUserName(username));
        model.addAttribute("userlist", userService.listAllUsers());
        model.addAttribute("rolelist", roleService.listAllRoles());
        return "/user/update";
    }
    @PostMapping("/update/{username}")
    public String updateUser(@PathVariable("username") String username, UserDTO user, Model model){
        userService.update(user);
//        userService.updateByObj(user);

//        model.addAttribute("user", new UserDTO()); //is for new form after hit save button
//        model.addAttribute("rolelist", roleService.findAll());
//        model.addAttribute("userlist", userService.findAll());
        return "redirect:/user/create";
    }
//
    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) throws TicketingProjectException {
        userService.delete(username);
        return "redirect:/user/create";
    }

}
