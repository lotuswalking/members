package com.sushi.members.Controller;

import com.sushi.members.Services.UserService;
import com.sushi.members.jpa.PersonRepository;
import com.sushi.members.jpa.entity.Person;
import com.sushi.members.jpa.util.CsvFileGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Controller
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CsvFileGenerator csvFileGenerator;
    @Autowired
    private UserService userService;

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


        Map<String,String> map = new HashMap<>();
        map.put("1","已注册");
        map.put("2","激活");
        model.addAttribute("allTypes", map);
        model.addAttribute("person", person);
        model.addAttribute("curMode", "new");
        return "person";
    }
    @GetMapping("/export-to-csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition","Attachment; filename=members.csv");
        csvFileGenerator.writePersonToCsv(userService.getAllPerson(),response.getWriter());
    }
    private void setExcelRespProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }
    @GetMapping("/person/edit/{id}")
    public String editPerson(Model model, @PathVariable Long id) {
        Person person = personRepository.getPersonById(id);
        Map<String,String> map = new HashMap<>();
        map.put("1","已注册");
        map.put("2","激活");
        model.addAttribute("allTypes", map);
        model.addAttribute("person", person);
        model.addAttribute("curMode", "edit");
        return "person";
    }
    @GetMapping("/person/remove/{id}")
    public String removePerson(Model model, @PathVariable Long id) {

        personRepository.deleteById(id);

        return "redirect:/home";
    }
    @PostMapping(value = "/person/save")
    public String savePerson(@RequestParam(value = "curMode", required = true) String curMode,
                      @ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/home";
    }

}
