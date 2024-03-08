package com.moura1001.webForum.model.infra;

import java.sql.Connection;
import java.sql.DriverManager;

public final class ConfigH2Database {

    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/mydb";
    public static final String USER = "admin";
    public static final String PASSWORD = "";

    private static boolean isDatabaseSetuped = false;

    private ConfigH2Database() {
    }

    public static void setupDatabase(String sqlSetupFile) {
        if (!isDatabaseSetuped) {

            isDatabaseSetuped = true;

            try {
                Class.forName(JDBC_DRIVER);

                System.out.println("Connecting to database...");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                System.out.println("Setuping database from SQL file...");

                SqlScriptBatchExecutor.executeBatchedSQL(sqlSetupFile, conn, 10);

                System.out.println("Database setup finished.");

                conn.close();
            } catch (Exception e) {
                throw new RuntimeException("Não foi possível configurar o banco de dados", e);
            }
        }
    }

    public static void setupDatabase(String sqlSetupFile, boolean forceSetup) {
        if (forceSetup) {
            try {
                Class.forName(JDBC_DRIVER);

                System.out.println("Connecting to database...");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                System.out.println("Setuping database from SQL file...");

                SqlScriptBatchExecutor.executeBatchedSQL(sqlSetupFile, conn, 10);

                System.out.println("Database setup finished.");

                conn.close();
            } catch (Exception e) {
                throw new RuntimeException("Não foi possível configurar o banco de dados", e);
            }
            return;
        }

        ConfigH2Database.setupDatabase(sqlSetupFile);
    }
}
