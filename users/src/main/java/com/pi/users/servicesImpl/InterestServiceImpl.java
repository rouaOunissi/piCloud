package com.pi.users.servicesImpl;

import com.pi.users.entities.Interest;
import com.pi.users.repository.InterestRepository;
import com.pi.users.services.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {
    @Autowired
    private InterestRepository interestRepository;
    @Override
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    @Override
    public Interest add(Interest interest) {
        return interestRepository.save(interest);
    }
}
