package com.example.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.PermissionRequest;
import com.example.backend.dto.response.PermissionReponse;
import com.example.backend.entity.Permission;
import com.example.backend.mapper.PermissionMapper;
import com.example.backend.repository.PermissionRepository;

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
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionReponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissiResponse(permission);
    }
    public List<PermissionReponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissiResponse).toList();
    }
    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
