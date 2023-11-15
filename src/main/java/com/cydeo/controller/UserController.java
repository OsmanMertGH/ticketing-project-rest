package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @RolesAllowed({"Manager","Admin"})
    public ResponseEntity<ResponseWrapper> getUsers() {
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "Users retrieved",
                        userService.listAllUsers(),
                        HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("userName") String userName) {
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "User is succesfully retrieved",
                        userService.findByUserName(userName),
                        HttpStatus.OK
                ));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUsers(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper(
                        "User Created",
                        userDTO,
                        HttpStatus.CREATED
                ));
    }

    @PutMapping()
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUsers(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "User Created",
                        HttpStatus.CREATED
                ));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> deleteUsers(@PathVariable("userName") String userName) {

        userService.delete(userName);
        return ResponseEntity
                .ok(new ResponseWrapper(
                        "User Deleted",
                        HttpStatus.OK)
                );
    }

}
