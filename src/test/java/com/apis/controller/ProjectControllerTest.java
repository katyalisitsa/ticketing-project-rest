package com.apis.controller;

import com.apis.dto.ProjectDTO;
import com.apis.dto.RoleDTO;
import com.apis.dto.UserDTO;
import com.apis.entity.Project;
import com.apis.enums.Gender;
import com.apis.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    public MockMvc mockMvc;

    private final String token = "";

    static UserDTO userDTO;
    static ProjectDTO projectDTO;

    @BeforeAll
    static void setUp() {
        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("Katya")
                .lastName("Lisitsa")
                .userName("katya.lisitsa@gmail.com")
                .password("abc123")
                .confirmPassword("abc123")
                .role(new RoleDTO(2L, "Manager"))
                .gender(Gender.FEMALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("API123")
                .projectName("Api")
                .assignedManager(userDTO)
                .startDate(LocalDate.now().plusDays(5))
                .projectDetails("Api Test")
                .projectStatus(Status.OPEN)
                .completedTaskCount(0)
                .unfinishedTaskCount(0)
                .build();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/project/API123"))
                .andExpect(status().is4xxClientError());
    }

}