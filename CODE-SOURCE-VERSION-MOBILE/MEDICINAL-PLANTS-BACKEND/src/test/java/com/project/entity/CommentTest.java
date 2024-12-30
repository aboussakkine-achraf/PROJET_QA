package com.project.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CommentTest {

    private Comment comment;
    private PlantesMedicinales plant;
    private User user;

    @BeforeEach
    void setUp() {
        plant = mock(PlantesMedicinales.class); // Mocking PlantesMedicinales
        user = mock(User.class); // Mocking User
        comment = new Comment();
        comment.setContent("This is a test comment.");
        comment.setTimestamp(LocalDateTime.now());
        comment.setPlant(plant);
        comment.setUser(user);
    }

    @Test
    void testDefaultConstructorAndSetters() {
        // Assert that the fields are correctly set
        assertNotNull(comment);
        assertEquals("This is a test comment.", comment.getContent());
        assertNotNull(comment.getTimestamp());
        assertEquals(plant, comment.getPlant());
        assertEquals(user, comment.getUser());
    }

    @Test
    void testPrePersistSetsTimestamp() {
        // Create a new comment without setting a timestamp
        Comment newComment = new Comment();
        newComment.setContent("Test comment without timestamp.");

        // Act: Call prePersist to set the timestamp
        newComment.prePersist();

        // Assert: The timestamp should now be set to the current time
        assertNotNull(newComment.getTimestamp());
        assertTrue(newComment.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1))); // Ensure the timestamp is recent
    }

}
