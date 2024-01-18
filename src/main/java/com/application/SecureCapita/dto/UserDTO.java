package com.application.SecureCapita.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/*
 * @author Tyrien Gilpin
 * @version 1.0
 * @since 16/01/2024 dd/mm/yyyy
 * */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
   private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMFA;
    private LocalDateTime createdAt;
}
