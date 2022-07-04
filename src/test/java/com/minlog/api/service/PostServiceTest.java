package com.minlog.api.service;

import com.minlog.api.domain.Post;
import com.minlog.api.repository.PostRepository;
import com.minlog.api.request.PostCreate;
import com.minlog.api.request.PostSearch;
import com.minlog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    public void writeTest() {
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.write(postCreate);

        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());

    }

    @Test
    public void selectTest() {
        PostCreate response = PostCreate.builder()
                .title("0123456789101112")
                .content("내용입니다.")
                .build();
        postService.write(response);

        PostResponse post = postService.get(1L);

        assertNotNull(post);
        // title의 길이를 10개 까지!
        assertEquals("0123456789", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    public void selectAllTest() {
        PostCreate response = PostCreate.builder()
                .title("foo1")
                .content("bar1")
                .build();

        PostCreate response2 = PostCreate.builder()
                .title("foo2")
                .content("bar2")
                .build();


        postService.write(response);
        postService.write(response2);

        List<PostResponse> list = postService.getList();

        assertEquals(2L, list.size());
    }

    @Test
    public void selectByPageTest() {

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                            .title("제목 - " + i)
                            .content("본문 - " + i)
                            .build()).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        List<PostResponse> list = postService.getListByPage(postSearch);

        assertEquals(10L, list.size());
        assertEquals("제목 - 19", list.get(0).getTitle());
        assertEquals("제목 - 10", list.get(9).getTitle());
//        assertEquals("제목 - 25", list.get(4).getTitle());
    }
}