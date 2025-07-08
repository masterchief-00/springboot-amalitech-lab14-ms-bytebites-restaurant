package com.kwizera.restaurantservice.services;

import com.kwizera.restaurantservice.domain.entities.Restaurant;

import java.util.List;

public interface RestaurantServices {
    Restaurant create(Restaurant restaurant);

    List<Restaurant> getRestaurants();
}
