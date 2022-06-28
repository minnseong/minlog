package com.minlog.api.controller;

import com.minlog.api.domain.Post;
import com.minlog.api.domain.User;
import com.minlog.api.request.PostCreate;
import com.minlog.api.service.PostService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Post post(@RequestBody @Valid PostCreate postCreate) {
        // Case1. 저장한 데이터 Entity 응답하기
        // Case2. 저장한 데이터의 primary_id 응답하기
        //          client에는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
        // Case3. 응답 필요 없음 -> 클라이언트에서 모든 Post 데이터 context를 잘 관리함.
        log.info("response = {}", postService.write(postCreate));
        return postService.write(postCreate);
    }


    @GetMapping("/home")
    public String getHome() {
        return "request home";
    }


    @PostMapping("/home")
    public UserDto postHome(@RequestBody @Valid User user) {
        return new UserDto(user.getUserId(), user.getPassword());
    }

    @Data
    @AllArgsConstructor
    static class UserDto {
        private String id;
        private String pwd;
    }
}

