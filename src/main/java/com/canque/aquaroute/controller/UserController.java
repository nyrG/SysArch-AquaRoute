package com.canque.aquaroute.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canque.aquaroute.model.User;
import com.canque.aquaroute.repository.UserRepository;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/findUsers")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/findUser/{email}")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email or password cannot be empty");
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        if (user.getPassword().equals(password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage.toString().trim());
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
        }

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
    
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.deleteByEmail(email);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/updateUser/{email}")
    public ResponseEntity<String> updateUserByEmail(@Valid @RequestParam String email, @RequestBody User newUser, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString().trim());
        }

        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setMiddleName(newUser.getMiddleName());
        user.setBirthdate(newUser.getBirthdate());
        user.setStreet(newUser.getStreet());
        user.setBarangay(newUser.getBarangay());
        user.setCity(newUser.getCity());
        user.setProvince(newUser.getProvince());
        user.setPassword(newUser.getPassword());

        userRepository.save(user);

        return ResponseEntity.ok("User account updated successfully");
    }
}
