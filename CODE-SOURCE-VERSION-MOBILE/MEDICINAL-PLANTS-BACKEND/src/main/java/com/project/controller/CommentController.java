package com.project.controller;

import com.project.dto.CommentRequest;
import com.project.dto.CommentResponse;
import com.project.security.jwt.JwtUtils;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtUtils jwtUtils;

    @Autowired
    public CommentController(CommentService commentService, JwtUtils jwtUtils) {
        this.commentService = commentService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addComment(@RequestHeader("Authorization") String token,
                                                          @RequestBody CommentRequest commentRequest) {
        // Extract the username from the token
        String username = jwtUtils.extractUsername(token.substring(7));

        // Add the comment
        Map<String, Object> response = commentService.addCommentToPlant(
                commentRequest.getPlantId(),
                commentRequest.getContent(),
                username
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{plantId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPlant(@PathVariable Long plantId) {
        // Fetch comments for the specified plant
        List<CommentResponse> comments = commentService.getCommentsByPlant(plantId);

        return ResponseEntity.ok(comments);
    }
}
