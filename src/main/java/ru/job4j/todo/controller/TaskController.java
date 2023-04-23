package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.HibernateTaskService;
import ru.job4j.todo.service.PriorityService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

import static org.springframework.context.i18n.LocaleContextHolder.setTimeZone;


@Controller
@AllArgsConstructor
public class TaskController {
    private final HibernateTaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping({"/", "/index"})
    public String getAll(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.findAll();
        tasks.forEach(t -> setTimeZone(t, user));
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/done")
    public String getCompleted(Model model, @SessionAttribute User user) {
        List<Task> doneTasks = taskService.findSortedByDone(true);
        doneTasks.forEach(t -> setTimeZone(t, user));
        model.addAttribute("tasks", doneTasks);
        return "index";
    }

    @GetMapping("/new")
    public String getNew(Model model, @SessionAttribute User user) {
        List<Task> newTasks = taskService.findSortedByDone(false);
        newTasks.forEach(t -> setTimeZone(t, user));
        model.addAttribute("tasks", newTasks);
        return "index";
    }

    @GetMapping("/add")
    public String getCreationPage(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Task task, @SessionAttribute User user,
                         @RequestParam("categoriesId") List<Integer> categoriesId, Model model) {
        task.setUser(user);
        task.setCategories(categoryService.findByIdList(categoriesId));
        if (taskService.add(task) != null) {
            model.addAttribute("message", "Задание добавлено успешно!");
            return "tasks/success";
        }
        model.addAttribute("message", "Ошибка создания задания");
        return "errors/404";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, @SessionAttribute User user) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        setTimeZone(taskOptional.get(), user);
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
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, @SessionAttribute User user,
                         @RequestParam("categoriesId") List<Integer> categoriesId, Model model) {
        task.setUser(user);
        task.setCategories(categoryService.findByIdList(categoriesId));
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

    private static void setTimeZone(Task task, User user) {
        ZoneId defTz = TimeZone.getDefault().toZoneId();
        ZoneId userTimeZone = ZoneId.of(user.getTimezone());
        LocalDateTime dateTime = task.getCreated()
                .atZone(defTz)
                .withZoneSameInstant(userTimeZone)
                .toLocalDateTime();
        task.setCreated(dateTime);
    }
}
