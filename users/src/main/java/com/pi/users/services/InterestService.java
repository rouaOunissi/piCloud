package com.pi.users.services;

import com.pi.users.entities.Interest;

import java.util.List;

public interface InterestService {
    public List<Interest> getAllInterests();

    Interest add(Interest interest);
}
