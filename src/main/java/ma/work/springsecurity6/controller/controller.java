package ma.work.springsecurity6.controller;

import ma.work.springsecurity6.model.OurUser;
import ma.work.springsecurity6.repository.OurUserRepository;
import ma.work.springsecurity6.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class controller {
    @Autowired
    private OurUserRepository ourUserRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping(path = "/")
    public String goHome(){
        return "this is publickily accessible withing needing authentication.";
    }
    @PostMapping(path = "/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody OurUser ourUser){
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        OurUser result = ourUserRepository.save(ourUser);
        if (result.getId() > 0) return ResponseEntity.ok("User Saved");
        return ResponseEntity.status(404).body("Error, User not saved");
    }
    @GetMapping(path = "/product/all")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }
    @GetMapping(path = "/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(ourUserRepository.findAll());
    }
    @GetMapping(path = "/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails(){
        return ResponseEntity.ok(ourUserRepository.findByEmail(getLoggedInUserDetails().getUsername()));
    }
    public UserDetails getLoggedInUserDetails(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }



}
