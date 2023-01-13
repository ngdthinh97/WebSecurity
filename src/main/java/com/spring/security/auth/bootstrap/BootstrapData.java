package com.spring.security.auth.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.auth.entity.*;
import com.spring.security.auth.repository.*;
import com.spring.security.auth.repository.es.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;

@Component
@AllArgsConstructor
public class BootstrapData implements ApplicationListener<ApplicationReadyEvent> {
    public UserDetailRepository userDetailRepository;
    private PasswordEncoder passwordEncoder;
    private BookRepository bookRepository;
    private LocationRepository locationRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private ArticleRepository articleRepository;

    final ObjectMapper ob = new ObjectMapper();

    @SneakyThrows
    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        System.out.println("------------------- Bootstrap Data Elastic -------------------");

//        elasticsearchClient.indexOps(Article.class).create();
        Iterable<Article> lst =   articleRepository.findAll();
        lst.forEach(s -> {
            System.out.println(s.toString());
        });

        System.out.println(articleRepository.findAll());
        System.out.println("------------------- Bootstrap Data -------------------");
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setPassword(passwordEncoder.encode("userU"));
        userEntity.setRole("user");

        UserEntity saved = userDetailRepository.save(userEntity);

        Set<UserEntity> adminGroup = new HashSet<>();
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUsername("Admin");
        userEntity2.setPassword(passwordEncoder.encode("adminP"));

        adminGroup.add(userEntity2);

        Set<PrincipleGroup> setPrint = new HashSet<>();
        PrincipleGroup adminRole = new PrincipleGroup();
        adminRole.setCode("admin");
        adminRole.setName("Raphael Tx");
        adminRole.setUsers(adminGroup);

        setPrint.add(adminRole);
        userEntity.setUserGroups(setPrint);

        userEntity2.setUserGroups(setPrint);
        UserEntity saved2 = userDetailRepository.save(userEntity2);

        Customer cus = Customer.builder().name("Thinh").build();
        Customer cusSaved = customerRepository.save(cus);

        Customer cus2 = Customer.builder().name("Rapael").build();
        Customer cus2Saved = customerRepository.save(cus2);

        Customer cus3 = Customer.builder().name("AsQuarkus").build();
        Customer cus3Saved = customerRepository.save(cus3);

        Order order = Order.builder().customer(cusSaved).build();
        orderRepository.save(order);

        Order order1 = Order.builder().customer(cusSaved).build();
        orderRepository.save(order1);

        Order order2 = Order.builder().customer(cusSaved).build();
        orderRepository.save(order2);

        Order order3 = Order.builder().customer(cusSaved).build();
        orderRepository.save(order3);

        Optional<List<Order>> orderById = orderRepository.findByOrderByCustomer();

        Location local = new Location();
        local.setName("Fahasa 9 disctrict");
        Location LocalSaved = locationRepository.save(local);

        Book book = new Book();
        book.setName("Sword art online");
        book.setLocation(LocalSaved);
        bookRepository.save(book);

    }
}