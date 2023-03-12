package com.project.board.post.domain;

import com.project.board.author.domain.Author;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private String title;
    private String contents;
    private String email;
    private String scheduled;
    private String scheduledTime;
}
