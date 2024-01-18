package com.application.SecureCapita.rowmapper;

import com.application.SecureCapita.models.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * @author Tyrien Gilpin
 * @version 1.0
 * @since 16/01/2024 dd/mm/yyyy
 * */

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Role.builder()
                .roleId(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .permission(resultSet.getString("permisision"))
                .build();
    }
}
