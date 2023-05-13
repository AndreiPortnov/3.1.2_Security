package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class InitDb {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitDb(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void fillDb() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        User admin = new User("Tom", "firstName1", "lastName1", LocalDate.now(), passwordEncoder.encode("tom"));
        admin.addRole(roleService.add(roleAdmin));
        userService.add(admin);

        User user = new User("Ann", "firstName2", "lastName2", LocalDate.now(), passwordEncoder.encode("ann"));
        user.addRole(roleService.add(roleUser));
        userService.add(user);

        User userAdmin = new User("Lip", "firstName3", "lastName3", LocalDate.now(), passwordEncoder.encode("lip"));
        userAdmin.addRole(roleService.add(roleUser));
        userAdmin.addRole(roleService.add(roleAdmin));
        userService.add(userAdmin);
    }
}
