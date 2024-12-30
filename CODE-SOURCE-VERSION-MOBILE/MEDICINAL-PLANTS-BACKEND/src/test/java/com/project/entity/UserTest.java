package com.project.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserTest {

    private User user;
    private Comment comment;

    @BeforeEach
    void setUp() {
        // Mocking the Comment entity to simulate the relationship
        comment = mock(Comment.class);

        // Creating a new User object with sample data
        user = new User();
        user.setUsername("testUser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");

        // Setting up the comments relationship
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        user.setComments(comments);
    }

    @Test
    void testDefaultConstructorAndSetters() {
        // Assert that the fields are correctly set
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getComments());
        assertEquals(1, user.getComments().size());
    }


    @Test
    void testAddComment() {
        // Arrange
        Comment newComment = new Comment();
        List<Comment> initialComments = user.getComments();
        initialComments.add(newComment);

        // Act: Adding a new comment to the user
        user.setComments(initialComments);

        // Assert: Verify that the comment was added
        assertEquals(2, user.getComments().size());
        assertTrue(user.getComments().contains(newComment));
    }
}
