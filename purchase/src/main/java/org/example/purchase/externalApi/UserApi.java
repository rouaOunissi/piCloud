package org.example.purchase.externalApi;

import com.pi.users.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserApi" , url = "http://localhost:8010")
public interface UserApi {

@GetMapping("/api/v1/users/auth/user/{id}")
public User getUserById(@PathVariable Long id);

}
