package com.example.backend.dto.response.page_user;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageUserResponse {
    String namePage;
    String titlePage;
    LocalDate createAt;
    LocalDate updateAt;
}
