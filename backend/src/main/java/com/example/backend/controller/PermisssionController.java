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
import com.example.backend.dto.request.PermissionRequest;
import com.example.backend.dto.response.PermissionReponse;
import com.example.backend.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermisssionController {
    PermissionService permissionService;
    @PostMapping
    ApiReponse<PermissionReponse> create(@RequestBody PermissionRequest request){
        return ApiReponse.<PermissionReponse>builder().result(permissionService.create(request)).build();
    }
    @GetMapping
    ApiReponse<List<PermissionReponse>> getAll(){
        return ApiReponse.<List<PermissionReponse>>builder().result(permissionService.getAll()).build();
    }
    @DeleteMapping("{permission}")
    ApiReponse<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        return ApiReponse.<Void>builder().build();
    }
}
