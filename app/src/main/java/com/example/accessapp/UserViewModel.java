package com.example.accessapp;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    public UserViewModel(){
    }

    public static void getHolidays(Activity context) {
        UserRepo userRepo = new UserRepo();
        userRepo.requestHolidays(context);
    }

}