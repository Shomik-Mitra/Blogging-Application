package com.blogging.app.controller;

import com.blogging.app.payload.RoleDto;
import com.blogging.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/")
    private ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        return new ResponseEntity<>(this.roleService.createRole(roleDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    private ResponseEntity<List<RoleDto>> getAllRoles(){
        return new ResponseEntity<>(this.roleService.getRoles(),HttpStatus.OK);
    }
}
