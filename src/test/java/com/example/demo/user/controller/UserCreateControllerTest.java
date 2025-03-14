package com.example.demo.user.controller;

import com.example.demo.user.domain.UserCreate;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCreateControllerTest {

//    @Test
//    void 사용자는_회원가입을_할_수_있고_상태는_PENDING이다() throws Exception {
//        // given
//        UserCreate userCreate = UserCreate.builder()
//                .email("zz@gmail.com")
//                .nickname("asdf2")
//                .address("gg")
//                .build();
//        BDDMockito.doNothing().when(javaMailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
//
//        // when
//        // then
//        mockMvc.perform(
//                post("/api/users")
//                        .header("EMAIL", "zz@gmail.com")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userCreate)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.email").value("zz@gmail.com"))
//                .andExpect(jsonPath("$.nickname").value("asdf2"))
//                .andExpect(jsonPath("$.status").value("PENDING"));
//    }

}
