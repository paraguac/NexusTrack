package com.nexustrack.api.service;

import com.nexustrack.api.model.Task;
import com.nexustrack.api.repository.TaskRepository;
import com.nexustrack.common.AppLogger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AppLogger logger = new AppLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        logger.info("Fetching task with id: {}", id);
        return taskRepository.findById(id);
    }

    public Task create(Task task) {
        logger.info("Creating task: {}", task.getTitle());
        return taskRepository.save(task);
    }

    public Optional<Task> update(Long id, Task updated) {
        return taskRepository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setAssignee(updated.getAssignee());
            existing.setPriority(updated.getPriority());
            existing.setDueDate(updated.getDueDate());
            existing.setProjectId(updated.getProjectId());
            logger.info("Updated task: {}", existing.getTitle());
            return taskRepository.save(existing);
        });
    }

    public void delete(Long id) {
        logger.info("Deleting task with id: {}", id);
        taskRepository.deleteById(id);
    }
}
