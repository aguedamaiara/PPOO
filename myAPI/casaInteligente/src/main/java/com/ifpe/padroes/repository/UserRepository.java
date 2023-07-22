package com.ifpe.padroes.repository;

import com.ifpe.padroes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
