package com.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserCrud {
    public static void main(String[] args) {
        UserService service = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: // Criar
                        int id = scanner.nextInt();
                        String name = scanner.next();
                        String email = scanner.next();
                        System.out.println(service.createUser(id, name, email));
                        break;
                    case 2: // Ler
                        int readId = scanner.nextInt();
                        System.out.println(service.readUser(readId));
                        break;
                    case 3: // Atualizar
                        int updateId = scanner.nextInt();
                        String newName = scanner.next();
                        String newEmail = scanner.next();
                        System.out.println(service.updateUser(updateId, newName, newEmail));
                        break;
                    case 4: // Deletar
                        int deleteId = scanner.nextInt();
                        System.out.println(service.deleteUser(deleteId));
                        break;
                    case 5: // Listar
                        System.out.println(service.listAllUsers());
                        break;
                    case 6: // Sair
                        return;
                    default:
                        System.out.println("ERRO: Opcao invalida. Tente novamente.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada invalida. Por favor, insira numeros para IDs e opcoes.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }
    }
}
