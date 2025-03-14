package com.example.demo.user.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.port.UserReadService;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Test
    public void 사용자는_특정유저의_개인정보는_소거된채_정보를_전달_받을_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("asdf@gmail.com")
                        .nickname("zh")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaa")
                        .build());

        // when
        ResponseEntity<UserResponse> result = UserController.builder()
                .userReadService(testContainer.userReadService)
                .build()
                .getById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("asdf@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("zh");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }


//    @Test
//    void 사용자는_존재하지_않는_유저의_아이디로_api호출시_404응답을_받는다() throws Exception {
//
//    }
//
//    @Test
//    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(get("/api/users/2/verify")
//                        .queryParam("certificationCode", "aaaaa-aaaa-aaa1"))
//                .andExpect(status().isFound());
//        UserEntity userEntity = userJpaRepository.findById(2L).get();
//        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
//    }
//
//    @Test
//    void 사용자는_내_정보를_불러올때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(get("/api/users/me")
//                        .header("EMAIL", "ziho1234567890@gmail.com"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.email").value("ziho1234567890@gmail.com"))
//                .andExpect(jsonPath("$.nickname").value("asdf"))
//                .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }
//
//    @Test
//    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
//        // given
//        UserUpdate userUpdate = UserUpdate.builder()
//                .nickname("asdf1")
//                .address("gg")
//                .build();
//        // when
//        // then
//        mockMvc.perform(put("/api/users/me")
//                        .header("EMAIL", "ziho1234567890@gmail.com")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userUpdate)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.email").value("ziho1234567890@gmail.com"))
//                .andExpect(jsonPath("$.nickname").value("asdf1"))
//                .andExpect(jsonPath("$.address").value("gg"))
//                .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }
//
//    @Test
//    void 사용자는_인증코드가_일치하지않을경우_권한없음에러를_내려준다() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(get("/api/users/2/verify")
//                        .queryParam("certificationCode", "aaaaa-aaaa-aaa2"))
//                .andExpect(status().isForbidden());
//    }
//

}
