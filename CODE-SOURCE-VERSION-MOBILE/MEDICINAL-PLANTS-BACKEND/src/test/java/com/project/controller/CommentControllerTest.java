package com.project.controller;

import com.project.dto.CommentRequest;
import com.project.dto.CommentResponse;
import com.project.security.jwt.JwtUtils;
import com.project.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private CommentController commentController;

    private CommentRequest commentRequest;
    private CommentResponse commentResponse;
    private String token;

    @BeforeEach
    public void setUp() {
        commentRequest = new CommentRequest();
        commentRequest.setPlantId(1L);
        commentRequest.setContent("Great plant!");

        commentResponse = new CommentResponse("testUser", "Great plant!");

        token = "Bearer mockJwtToken";
    }

    @Test
    public void testAddComment() {
        String username = "testUser";
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Comment added successfully");

        when(jwtUtils.extractUsername(anyString())).thenReturn(username);
        when(commentService.addCommentToPlant(1L, "Great plant!", username)).thenReturn(expectedResponse);

        ResponseEntity<Map<String, Object>> response = commentController.addComment(token, commentRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comment added successfully", response.getBody().get("message"));
        verify(jwtUtils, times(1)).extractUsername(anyString());
        verify(commentService, times(1)).addCommentToPlant(1L, "Great plant!", username);
    }

    @Test
    public void testGetCommentsByPlant() {
        List<CommentResponse> comments = Collections.singletonList(commentResponse);

        when(commentService.getCommentsByPlant(1L)).thenReturn(comments);

        ResponseEntity<List<CommentResponse>> response = commentController.getCommentsByPlant(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Great plant!", response.getBody().get(0).getContent());
        assertEquals("testUser", response.getBody().get(0).getUsername());
        verify(commentService, times(1)).getCommentsByPlant(1L);
    }

    @Test
    public void testAddCommentWithInvalidToken() {
        String invalidToken = "InvalidToken";

        when(jwtUtils.extractUsername(anyString())).thenThrow(new IllegalArgumentException("Invalid token"));

        try {
            commentController.addComment(invalidToken, commentRequest);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid token", e.getMessage());
        }

        verify(jwtUtils, times(1)).extractUsername(anyString());
        verify(commentService, times(0)).addCommentToPlant(anyLong(), anyString(), anyString());
    }

    @Test
    public void testGetCommentsByPlantEmptyList() {
        when(commentService.getCommentsByPlant(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CommentResponse>> response = commentController.getCommentsByPlant(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
        verify(commentService, times(1)).getCommentsByPlant(1L);
    }
}
