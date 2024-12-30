package com.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String content; // The content of the comment
    private Long plantId;   // The ID of the plant
}
