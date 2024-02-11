package com.application.SecureCapita.repositories.impl;

import com.application.SecureCapita.enumeration.RoleType;
import com.application.SecureCapita.exceptions.ApiException;
import com.application.SecureCapita.models.Role;
import com.application.SecureCapita.repositories.RoleRepository;
import com.application.SecureCapita.mappers.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.application.SecureCapita.queries.RoleQuery.*;
import java.util.Collection;
import java.util.Optional;

import static com.application.SecureCapita.enumeration.RoleType.ROLE_USER;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            System.out.println("===================================== " + RoleType.valueOf(roleName.toUpperCase()));
            RoleType roleType = RoleType.valueOf(roleName.toUpperCase());
            Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleType), new RoleRowMapper());
            jdbcTemplate.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role).getRoleId()));
            log.info("Role added successfully");
        }catch (IllegalArgumentException e) {
            throw new ApiException("Invalid role name: " + roleName);
        }  catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + roleName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An unexpected error occurred.");
        }
    }

    @Override
    public Role getRoleByUserId(Long userID) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }

    @Override
    public Optional<Role> findByName(RoleType roleType) {
        try {
            Role role = jdbcTemplate.queryForObject(
                    SELECT_ROLE_BY_NAME_QUERY,
                    new MapSqlParameterSource("name", roleType.name()),
                    new BeanPropertyRowMapper<>(Role.class)
            );
            return Optional.ofNullable(role);
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An unexpected error occurred.");
        }
    }
}

