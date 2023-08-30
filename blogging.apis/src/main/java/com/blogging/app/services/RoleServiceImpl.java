package com.blogging.app.services;

import com.blogging.app.entity.Role;
import com.blogging.app.payload.RoleDto;
import com.blogging.app.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role=this.modelMapper.map(roleDto,Role.class);
        return this.modelMapper.map(this.roleRepo.save(role),RoleDto.class);
    }

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roles=this.roleRepo.findAll();
        return roles.stream().map(role->this.modelMapper.map(role,RoleDto.class)).collect(Collectors.toList());
    }
}
