package com.mycompany.mvpinclusaoproduto.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteConnection {
    public static void main(String[] args) {
        var url = "jdbc:sqlite:src/main/java/com/mycompany/mvpinclusaoproduto/db/produtos.db";

        String sql = """
            CREATE TABLE IF NOT EXISTS produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome VARCHAR(999) NOT NULL,
                precoCusto DECIMAL(5,2),
                percentualLucro DECIMAL(5,2)
            );
        """;

        try {
            Connection connection = DriverManager.getConnection(url);
            connection.createStatement().execute(sql);
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }

        

        
    }
}
