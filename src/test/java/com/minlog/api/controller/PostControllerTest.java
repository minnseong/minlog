package com.minlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlog.api.domain.Post;
import com.minlog.api.repository.PostRepository;
import com.minlog.api.request.PostCreate;
import com.minlog.api.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

//    @Test
//    public void getHome() throws Exception {
//        mockMvc.perform(get("/home"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("request home"))
//                .andDo(print());
//    }
//
//    @Test
//    public void postHome() throws Exception {
//        String requestJson = "{\"userId\":\"\", \"password\": \"1234\"}";
//        String responseJson = "{\"id\":\"minnseong\",\"pwd\":\"1234\"}";
//
//        mockMvc.perform(post("/home")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isBadRequest())
////                .andExpect(MockMvcResultMatchers.content().string(responseJson))
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("????????? ???????????????."))
//                .andExpect(jsonPath("$.validation.userId").value("must not be blank"))
//                .andDo(print());
//    }

    @Test
    public void writeTest() throws Exception {
//        String requestJson = "{\"title\":\"???????????????.\", \"content\": \"???????????????.\"}";

        PostCreate request = PostCreate.builder()
                .title("???????????????.")
                .content("???????????????.")
                .build();

        // ObjectMapper ?????? !!!
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isOk())
                .andDo(print());

//        assertEquals(1L, postRepository.count());
    }

    @Test
    public void getTest() throws Exception {

        Post post = Post.builder()
                .title("??????")
                .content("??????")
                .build();
        postRepository.save(post);

        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("??????"))
                .andExpect(jsonPath("$.content").value("??????"))
                .andDo(print());
    }

//    @Test
//    public void getAllTest() throws Exception {
//
//        Post post1 = Post.builder()
//                .title("??????1")
//                .content("??????1")
//                .build();
//        postRepository.save(post1);
//
//        Post post2 = Post.builder()
//                .title("??????2")
//                .content("??????2")
//                .build();
//        postRepository.save(post2);
//
//        mockMvc.perform(get("/posts")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", is(2)))
//                .andExpect(jsonPath("$[0].id").value(post1.getId()))
//                .andExpect(jsonPath("$[0].title").value("??????1"))
//                .andExpect(jsonPath("$[0].content").value("??????1"))
//                .andExpect(jsonPath("$[1].id").value(post2.getId()))
//                .andExpect(jsonPath("$[1].title").value("??????2"))
//                .andExpect(jsonPath("$[1].content").value("??????2"))
//                .andDo(print());
//
//    }

    @Test
    public void getByPagingTest() throws Exception {

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("?????? - " + i)
                        .content("?????? - " + i)
                        .build()).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

//        mockMvc.perform(get("/posts?page=1&size=10")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", is(10)))
//                .andExpect(jsonPath("$[0].id").value(20))
//                .andExpect(jsonPath("$[0].title").value("?????? - 19"))
//                .andExpect(jsonPath("$[0].content").value("?????? - 19"))
//                .andDo(print());
    }

    @Test
    public void getByPaging0Test() throws Exception {

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("?????? - " + i)
                        .content("?????? - " + i)
                        .build()).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andDo(print());
    }

    @Test
    public void editTest() throws Exception {

        Post post = Post.builder()
                .title("??????1")
                .content("??????1")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("?????? ??????")
                .content("??????1")
                .build();

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void delete() throws Exception {

        Post post = Post.builder()
                .title("??????1")
                .content("??????1")
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getFailedTest() throws Exception {

        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? '??????'??? ???????????? ??????.")
    public void write2Test() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("???????????????..")
                .content("???????????????.")
                .build();

        // ObjectMapper ?????? !!!
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }
}

