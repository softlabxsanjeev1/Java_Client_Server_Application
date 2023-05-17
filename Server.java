import java.lang.*;
import java.net.*;
import java.io.*;

class Server {

    // variables
    ServerSocket server;
    Socket socket;
    BufferedReader br; // reading
    PrintWriter out; // writting

    // constructor of server

    public Server()
    {
        try{
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting...........");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWritting();

        }catch(Exception e){
            // 1000: handle exception
            e.printStackTrace();
        }       
    }

    // reading functions

    public void startReading() {
        // use thread jo data read karka deta rhaga
        Runnable r1 = () -> {
            System.out.println("Reader started...");

            try{
            while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");

                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);
            }}catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection closed.......");
            }



        };

        // way to start thread
        new Thread(r1).start();
    }

    // writting function

    public void startWritting() {
        // thread - data user lege and then send karega client tak
        Runnable r2 = () -> {
            System.out.println("Writter started .......");

            try{
            while (!socket.isClosed()) {
               
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

            }}catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection closed........");
            }
            System.out.println("Connection closed");
        };

        // way to create thread
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("This is servre...going to server");
        new Server(); // object constructor execute
    }
}