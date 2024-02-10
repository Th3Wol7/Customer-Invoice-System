package com.application.SecureCapita.repositories;

import com.application.SecureCapita.models.User;

import java.util.Collection;
import java.util.Optional;

/*
* @author Tyrien Gilpin
* @version 1.0
* @since 16/01/2024 dd/mm/yyyy
* */
public interface UserRepository <T extends User>{
    //Basic Crud Operations
    T create(T data);

    Collection<T> list(int page, int pageSize);

    T get(Long id);

    T update(T data);

    boolean delete(Long id);

    //Complex operations
    Optional<T>findByEmail(String email);
}
