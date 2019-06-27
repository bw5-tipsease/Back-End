package com.lambdaschool.tipsease.services;

import com.lambdaschool.tipsease.models.User;

import java.util.List;

public interface UserService
{
    User findUserData(String username);

    // premade code

    List<User> findAll();

    User findUserById(long id);

    void delete(long id);

    User save(User user);

    User update(User user, long id);
}