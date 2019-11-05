package com.javapractice.practice2.Controller;

import com.javapractice.practice2.model.Person;
import com.javapractice.practice2.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    private final PersonService personService;

    @Autowired
    public AppController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("person")
    public String demo(Model model) {
        //Person person1 = new Person("Luk");
        //personService.create(person1)
        model.addAttribute("message", "ceva");
        return "person/person";
    }

}
