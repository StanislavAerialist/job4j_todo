package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HibernateTaskService;
import ru.job4j.todo.service.PriorityService;

@Controller
@AllArgsConstructor
public class TaskController {
    private final HibernateTaskService taskService;
    private final PriorityService priorityService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("/done")
    public String getCompleted(Model model) {
        model.addAttribute("tasks", taskService.findSortedByDone(true));
        return "index";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findSortedByDone(false));
        return "index";
    }

    @GetMapping("/add")
    public String getCreationPage(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/create";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Task task, @SessionAttribute User user, Model model) {
        task.setUser(user);
        if (taskService.add(task) != null) {
            model.addAttribute("message", "Задание добавлено успешно!");
            return "tasks/success";
        }
        model.addAttribute("message", "Ошибка создания задания");
        return "errors/404";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, @SessionAttribute User user, Model model) {
        task.setUser(user);
        if (!taskService.update(task)) {
            model.addAttribute("message", "Ошибка редактирования задания");
            return "errors/404";
        }
        model.addAttribute("message", "Задание отредактировано успешно!");
        return "tasks/success";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!taskService.delete(id)) {
            model.addAttribute("message", "Ошибка удаления задания");
            return "errors/404";
        }
            return "redirect:/index";
    }

    @GetMapping("/done/{id}")
    public String updateState(Model model, @PathVariable int id) {
        if (!taskService.setDone(id)) {
            model.addAttribute("message", "Ошибка выполнения задания");
            return "errors/404";
        }
        model.addAttribute("message", "Заданаие выполнено!");
        return "tasks/success";
    }
}
