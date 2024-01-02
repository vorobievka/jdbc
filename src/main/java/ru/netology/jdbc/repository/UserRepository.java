package ru.netology.jdbc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository  {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


//    final String script = read("myScript.sql");
    Map<String, Object> customerParameters;
    String script;
    List<String> products;

    @Autowired
    public UserRepository() {
          script = read("myScript.sql");
          customerParameters = new HashMap<String, Object>();
          products = new ArrayList<>();
//        System.out.println(script);

    }

//    @Override
//    public void run(String... args) throws SQLException {
//        Map<String, Object> params = new HashMap<>();
//        String namet = "'alexey'";
//        params.put("names", namet);
//        List<String> chren = namedParameterJdbcTemplate.query(
//                "select* from public.orders o join public.customers c ON c.id = o.customer_id WHERE c.name = :names;",
//                params,
//                (rs, rowNum) -> {
//                    String nameuuu = rs.getString("name_product");
//                    return nameuuu;
//                }
//        );
//        System.out.println(namet + "  " + chren + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//    }
//



    public List<String> getProductName(String name) throws SQLException {
        customerParameters.put("names", "'" + "alexey" + "'");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("***************" + customerParameters);
        System.out.println("******************" + script);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println( dataSource.getConnection().getSchema());
        System.out.println( dataSource.getConnection().getMetaData());
//        System.out.println(namedParameterJdbcTemplate.getCacheLimit());
          System.out.println("fz " + namedParameterJdbcTemplate.queryForList(script, customerParameters, String.class));
        System.out.println("fzo " + namedParameterJdbcTemplate.queryForObject("select count(*) from customers where name = 'alexey' -- :names", customerParameters, Integer.class));
        List<String> productss =  namedParameterJdbcTemplate.query("select * from public.customers where name = 'alexey' -- :names", customerParameters, (rs, rowNum) -> {String name_p = rs.getString("name");
            return name_p;});
        System.out.println("products: " + productss);

        //##############

        List<String> productssp =  namedParameterJdbcTemplate.query("select product_name from public.orders o join public.customers c ON c.id = o.customer_id where c.name ilike 'alexey' -- :names", customerParameters, (rs, rowNum) -> {String name_p = rs.getString("product_name");
            return name_p;});
        System.out.println("productsp: " + productssp);

        //############

        List<String> productssp9 =  namedParameterJdbcTemplate.query("select product_name from public.orders o join public.customers c ON c.id = o.customer_id where c.name ilike :names", customerParameters, (rs, rowNum) -> {String name_p = rs.getString("product_name");
            return name_p;});
        System.out.println("productsp9: " + productssp9);

        //##########


        //products.addAll(namedParameterJdbcTemplate.queryForList(script, customerParameters, String.class));
        products.add("Figa");
        return products;

//        System.out.println(name);
// //       products = namedParameterJdbcTemplate.queryForList(script, customerParameters, String.class);
//        System.out.println( "Products: " + products);
//        if(!products.isEmpty()){
//            //return products;
//            return Collections.emptyList();
//        }
//        return Collections.emptyList();
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
