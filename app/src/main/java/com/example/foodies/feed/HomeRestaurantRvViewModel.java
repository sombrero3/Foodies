package com.example.foodies.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodies.model.Restaurant;

import java.util.List;

public class HomeRestaurantRvViewModel extends ViewModel {
    LiveData<List<Restaurant>> restaurantList;
}
