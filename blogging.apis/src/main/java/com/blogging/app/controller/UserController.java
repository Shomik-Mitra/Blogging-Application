package com.blogging.app.controller;

import com.blogging.app.payload.ApiResponse;
import com.blogging.app.payload.UserDto;
import com.blogging.app.services.UserService;
import com.blogging.app.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    //Post - create User
    @PostMapping("/{role_Ids}")
    private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto,@PathVariable("role_Ids") List<Integer> ids){
        return new ResponseEntity<>(this.userService.createUser(userDto,ids), HttpStatus.CREATED);
    }
    //Put - update User
    @PutMapping("/{userId}")
    private ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") int uid){
        return new ResponseEntity<>(this.userService.updateUser(userDto,uid),HttpStatus.OK);
    }
    //Delete - delete User
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{userId}")
    private ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }

    //Get - get User
    @GetMapping("/")
    private ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserDto> getBuUserId(@PathVariable("userId") int uid){
        return ResponseEntity.ok(this.userService.getById(uid));
    }

}
