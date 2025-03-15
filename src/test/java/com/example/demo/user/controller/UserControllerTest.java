package com.example.demo.user.controller;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserControllerTest {

    @Test
    public void 사용자는_특정유저의_개인정보는_소거된채_정보를_전달_받을_수_있다() {
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
        ResponseEntity<UserResponse> result =
                testContainer.userController.getById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("asdf@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("zh");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api호출시_404응답을_받는다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() ->
                testContainer.userController.getById(1))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("asdf@gmail.com")
                        .nickname("zh")
                        .address("Seoul")
                        .status(UserStatus.PENDING)
                        .certificationCode("aaaaa")
                        .build());

        // when
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "aaaaa");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증코드가_일치하지않을경우_권한없음에러를_내려준다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        testContainer.userRepository.save(
                User.builder()
                        .id(1L)
                        .email("asdf@gmail.com")
                        .nickname("zh")
                        .address("Seoul")
                        .status(UserStatus.PENDING)
                        .certificationCode("aaaaa")
                        .build());

        // when
        // then
        assertThatThrownBy(() ->
                testContainer.userController.verifyEmail(1, "aaaaab"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올때_개인정보인_주소도_갖고_올_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
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
        ResponseEntity<MyProfileResponse> result = testContainer.myInfoController.get("asdf@gmail.com");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("asdf@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("zh");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() {
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
        ResponseEntity<MyProfileResponse> result =
                testContainer.myInfoController.update("asdf@gmail.com", UserUpdate.builder()
                .address("Pangyo")
                .nickname("zh1")
                .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("asdf@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("zh1");
        assertThat(result.getBody().getAddress()).isEqualTo("Pangyo");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }


}
