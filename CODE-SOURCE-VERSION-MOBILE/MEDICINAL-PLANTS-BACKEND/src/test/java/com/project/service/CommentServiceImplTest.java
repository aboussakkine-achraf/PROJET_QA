package com.project.service;

import com.project.dto.CommentResponse;
import com.project.entity.Comment;
import com.project.entity.PlantesMedicinales;
import com.project.entity.User;
import com.project.repository.CommentRepository;
import com.project.repository.PlantesMedicinalesRepository;
import com.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlantesMedicinalesRepository plantesMedicinalesRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private PlantesMedicinales plant;
    private User user;

    @BeforeEach
    public void setUp() {
        plant = new PlantesMedicinales();
        plant.setId(1L);
        plant.setNom("Mint");

        user = new User();
        user.setUsername("john_doe");
    }

    @Test
    public void testAddCommentToPlant() {
        String commentContent = "Great medicinal plant!";

        // Simulate saving the comment to the repository
        when(plantesMedicinalesRepository.findById(1L)).thenReturn(Optional.of(plant));
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());

        Map<String, Object> response = commentService.addCommentToPlant(1L, commentContent, "john_doe");

        assertNotNull(response);
        assertEquals("Comment added successfully", response.get("message"));
        assertEquals("john_doe", response.get("username"));

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void testAddCommentToPlant_PlantNotFound() {
        // Only mock what's necessary for this test
        when(plantesMedicinalesRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.addCommentToPlant(1L, "Content", "john_doe");
        });

        assertEquals("Plant not found", exception.getMessage());

        // Ensure no unnecessary stubbing error
        verify(plantesMedicinalesRepository).findById(1L);
    }

    @Test
    public void testAddCommentToPlant_UserNotFound() {
        when(plantesMedicinalesRepository.findById(1L)).thenReturn(Optional.of(plant));
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.addCommentToPlant(1L, "Content", "john_doe");
        });

        assertEquals("User not found", exception.getMessage());

        // Ensure no unnecessary stubbing error
        verify(userRepository).findByUsername("john_doe");
    }

    @Test
    public void testGetCommentsByPlant() {
        Comment comment1 = new Comment();
        comment1.setContent("Good for digestion");
        comment1.setUser(user);
        comment1.setPlant(plant);

        Comment comment2 = new Comment();
        comment2.setContent("Helps with headaches");
        comment2.setUser(user);
        comment2.setPlant(plant);

        List<Comment> comments = Arrays.asList(comment1, comment2);

        // Only mock what's necessary for this test
        when(commentRepository.findByPlantId(1L)).thenReturn(comments);

        List<CommentResponse> response = commentService.getCommentsByPlant(1L);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Good for digestion", response.get(0).getContent());
        assertEquals("john_doe", response.get(0).getUsername());

        // Ensure no unnecessary stubbing error
        verify(commentRepository).findByPlantId(1L);
    }
}
