package com.example.backend.service.page_user;

import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.page_user.PageUserRequest;
import com.example.backend.dto.request.page_user.PageUserUpdateRequest;
import com.example.backend.dto.response.page_user.PageUserResponse;
import com.example.backend.entity.PageUser;
import com.example.backend.entity.User;
import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.mapper.page_user.PageUserMapper;
import com.example.backend.repository.PageUserRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserAuthService;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class PageUserService {
    UserAuthService userAuthService;
    PageUserRepository pageUserRepository;
    UserRepository userRepository;
    PageUserMapper pageUserMapper;

    public PageUserResponse createPage (PageUserRequest pageUserRequest, String token) throws ParseException{
        String userId =  userAuthService.getStringId(token);
        User user = userRepository.findById(userId).orElseThrow(()-> new AppExeption(ErrorCode.USERID_NOT_EXITTED));
        
        PageUser pageUser = PageUser.builder()
        .namePage(pageUserRequest.getNamePage())
        .titlePage(pageUserRequest.getTitlePage())
        .createBy(user)
        .updateAt(LocalDate.now())
        .createAt(LocalDate.now())
        .build();
        
        return pageUserMapper.toPageUser(pageUserRepository.save(pageUser));
    }

    public PageUserResponse updatePage (PageUserUpdateRequest pageUserUpdateRequest, Long pageId, String token) throws ParseException{
        String userId =  userAuthService.getStringId(token);
        userRepository.findById(userId).orElseThrow(()-> new AppExeption(ErrorCode.USERID_NOT_EXITTED));

        PageUser page = pageUserRepository.findById(pageId).orElseThrow(()->new AppExeption(ErrorCode.valueOf("không tìm thấy id page")));
        
        pageUserMapper.updatePage(page, pageUserUpdateRequest);

        page.setUpdateAt(LocalDate.now());
        log.info(page.getNamePage()+page.getTitlePage()+page.getId());
        return pageUserMapper.toPageUser(pageUserRepository.save(page));
    }
}
