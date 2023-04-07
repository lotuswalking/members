package com.sushi.members.Controller;

import com.sushi.members.jpa.entity.User;
import com.sushi.members.jpa.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
@Log
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {
        User user;
        user = model.containsAttribute("user") ? (User) model.get("user") : new User();
        model.addAttribute("user", user);
    }
    @GetMapping("/users")
    public  String listUsers(Model model) {
        List<User> userList=userRepository.findAll();

        model.addAttribute("users",userList);
        return "users";
    }
    @GetMapping("/user/new")
    public String newUser(Model model) {
        User user = new User();
        user.setActive(true);
        user.setEffectiveData(LocalDate.now());
        model.addAttribute("user", user);
        model.addAttribute("curMode", "new");
        return "user";
    }

    @PostMapping(value = "/user/save")
    public String saveUSer(@RequestParam(value = "curMode", required = false) String curMode,
                             @ModelAttribute User user) {

        userRepository.save(user);
        return "redirect:/users";
    }
}
