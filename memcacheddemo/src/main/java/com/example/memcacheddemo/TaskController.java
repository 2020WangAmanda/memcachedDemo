package com.example.memcacheddemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/05/31
 */
@Controller
@RequestMapping("/")
public class TaskController {
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    @RequestMapping(method = RequestMethod.GET)
    public  String showAllTasks(ModelMap model){
        Iterable<Task> tasks=this.taskRepository.findAllCached();
        //model 干啥的
        model.addAttribute("tasks",tasks);
        model.addAttribute("newTask",new Task());

        return "task";

    }

    @PostMapping
    public String newTask(ModelMap model,
                          @ModelAttribute("newTask") @Valid Task task,
                          BindingResult result) {
        if (!result.hasErrors()) {
            this.taskRepository.saveAndClearCache(task);
        }
        return showAllTasks(model);
    }

    @RequestMapping(method=RequestMethod.DELETE)
    public String deleteTask(ModelMap model,@RequestParam("taskId") long id){
        this.taskRepository.deleteByIdAndClearCache(id);
        return showAllTasks(model);
    }

}
