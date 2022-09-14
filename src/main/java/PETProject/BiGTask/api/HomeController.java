package PETProject.BiGTask.api;


import PETProject.BiGTask.model.Subject;
import PETProject.BiGTask.model.User;
import PETProject.BiGTask.service.UserService;
import PETProject.BiGTask.model.Task;
import PETProject.BiGTask.model.Type;
import PETProject.BiGTask.service.TaskService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @GetMapping(value = "/")
    public String indexPage(){
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/menu")
    public String menu(){
        return "menu";
    }

    @GetMapping(value = "/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping(value = "/toregister")
    public String toRegister(@RequestParam(name = "user_email") String userEmail,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "user_re_password") String rePassword,
                             @RequestParam(name = "user_full_name") String fullName,
                             @RequestParam(name = "user_phone_number") int number,
                             @RequestParam(name = "user_adress") String address){

        if(password.equals(rePassword)){
            User newUser = new User();
            newUser.setEmail(userEmail);
            newUser.setPassword(password);
            newUser.setFullName(fullName);
            newUser.setAddress(address);
            newUser.setNumber(number);
            newUser = userService.registerUser(newUser);
            if(newUser!=null){
                return "redirect:/?success";
            }
        }
        return "redirect:/register?error";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profile(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        return "profile";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @GetMapping(value = "/teacherPanel")
    public String teacherPanel(Model model){
        List<Task> allTasks = taskService.getAllTasks();
        List<Subject> allSubjects = taskService.getAllSubjects();
        List<Type> allTypes = taskService.getAllTypes();
        List<User> allStudents = userService.getAllStudent();
        model.addAttribute("students",allStudents);
        for(User st : allStudents){
            System.out.println(st.getFullName());
        }
        model.addAttribute("subjects",allSubjects);
        model.addAttribute("tasks",allTasks);
        model.addAttribute("allTypes",allTypes);
        return "teacherPanel";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @PostMapping(value = "/addTask")
    public String addItem(@RequestParam(name = "task_desc")String description,
                          @RequestParam(name = "task_subject_id") Long id,
                          @RequestParam(name = "deadlineDate") Date date,
                          @RequestParam(name = "task_type_id") Long task_type_id){
            Task task = new Task();
            task.setUser(getCurrentUser());
            task.setType(taskService.getType(task_type_id));
            task.setDate(date);
            task.setDescription(description);
            task.setSubject(taskService.getSubject(id));
            taskService.addTask(task);
            return "redirect:/teacherPanel";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @PostMapping(value = "/addSubject")
    public String addSubject(@RequestParam(name = "subject_name")String name){
        Subject subject = new Subject();
        subject.setName(name);
        taskService.addSubject(subject);
        return "redirect:/teacherPanel";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @PostMapping(value = "/addType")
    public String addType(@RequestParam(name = "type_name")String name){
        Type type = new Type();
        type.setName(name);
        taskService.addType(type);

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        
        return "redirect:/teacherPanel";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id") Long id,
                          Model model){
        List<Type> allTypes = taskService.getAllTypes();
        model.addAttribute("allTypes",allTypes);
        List<Subject> subjects = taskService.getAllSubjects();
        model.addAttribute("subjects",subjects);
        Task task = taskService.getTask(id);
        model.addAttribute("task",task);
        return "details";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name = "taskId") Long id){
        Task task = taskService.getTask(id);
        taskService.deleteTask(task);
        return "redirect:/teacherPanel";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @PostMapping(value = "/editTask")
    public String editItem(@RequestParam(name = "task_type_id")Long task_type_id,
                          @RequestParam(name = "task_desc")String description,
                          @RequestParam(name = "task_subject_id") Long subject_id,
                           @RequestParam(name = "deadlineDate") Date date,
                           @RequestParam(name = "taskId") Long taskId){
        Task task = new Task();
        task.setUser(getCurrentUser());
        task.setType(taskService.getType(task_type_id));
        task.setDate(date);
        task.setDescription(description);
        task.setId(taskId);
        task.setSubject(taskService.getSubject(subject_id));
        taskService.editTask(task);
        return "redirect:/teacherPanel";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/forbidden")
    public String forbiddenPage(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "403";
    }
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

}
