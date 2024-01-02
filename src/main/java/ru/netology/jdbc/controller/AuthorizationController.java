package ru.netology.jdbc.controller;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.jdbc.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class AuthorizationController {

    @Autowired
    private final UserRepository repository;

    public AuthorizationController(UserRepository repository) {
        this.repository = repository;
    }

    //    public AuthorizationController()  {
//        this.repository = new UserRepository();
//
//    }
    @GetMapping("/products/fetch-product")
    public List<String> getAuthorities(@RequestParam("name") String name) throws SQLException {
        return repository.getProductName(name);
        //System.out.println("Repository: " + repository.getProductName(name));
        //return Collections.emptyList();

    }

}
