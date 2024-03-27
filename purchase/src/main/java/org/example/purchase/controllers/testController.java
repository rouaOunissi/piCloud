package org.example.purchase.controllers;

import com.pi.cours.models.Course;
import com.pi.users.entities.User;
import com.stripe.exception.StripeException;
import org.example.purchase.entities.Seller;
import org.example.purchase.externalApi.CourseApi;
import org.example.purchase.externalApi.UserApi;
import org.example.purchase.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@CrossOrigin
public class testController {

    @Autowired
    CourseApi courseApi;

    @Autowired
    SellerService sellerService ;
//@Autowired
    //UserApi userApi ;

    @GetMapping("/api/v1/cours/{id}")
    public Course getCoursById(@PathVariable Long id) {
        return courseApi.getCoursById(id);
    }


    @PostMapping("/seller")
    public void createSeller(@RequestBody Seller seller) throws StripeException {
        sellerService.createSeller(seller);
    }

    @PostMapping("/pay")
    public void cretaePayment() throws StripeException {
      //  sellerService.cretaePayment();

    }
}




    /*@GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userApi.getUserById(id);
    }

}
     */

