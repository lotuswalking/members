package com.sushi.members.Controller;

import com.sushi.members.jpa.PersonRepository;
import com.sushi.members.jpa.entity.Person;
import lombok.RequiredArgsConstructor;
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

@Log
@Controller
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {
        Person person;
        person = model.containsAttribute("person") ? (Person) model.get("person") : new Person();
        model.addAttribute("person", person);
    }

    @GetMapping("/person/new")
    public String newPerson(Model model) {
        Person person = new Person();
        person.setPoint(300L);
        person.setRegisterDate(LocalDate.now());
        model.addAttribute("person", person);
        model.addAttribute("curMode", "new");
        return "person";
    }

    @PostMapping(value = "/person/save")
    public String savePerson(@RequestParam(value = "curMode", required = true) String curMode,
                      @ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/home";
    }

}
