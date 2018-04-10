package Server;

import GameState.GameState;
import character.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Server server = null;
    private boolean connected;
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static InputStreamReader reader;
    private static BufferedReader bufferedReader;
    private static DataOutputStream dout;
    private static String message;
    private static String toSend = "";
    private Player player;

    private Server(){
    }

    public static Server getServer(){
        if (server == null){
            server = new Server();
        }
        return server;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setToSend(String info){
        toSend = info;
    }

    public boolean getConnected(){
        return connected;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(8000);

            while (true) {
                socket = serverSocket.accept();
                if (!connected)
                    connected = true;
                dout = new DataOutputStream(socket.getOutputStream());
                dout.writeUTF(toSend);

                reader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(reader);
                message = bufferedReader.readLine();

                if (message.equals("Right")) {
                    player.setLeft(false);
                    player.setRight(true);
                }
                if (message.equals("Left")) {
                    player.setRight(false);
                    player.setLeft(true);
                }
                if (message.equals("  ")) {
                    player.setLeft(false);
                    player.setRight(false);
                }
                if (message.equals("Fire"))
                    player.setFire(true);
            }
        } catch (IOException e) {
            System.out.print("");
        }
    }
}