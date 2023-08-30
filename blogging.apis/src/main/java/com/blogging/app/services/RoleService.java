package com.blogging.app.services;

import com.blogging.app.payload.RoleDto;

import java.util.List;

public interface RoleService {

    //post Role
    RoleDto createRole(RoleDto roleDto);

    //get Role

    List<RoleDto> getRoles();
}
