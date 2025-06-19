package com.example.backend.mapper.page_user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.backend.dto.request.page_user.PageUserUpdateRequest;
import com.example.backend.dto.response.page_user.PageUserResponse;
import com.example.backend.entity.PageUser;

@Mapper(componentModel = "spring")
public interface PageUserMapper {

    PageUser toPageUserResponse(PageUserResponse pageUserResponse);

    PageUserResponse toPageUser(PageUser reponse);

    void updatePage(@MappingTarget PageUser page, PageUserUpdateRequest pageUpdate);
    
}
