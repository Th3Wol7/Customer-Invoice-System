package com.application.SecureCapita.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Role {
    private Long roleId;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Permissions cannot be empty")
    private String permission;
}
