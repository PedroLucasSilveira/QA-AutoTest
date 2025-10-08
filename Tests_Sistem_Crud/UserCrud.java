import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

// Classe que representa nosso usuário
class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}

// Classe que gerencia as operações de CRUD
class UserService {
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
    
    public void listAllUsers() {
        if (userDatabase.isEmpty()) {
            System.out.println("INFO: Nenhum usuario cadastrado.");
            return;
        }
        System.out.println("--- Lista de Todos os Usuarios ---");
        for (User user : userDatabase.values()) {
            System.out.println(user.toString());
        }
        System.out.println("---------------------------------");
    }
}

// Classe principal que interage com o usuário via console
public class UserCrud {
    public static void main(String[] args) {
        UserService service = new UserService();
        Scanner scanner = new Scanner(System.in);

        // Loop infinito para manter o programa rodando para os testes
        // O comando '6' (sair) fechará o programa.
        while (true) {
            try {
                // System.out.println("\nEscolha uma operacao:");
                // System.out.println("1: Criar Usuario | 2: Ler Usuario | 3: Atualizar Usuario | 4: Deletar Usuario | 5: Listar Todos | 6: Sair");
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
                        service.listAllUsers();
                        break;
                    case 6: // Sair
                        // System.out.println("Saindo...");
                        return; // Encerra o programa
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