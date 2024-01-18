package com.application.SecureCapita.queries;

public class RoleQuery {
    public final String INSERT_ROLE_TO_USER_QUERY = "INSERT INTO UserRoles (user_id, role_id) " +
            "VALUES (:userId, :roleId)";
    public final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM Roles WHERE name = :name";


}
