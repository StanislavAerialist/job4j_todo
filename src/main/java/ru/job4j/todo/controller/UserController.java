package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.TimeZone;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/register")
    public String getRegistrationPage(Model model) {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        model.addAttribute("zones", zones);
        return "users/register";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute User user, @RequestParam("zoneId") TimeZone timeZone, Model model) {
        user.setTimezone(timeZone.getID());
        var savedUser = userService.add(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "errors/404";
        }
        return "redirect:/index";
    }

    @GetMapping("/users/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/users/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
