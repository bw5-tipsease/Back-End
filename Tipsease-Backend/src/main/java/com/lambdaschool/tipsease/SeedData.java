package com.lambdaschool.tipsease;

import com.lambdaschool.tipsease.models.Role;
import com.lambdaschool.tipsease.models.User;
import com.lambdaschool.tipsease.models.UserRoles;
import com.lambdaschool.tipsease.services.RoleService;
import com.lambdaschool.tipsease.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        roleService.save(r1);
        roleService.save(r2);

        // admin, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        User u1 = new User("Jtonna", "java", admins);
        userService.save(u1);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        User u3 = new User("Alex", "alexspassword", users);
        userService.save(u3);

        User u4 = new User("Joseph", "josephspassword", users);
        userService.save(u4);

        User u5 = new User("Marolon", "marlonspassword", users);
        userService.save(u5);

    }
}