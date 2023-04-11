package com.sushi.members.Controller;

import com.sushi.members.jpa.RoleRepository;
import com.sushi.members.jpa.UserRepository;
import com.sushi.members.jpa.entity.Role;
import com.sushi.members.jpa.entity.User;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@Log
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("allRoles",roles);
        return "user";
    }
    @GetMapping("/user/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
       User user = userRepository.getUserById(id);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("allRoles",roles);
       model.addAttribute("user",user);
       model.addAttribute("curMode","edit");
        return "user";
    }
    @GetMapping("user/remove/{id}")
    public String removeUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @PostMapping(value = "/user/save")
    public String saveUSer(@RequestParam(value = "curMode", required = false) String curMode,
                             @Valid @ModelAttribute User user,
                           BindingResult result
    ) {
        if(result.hasErrors()) {
            return "user";
        }
        String passwordHash=String.valueOf(user.getPassword().hashCode());
        user.setPassword(passwordHash);
//        user.setRoles(user.getRoles().toUpperCase());
        userRepository.save(user);
        return "redirect:/users";
    }
}
