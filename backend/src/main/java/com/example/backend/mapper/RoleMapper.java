package com.example.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.backend.dto.request.RoleRequest;
import com.example.backend.dto.response.RoleReponse;
import com.example.backend.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)

    Role toRole(RoleRequest request);

    RoleReponse toRoleResponse(Role role);
    
}
