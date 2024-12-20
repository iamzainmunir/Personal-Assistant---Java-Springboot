package com.ztech.taskservice.Repository;

import com.ztech.taskservice.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsername(String username);
}
