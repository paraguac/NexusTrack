package com.nexustrack.api.service;

import com.nexustrack.api.model.Project;
import com.nexustrack.api.repository.ProjectRepository;
import com.nexustrack.common.AppLogger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AppLogger logger = new AppLogger(ProjectService.class);

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        logger.info("Fetching all projects");
        return projectRepository.findAll();
    }

    public Optional<Project> findById(Long id) {
        logger.info("Fetching project with id: {}", id);
        return projectRepository.findById(id);
    }

    public Project create(Project project) {
        logger.info("Creating project: {}", project.getName());
        return projectRepository.save(project);
    }

    public Optional<Project> update(Long id, Project updated) {
        return projectRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setStatus(updated.getStatus());
            logger.info("Updated project: {}", existing.getName());
            return projectRepository.save(existing);
        });
    }

    public void delete(Long id) {
        logger.info("Deleting project with id: {}", id);
        projectRepository.deleteById(id);
    }
}
