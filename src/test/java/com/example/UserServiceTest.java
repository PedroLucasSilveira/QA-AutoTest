package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void testCreateAndRead() {
        UserService service = new UserService();
        String create = service.createUser(1, "Alice", "alice@example.com");
        assertTrue(create.contains("SUCESSO"));

        String read = service.readUser(1);
        assertTrue(read.contains("ENCONTRADO"));
        assertTrue(read.contains("Alice"));
    }

    @Test
    public void testUpdateAndDelete() {
        UserService service = new UserService();
        service.createUser(2, "Bob", "bob@example.com");
        String update = service.updateUser(2, "Bobby", "bobby@example.com");
        assertTrue(update.contains("SUCESSO"));
        assertTrue(update.contains("Bobby"));

        String del = service.deleteUser(2);
        assertTrue(del.contains("SUCESSO"));
    }

    @Test
    public void testErrors() {
        UserService service = new UserService();
        String read = service.readUser(999);
        assertTrue(read.contains("nao encontrado") || read.contains("ERRO"));

        String update = service.updateUser(999, "X", "x@x.com");
        assertTrue(update.contains("ERRO"));
    }
}
