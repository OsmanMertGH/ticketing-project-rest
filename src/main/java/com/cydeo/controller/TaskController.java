package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTasks() {
        List<TaskDTO> tasks = taskService.listAllTasks();
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Tasks retrieved!",
                        tasks,
                        HttpStatus.OK
                ));
    }

    @GetMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("id") Long id) {
        TaskDTO task = taskService.findById(id);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Task retrieved!",
                        task,
                        HttpStatus.OK
                ));
    }

    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Task created!",
                        HttpStatus.CREATED
                ));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTasks(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Task deleted!",
                        HttpStatus.ACCEPTED
                ));
    }

    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTasks(@RequestBody TaskDTO taskDTO) {
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Task updated!",
                        HttpStatus.ACCEPTED
                ));
    }

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeePendingTasks() {
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Tasks are succesfully retrieved",
                        taskDTOList,
                        HttpStatus.OK
                ));
    }

    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO taskDTO) {
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Task is updated!",
                        HttpStatus.OK
                ));
    }

    @GetMapping("/employee/archieve")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks() {
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Tasks are succesfully retrieved",
                        taskDTOList,
                        HttpStatus.OK
                ));
    }
}
