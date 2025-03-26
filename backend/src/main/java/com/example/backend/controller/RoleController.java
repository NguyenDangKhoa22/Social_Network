package com.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.RoleRequest;
import com.example.backend.dto.response.RoleReponse;
import com.example.backend.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {
    RoleService roleService;
    @PostMapping
    ApiReponse<RoleReponse> create(@RequestBody RoleRequest request){
        return ApiReponse.<RoleReponse>builder().result(roleService.create(request)).build();
    }
    @GetMapping
    ApiReponse<List<RoleReponse>> getAll(){
        return ApiReponse.<List<RoleReponse>>builder().result(roleService.getAll()).build();
    }
    @DeleteMapping("{role}")
    ApiReponse<Void> delete(@PathVariable String role){
        roleService.deleteById(role);
        return ApiReponse.<Void>builder().build();
    }
}
