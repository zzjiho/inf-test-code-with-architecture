package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PostResponseTest {

    @Test
    void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("asdf@gmail.com")
                        .nickname("zh")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaa")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("asdf@gmail.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("zh");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}
