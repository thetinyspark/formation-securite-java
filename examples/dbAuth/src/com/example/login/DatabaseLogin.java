package com.example.login;
import java.sql.*;
import com.example.user.MyUser;

public class DatabaseLogin {

    public MyUser login( String username, String password) {
        // créer un nouvel utilisateur par défaut
        MyUser user = new MyUser("", "");
        user.setIsConnected(false);

        try {
            // Enregistrer le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données MySQL
            String jdbcUrl = "jdbc:mysql://localhost:3306/jaas_example";
            String dbUsername = "root";  // Change selon ton utilisateur DB
            String dbPassword = "root";  // Change selon ton mot de passe DB
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Vérifier si les informations d'identification sont correctes
            String sql = "SELECT password, username FROM users WHERE username=? AND password=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    user.setName(rs.getString("username"));
                    user.setIsConnected(true);
                } else {
                    System.out.println("Invalid credentials");
                }
            }

            // Récupérer les rôles de l'utilisateur
            // ce code est naturellement protégé des injections SQL grâce aux requêtes préparées.
            sql = "SELECT role_name FROM roles WHERE username=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String role = rs.getString("role_name");
                    user.setRole(role);
                }
            }

            // libère la connexion à la base de données
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL non trouvé !");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error during login process: " + e.getMessage());
        }
        return user;
    }
}
