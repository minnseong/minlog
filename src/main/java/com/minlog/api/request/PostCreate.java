package com.minlog.api.request;


import com.minlog.api.exception.InvalidRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
@NoArgsConstructor
public class PostCreate {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if(title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}
