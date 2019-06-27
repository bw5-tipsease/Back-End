package com.lambdaschool.tipsease.repository;

import com.lambdaschool.tipsease.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
