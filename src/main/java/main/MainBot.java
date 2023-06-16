package main;


import net.dv8tion.jda.api.OnlineStatus;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                            INSTANCE.shutDown();
                            System.out.println("Bot shutdown");
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }
}
