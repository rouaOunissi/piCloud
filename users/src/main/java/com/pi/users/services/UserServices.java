package com.pi.users.services;


import com.pi.users.entities.User;

public interface UserServices {
     User updateUser(Long id, User userDetails);
     void deleteUser(Long id) ;


}
