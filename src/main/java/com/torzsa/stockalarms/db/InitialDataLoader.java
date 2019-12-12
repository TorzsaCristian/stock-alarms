//package com.torzsa.stockalarms.db;
//
//import com.torzsa.stockalarms.model.Role;
//import com.torzsa.stockalarms.model.User;
//import com.torzsa.stockalarms.repository.RoleRepository;
//import com.torzsa.stockalarms.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.util.Collections;
//import java.util.HashSet;
//
//@Component
//public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    private boolean alreadySetup = false;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        if (alreadySetup)
//            return;
//
//        createRoleIfNotFound("ROLE_ADMIN");
//        createRoleIfNotFound("ROLE_USER");
//
//        // Create Admin user and User user
//        User admin = createaAdminIfNotFound();
//        User user = createUserIfNotFound();
//
//        // Save to database
//        userRepository.save(admin);
//        userRepository.save(user);
//
//        alreadySetup = true;
//    }
//
//    @Transactional
//    User createaAdminIfNotFound() {
//        User admin = userRepository.findByEmail("admin@admin.com");
//        if (admin == null) {
//            admin = new User();
//            admin.setFirstName("Admin");
//            admin.setLastName("Admin");
//            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
//            admin.setEmail("admin@admin.com");
//            admin.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_ADMIN"))));
//        }
//        return admin;
//    }
//
//    @Transactional
//    User createUserIfNotFound() {
//        User user = userRepository.findByEmail("user@email.com");
//        if (user == null) {
//            user = new User();
//            user.setFirstName("User");
//            user.setLastName("Userescu");
//            user.setPassword(bCryptPasswordEncoder.encode("password"));
//            user.setEmail("user@email.com");
//            user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName("ROLE_USER"))));
//        }
//        return user;
//    }
//
//    @Transactional
//    Role createRoleIfNotFound(
//            String name) {
//
//        Role role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new Role(name);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//}
