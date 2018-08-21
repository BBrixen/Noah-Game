package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    static Socket socket;
    static ObjectInputStream in;
    static ObjectOutputStream out;

    public static void main(String[] args) throws Exception{
        System.out.println("Connecting...");
        socket = new Socket("localhost", 7777);
        System.out.println("Connected");

        in = new ObjectInputStream(socket.getInputStream());

        continuallySendData();
        continuallyReceiveData();
    }

    public static void continuallySendData() {
        //threaded so it runs at the same time as recieve data
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    try {
                        Object Object = null;  //calculate or create data to send

                        sendData(Object);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread.start();
    }

    public static void sendData(Object data) throws IOException{
        //this creates a new output stream for each new time data is being sent
        out = new ObjectOutputStream(socket.getOutputStream());

        //this sends the data
        out.writeObject(data);
        out.flush();
    }

    public static Object receiveData() throws IOException, ClassNotFoundException{
        return in.readObject();
    }

    public static void continuallyReceiveData() {

        //thread the receiving data code so that it can run at the same time as the send data
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    try {
                        Object data = (Object) receiveData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread.start();
    }

}