package com.minlog.api.request;


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
}
