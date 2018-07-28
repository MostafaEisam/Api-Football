package com.example.mostafaeisam.apifootball.services;

public interface RequestListener {
    void onSuccess(Object object);
    void  onFailure(int errorCode);
}
