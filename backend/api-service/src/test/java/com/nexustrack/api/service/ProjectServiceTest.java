package com.nexustrack.api.service;

import com.nexustrack.api.model.Project;
import com.nexustrack.api.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project sampleProject;

    @BeforeEach
    void setUp() {
        sampleProject = new Project();
        sampleProject.setId(1L);
        sampleProject.setName("Test Project");
        sampleProject.setDescription("A test project");
        sampleProject.setStatus("ACTIVE");
    }

    @Test
    void findAll_returnsAllProjects() {
        Project another = new Project();
        another.setId(2L);
        another.setName("Another Project");
        when(projectRepository.findAll()).thenReturn(Arrays.asList(sampleProject, another));

        List<Project> result = projectService.findAll();

        assertEquals(2, result.size());
        verify(projectRepository).findAll();
    }

    @Test
    void create_savesAndReturnsProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        Project result = projectService.create(sampleProject);

        assertEquals("Test Project", result.getName());
        verify(projectRepository).save(sampleProject);
    }

    @Test
    void findById_whenFound_returnsProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        Optional<Project> result = projectService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Project", result.get().getName());
    }

    @Test
    void findById_whenNotFound_returnsEmpty() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void update_whenFound_updatesAndReturns() {
        Project updated = new Project();
        updated.setName("Updated");
        updated.setDescription("Updated desc");
        updated.setStatus("ARCHIVED");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        Optional<Project> result = projectService.update(1L, updated);

        assertTrue(result.isPresent());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void update_whenNotFound_returnsEmpty() {
        Project updated = new Project();
        updated.setName("Updated");
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.update(99L, updated);

        assertFalse(result.isPresent());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void delete_callsRepository() {
        projectService.delete(1L);
        verify(projectRepository).deleteById(1L);
    }
}
