package com.blogging.app.services;

import com.blogging.app.entity.Role;
import com.blogging.app.entity.User;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payload.UserDto;
import com.blogging.app.repositories.RoleRepo;
import com.blogging.app.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDto createUser(UserDto user,List<Integer> roles) {
        List<Role> rol=new ArrayList<>();
        for(Integer role:roles){
         rol.add(this.roleRepo.findById(role).orElseThrow(()->new ResourceNotFoundException("Role"," Id ",role)));
        }
       User us=this.modelMapper.map(user,User.class);
        us.setRoles(rol);

        return this.modelMapper.map(this.userRepo.save(us),UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto user, int id) {
        User us=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User"," Id ",id));
        us.setName(user.getName());
        us.setEmailId(user.getEmailId());
        us.setPassword(user.getPassword());
        us.setAbout(user.getAbout());
        return this.userToUserDto(this.userRepo.save(us));
    }

    @Override
    public UserDto getById(int id) {
        Optional<User> us=this.userRepo.findById(id);
        if(us.isPresent()) {
            return this.userToUserDto(us.get());
        }else{
            throw new ResourceNotFoundException("User"," Id ",id);
        }
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> li=this.userRepo.findAll();
//        List<UserDto> userdto=new ArrayList<>();
//        for(User u:li){
//            userdto.add(this.userToUserDto(u));
//        }
//        return userdto;
        return li.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int id) {
        User user=this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",id));
        this.userRepo.delete(user);
    }
    private User userDtoToUser(UserDto user){
          User us=this.modelMapper.map(user,User.class);
//        User us=new User();
//        us.setId(user.getId());
//        us.setName(user.getName());
//        us.setEmailId(user.getEmailId());
//        us.setPassword(user.getPassword());
//        us.setAbout(user.getAbout());
        return us;
    }
    private UserDto userToUserDto(User u){
        UserDto user=this.modelMapper.map(u, UserDto.class);
//        UserDto user=new UserDto(u.getId(),u.getName(),u.getEmailId(),u.getPassword(),u.getAbout());
        return user;
    }
}
