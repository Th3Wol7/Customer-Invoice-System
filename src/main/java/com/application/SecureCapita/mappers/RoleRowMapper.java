package com.application.SecureCapita.mappers;


import com.application.SecureCapita.enumeration.RoleType;
import com.application.SecureCapita.models.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * @author Tyrien Gilpin
 * @version 1.0
 * @since 16/01/2024 dd/mm/yyyy
 * */
@Component
public class RoleRowMapper implements RowMapper<Role> {


    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Role.builder()
                .roleId(resultSet.getLong("role_id"))
                .name(RoleType.valueOf(resultSet.getString("name")))
                .permission(resultSet.getString("permission"))
                .build();
    }
}
