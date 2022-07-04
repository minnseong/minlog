package com.minlog.api.repository;


import com.minlog.api.domain.Post;
import com.minlog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
