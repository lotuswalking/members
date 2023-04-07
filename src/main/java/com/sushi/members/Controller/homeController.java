package com.sushi.members.Controller;

import com.sushi.members.jpa.PersonRepository;
import com.sushi.members.jpa.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
public class homeController {
    private final PersonRepository personRepository;
    @Value("${spring.jpa.defaultPageSize}")
    private int defaultPageSize;
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    @GetMapping("/members")
    public String members() {
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(ModelMap model) {

        List<Person> persons = personRepository.findAll();

        model.addAttribute("persons",persons);
        return "home";
    }
}
