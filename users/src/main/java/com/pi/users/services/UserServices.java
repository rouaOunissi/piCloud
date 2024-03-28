package com.pi.users.services;


import com.pi.users.entities.User;

import java.util.List;

public interface UserServices {
     User updateUser(Long id, User userDetails);
     void deleteUser(Long id) ;

     User getUserById(Long id);

     List<User> findAll() ;




}
