package com.chain.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class TaskController {

//    @Autowired
    private S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/tasks")
    public Iterable<Task> getTask(){
        return taskRepository.findAll();
    }

    @GetMapping("/users/{assignee}/tasks")
    public Iterable<Task> getAssigneeTasks(@PathVariable String assignee){
        return taskRepository.findByAssignee(assignee);
    }

    @PostMapping("/tasks")
    public ResponseEntity createTasks(@RequestBody Task newTask){
        if(newTask.getAssignee() != null){
            newTask.setStatus("Assigned");
        } else {
            newTask.setStatus("Available");
        }
        taskRepository.save(newTask);
        return new ResponseEntity(newTask, HttpStatus.OK);
    }

    //Change Status
    @PutMapping("/tasks/{id}/state")
    public List<Task> putState(@PathVariable String id){
        Task task = taskRepository.findById(id).get();

        if(task.getStatus().equals("Available")){
            task.setStatus("Assigned");
        } else if(task.getStatus().equals("Assigned")){
            task.setStatus("Accepted");
        } else if(task.getStatus().equals("Accepted")){
            task.setStatus("Finished");
        }
        taskRepository.save(task);
        List<Task> tasks = (List) taskRepository.findAll();
        return tasks;
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public List<Task> putAssignee(@PathVariable String id, @PathVariable String assignee){
        Task task = taskRepository.findById(id).get();
        task.setAssignee(assignee);
        task.setStatus("Assigned");
        taskRepository.save(task);
        List<Task> tasks = (List) taskRepository.findAll();
        return tasks;
    }

    @PostMapping("/tasks/{id}/images")
    public Task uploadFile(
            @PathVariable String id,
            @RequestPart(value = "file") MultipartFile file
    ){
        Task task = taskRepository.findById(id).get();
        String pic = this.s3Client.uploadFile(file);
        task.setPic(pic);
        String[] blah = pic.split("/");
        String tb = blah[blah.length -1];
        task.setRepic("https://reacttaskapp-resized.s3-us-west-1.amazonaws.com/resized-" + tb);
        taskRepository.save(task);
        return task;
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable String id){
        taskRepository.deleteById(id);
    }
}
