package com.lambdaschool.tipsease.services;

import com.lambdaschool.tipsease.models.Restaurant;
import com.lambdaschool.tipsease.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restrepos;

    @Override
    public List<Restaurant> findAll()
    {
        List<Restaurant> list = new ArrayList<>();
        restrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
}
