package com.project.Plantes.Medicinales.repository;

import com.project.Plantes.Medicinales.dtos.*;
import com.project.Plantes.Medicinales.entities.User;
import com.project.Plantes.Medicinales.repository.UserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
