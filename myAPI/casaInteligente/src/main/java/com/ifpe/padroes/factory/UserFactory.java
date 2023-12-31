package com.ifpe.padroes.factory;

import com.ifpe.padroes.model.User;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class UserFactory {

    public User createUser(String login, String perfil, String senha) {
        return new User(login, perfil, senha);
    }
}
