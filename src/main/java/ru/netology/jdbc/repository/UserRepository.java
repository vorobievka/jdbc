package ru.netology.jdbc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Map<String, Object> customerParameters;
    private final String script;
    private final List<String> products;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.script = read("myScript.sql");
        this.customerParameters = new HashMap<String, Object>();
        this.products = new ArrayList<>();
    }

    @Transactional
    public List<String> getProductName(String name) throws SQLException {
        customerParameters.put("names", name);
        products.clear();
        products.addAll(namedParameterJdbcTemplate.queryForList(script, customerParameters, String.class));
        return products;
    }

    public static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
