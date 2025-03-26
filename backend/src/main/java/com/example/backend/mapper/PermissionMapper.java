package com.example.backend.mapper;

import org.mapstruct.Mapper;

import com.example.backend.dto.request.PermissionRequest;
import com.example.backend.dto.response.PermissionReponse;
import com.example.backend.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionReponse toPermissiResponse(Permission permission);
    
}
