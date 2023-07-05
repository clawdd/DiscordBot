package main;


import net.dv8tion.jda.api.OnlineStatus;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainBot {

    public static Setup INSTANCE;

    public static void main(String[] args) {

        INSTANCE = new Setup();
        shutdown();
    }

    public static void shutdown(){
        new Thread(() -> {
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                while ((line = reader.readLine()) != null){

                    if (line.equalsIgnoreCase("exit")) {
                        if (INSTANCE != null) {
                            INSTANCE.setOnlineStatus(OnlineStatus.OFFLINE);

                            INSTANCE.getConnection().close();
                            System.out.println("SQL connection closed");

                            INSTANCE.shutDown();
                            System.out.println("Bot shutdown");
                        }
                    }

                    if (line.equalsIgnoreCase("reconnect")) {
                        if (INSTANCE != null) {
                            INSTANCE.setOnlineStatus(OnlineStatus.ONLINE);

                            INSTANCE.establishSQLConnection();
                            System.out.println("Bot reconnected to SQL Data base");

                        }
                    }
                }
            }catch (SQLException | IOException e){
                e.printStackTrace();
            }
        }).start();
    }
}
