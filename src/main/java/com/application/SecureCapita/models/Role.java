package com.application.SecureCapita.models;

import com.application.SecureCapita.enumeration.RoleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Role {
    @Id
    private Long roleId;
    @NotEmpty(message = "Name cannot be empty")
    @Enumerated(EnumType.STRING)
    private RoleType name;
    @NotEmpty(message = "Permissions cannot be empty")
    private String permission;
}
