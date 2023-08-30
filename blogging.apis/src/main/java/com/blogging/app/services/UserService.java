package com.blogging.app.services;

import com.blogging.app.payload.UserDto;

import java.util.List;

public interface UserService {

     public UserDto createUser(UserDto user,List<Integer> roles);
     public UserDto updateUser(UserDto user,int id);

     public UserDto getById(int id);
     public List<UserDto> getAllUser();

     public void deleteUser(int id);



}
