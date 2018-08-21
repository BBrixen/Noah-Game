package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ServerSocket serverSocket;
    static ArrayList<Socket> sockets = new ArrayList<>();
    static ArrayList<ObjectOutputStream> objectOutputStreams = new ArrayList<>();

    public static void main(String[] args) throws Exception{

        System.out.println("Starting Server");
        serverSocket = new ServerSocket(7777);
        System.out.println("Server Started");

        while (true) {
            clientJoins();
        }
    }

    public static void clientJoins() throws IOException{
        Socket socket;
        ObjectOutputStream out;


        socket = serverSocket.accept();
        System.out.println("Client Connected, address: " + socket.getInetAddress());
        sockets.add(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStreams.add(out);

        //recieving client data
        //new thread for each client so that all of them can send data at the same time
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //getting the data from the client
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        Object data = (Object) in.readObject();
                    } catch (IOException e) {
                        System.out.println("Client disconnected");
                        System.exit(12);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Client disconnected");
                        System.exit(12);
                    }
                }
            }
        });
        thread.start();
    }
}
