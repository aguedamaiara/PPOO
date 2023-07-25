package com.ifpe.padroes.repository;

import com.ifpe.padroes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByLogin(String login);
}
