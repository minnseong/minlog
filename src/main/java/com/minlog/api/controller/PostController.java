package com.minlog.api.controller;

import com.minlog.api.domain.Post;
import com.minlog.api.request.PostCreate;
import com.minlog.api.response.PostResponse;
import com.minlog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    // 조회 API - 단건 조회 API
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        PostResponse post = postService.get(id);
        return post;
    }

    @GetMapping("/posts")
    public List<PostResponse> getList() {
        return postService.getList();
    }
}

