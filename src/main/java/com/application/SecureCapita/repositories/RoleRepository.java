package com.application.SecureCapita.repositories;

import com.application.SecureCapita.enumeration.RoleType;
import com.application.SecureCapita.models.Role;

import java.util.Collection;
import java.util.Optional;

/*
 * @author Tyrien Gilpin
 * @version 1.0
 * @since 16/01/2024 dd/mm/yyyy
 * */

public interface RoleRepository <T extends Role>{
    T create(T data);

    Collection<T> list(int page, int pageSize);

    T get(Long id);

    T update(T data);

    boolean delete(Long id);

    //Complex operations
    void addRoleToUser(Long userId, String roleName);

    Role getRoleByUserId(Long userID);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
    Optional<Object> findByName(RoleType roleType);
}
