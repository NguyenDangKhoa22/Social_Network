package com.example.backend.controller.page_user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.page_user.PageUserRequest;
import com.example.backend.dto.request.page_user.PageUserUpdateRequest;
import com.example.backend.dto.response.page_user.PageUserResponse;
import com.example.backend.service.page_user.PageUserService;
import com.nimbusds.jose.JOSEException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/page_user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PageUserController {
    PageUserService pageUserService;
    @PostMapping("/create")
    public ApiReponse<PageUserResponse> createPage(@RequestBody PageUserRequest request,HttpServletRequest httpServletRequest)
    throws ParseException, JOSEException  
    {
        String token =  extractToken(httpServletRequest);
        return ApiReponse.<PageUserResponse>builder().result(pageUserService.createPage(request, token)).build();
    }
    @PutMapping("/update/{id}")
    public ApiReponse<PageUserResponse> updatePage(@RequestBody PageUserUpdateRequest request,@PathVariable long id, HttpServletRequest httpServletRequest) 
    throws ParseException, JOSEException
    {
        String token = extractToken(httpServletRequest);

        return ApiReponse.<PageUserResponse>builder().result(pageUserService.updatePage(request,id,token)).build();
    }

    private String extractToken(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer != null && bearer.startsWith(bearer)){
            return bearer.substring(7);
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }
    
}
