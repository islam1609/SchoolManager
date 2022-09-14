package PETProject.BiGTask.service;


import PETProject.BiGTask.model.Subject;
import PETProject.BiGTask.model.Task;
import PETProject.BiGTask.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private PETProject.BiGTask.repository.taskRepository taskRepository;

    @Autowired
    private PETProject.BiGTask.repository.subjectRepository subjectRepository;

    @Autowired
    private PETProject.BiGTask.repository.typeRepository typeRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    public Task addTask(Task task){
         return taskRepository.save(task);
    }

    public Task getTask(Long id){
        return taskRepository.getReferenceById(id);
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }

    public Type getType(Long id){
        return typeRepository.getReferenceById(id);
    }

    public void deleteTask(Task task){
         taskRepository.delete(task);
    }

    public Subject getSubject(Long id){
        return subjectRepository.getReferenceById(id);
    }

    public void editTask(Task task){
        taskRepository.save(task);
    }

    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public void addType(Type type) {
        typeRepository.save(type);
    }
}

