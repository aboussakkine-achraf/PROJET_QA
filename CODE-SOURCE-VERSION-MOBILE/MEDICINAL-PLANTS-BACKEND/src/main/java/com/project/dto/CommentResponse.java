package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private String username; // The username of the commenter
    private String content;  // The content of the comment
}
