package com.minlog.api.service;

import com.minlog.api.domain.Post;
import com.minlog.api.exception.PostNotFound;
import com.minlog.api.repository.PostRepository;
import com.minlog.api.request.PostCreate;
import com.minlog.api.request.PostEdit;
import com.minlog.api.request.PostSearch;
import com.minlog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
        Post post = Post.builder()
                .title("0123456789")
                .content("내용입니다.")
                .build();

        postRepository.save(post);

        PostResponse postResponse = postService.get(post.getId());

        assertNotNull(postResponse);
        // title의 길이를 10개 까지!
        assertEquals("0123456789", postResponse.getTitle());
        assertEquals("내용입니다.", postResponse.getContent());
    }

    @Test
    public void selectFailedTest() {

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        assertThrows(PostNotFound.class, ()->{
            postService.get(post.getId()+1L);
        });

//        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
//                () -> {postService.get(post.getId()+1L);}
//        , "예외처리가 잘못 되었어요.");
//
//        assertEquals("존재하지 않는 글입니다.", e.getMessage());
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

    @Test
    public void editTest() {

        Post post = Post.builder()
                .title("제목 - 10")
                .content("본문 - 10")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정")
                .content("본문 - 10")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 글입니다. id = " + post.getId()));
        assertEquals("제목 수정", changedPost.getTitle());
        assertEquals("본문 - 10", changedPost.getContent());
    }

    @Test
    public void edit2Test() {

        Post post = Post.builder()
                .title("제목 - 10")
                .content("본문 - 10")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 - 10")
                .content("본문 수정")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 글입니다. id = " + post.getId()));
        assertEquals("제목 - 10", changedPost.getTitle());
        assertEquals("본문 수정", changedPost.getContent());
    }

    @Test
    public void delete() {

        Post post = Post.builder()
                .title("제목 - 10")
                .content("본문 - 10")
                .build();

        postRepository.save(post);

        postService.delete(post.getId());

        assertEquals(0, postRepository.count());

    }
}