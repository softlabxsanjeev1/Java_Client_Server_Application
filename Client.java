import java.lang.*;
import java.net.*;
import java.io.*;


public class Client {

    Socket socket;
    BufferedReader br; // reading
    PrintWriter out; // writting

    // constructor
    public Client(){
        try{
            System.out.println("Sending request to server.......");
            socket = new Socket("192.168.0.198",7777);
            System.out.println("connection done.");

            
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWritting();

        }catch(Exception e){
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
                        System.out.println("Server terminated the chat");

                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);               
            }}catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection closed...........");
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

            }
            System.out.println("Connection is closed.............");
        }catch(Exception e){
                // e.printStackTrace();
               
            }
        };

        // way to create thread
        new Thread(r2).start();
    }




    public static void main(String[] args) {
        System.out.println("This is client......");
        new Client();
    }
    
}
