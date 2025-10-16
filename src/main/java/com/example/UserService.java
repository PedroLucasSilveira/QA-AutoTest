package com.example;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Integer, User> userDatabase = new HashMap<>();

    public String createUser(int id, String name, String email) {
        if (userDatabase.containsKey(id)) {
            return "ERRO: Usuario com ID " + id + " ja existe.";
        }
        userDatabase.put(id, new User(id, name, email));
        return "SUCESSO: Usuario criado -> " + userDatabase.get(id).toString();
    }

    public String readUser(int id) {
        if (userDatabase.containsKey(id)) {
            return "ENCONTRADO: " + userDatabase.get(id).toString();
        }
        return "ERRO: Usuario com ID " + id + " nao encontrado.";
    }

    public String updateUser(int id, String newName, String newEmail) {
        if (!userDatabase.containsKey(id)) {
            return "ERRO: Nao e possivel atualizar. Usuario com ID " + id + " nao encontrado.";
        }
        User user = userDatabase.get(id);
        user.setName(newName);
        user.setEmail(newEmail);
        return "SUCESSO: Usuario atualizado -> " + user.toString();
    }

    public String deleteUser(int id) {
        if (!userDatabase.containsKey(id)) {
            return "ERRO: Nao e possivel deletar. Usuario com ID " + id + " nao encontrado.";
        }
        User removedUser = userDatabase.remove(id);
        return "SUCESSO: Usuario deletado -> " + removedUser.toString();
    }

    public java.util.List<User> listAllUsers() {
        return new java.util.ArrayList<>(userDatabase.values());
    }

    public java.util.Optional<User> findById(int id) {
        return java.util.Optional.ofNullable(userDatabase.get(id));
    }
}
