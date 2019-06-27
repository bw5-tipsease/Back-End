package com.lambdaschool.tipsease.repository;

import com.lambdaschool.tipsease.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>
{
    Restaurant findByName(String name);
}