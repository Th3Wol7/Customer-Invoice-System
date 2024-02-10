package com.application.SecureCapita.repositories.impl;

import com.application.SecureCapita.exceptions.ApiException;
import com.application.SecureCapita.models.Role;
import com.application.SecureCapita.models.User;
import com.application.SecureCapita.repositories.RoleRepository;
import com.application.SecureCapita.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.application.SecureCapita.enumeration.RoleType.ROLE_USER;
import static com.application.SecureCapita.enumeration.VerificationType.ACCOUNT;
import static com.application.SecureCapita.queries.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRepository<Role> roleRepository;

    private final BCryptPasswordEncoder encoder;
    @Override
    public User create(User user) {
        //check the email is unique
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0) {
            throw new ApiException("Email already in use. Please us e a different email and try again.");
        }
        //save new user
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbcTemplate.update(INSERT_USER_QUERY, parameters, holder);
            user.setUserId(requireNonNull(holder.getKey()).longValue());

            //add role to user
            roleRepository.addRoleToUser(user.getUserId(), ROLE_USER.name());

            //send verification url
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());

            //save url in verification table
            jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_QUERY,
                    Map.of("userId", user.getUserId(), "url", verificationUrl));

            //send email to uer with verification url
            //emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            user.setUsingMFA(false);

            //return newly created user
            return user;

            //if any errors, throw exception with proper message
        }catch(Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An unexpected error occurred.");
        }
    }


    @Override
    public Collection list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
        try {
            User user = jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL_QUERY, params, new BeanPropertyRowMapper<>(User.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private int getEmailCount(String email) {
        return  jdbcTemplate.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/"+ type+"/"+ key).toUriString();
    }

}
