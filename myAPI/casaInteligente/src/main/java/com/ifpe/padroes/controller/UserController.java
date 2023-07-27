package com.ifpe.padroes.controller;

import com.ifpe.padroes.model.User;
import com.ifpe.padroes.repository.UserRepository;
import com.ifpe.padroes.factory.UserFactory;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequestMapping("/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final List<User> userList = new ArrayList<>();
    private final UserFactory userFactory;

    @Autowired
    public UserController(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    // Endpoint GET para buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Endpoint POST para criar um novo usuário
    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam String login, @RequestParam String perfil, @RequestParam String senha) {
        //hash(senha)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedSenha = passwordEncoder.encode(senha);

        User newUser = userFactory.createUser(login, perfil, hashedSenha);
        User createdUser = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso:\n" + createdUser.toString());
    }

    //endpoint para atualizar
    @PutMapping("/{login}")
    public ResponseEntity<String> updateUser(@PathVariable String login,  @RequestParam String novoLogin, @RequestParam String perfil, @RequestParam String senha) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPerfil(perfil);
            user.setSenha(senha);
            user.setLogin(novoLogin);
            userRepository.save(user); // Salvando as alterações no banco de dados
            return ResponseEntity.ok("Usuário atualizado com sucesso:\n" + user.toString());
        }

        return ResponseEntity.notFound().build();
    }

    // Endpoint DELETE para deletar um usuário existente pelo Login
    @DeleteMapping("/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return ResponseEntity.ok("Usuário deletado com sucesso:\n" + user.toString());
        }

        return ResponseEntity.notFound().build();
    }

}

