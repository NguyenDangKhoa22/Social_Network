package com.example.backend.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.RoleRequest;
import com.example.backend.dto.response.RoleReponse;
import com.example.backend.mapper.RoleMapper;
import com.example.backend.repository.PermissionRepository;
import com.example.backend.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleReponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role =  roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleReponse> getAll(){
        var role = roleRepository.findAll();
        return role.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteById(String role){
        roleRepository.deleteById(role);
    }
}
