import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static String host = "127.0.0.1";
    private static int port = 32123;

    public static void main(String[] args) {
        try {
            clientSocket = new Socket(host, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Client: Time is "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            out.println("Time is "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            System.out.println("Server: "+in.readLine());
            out.println("exit");
            System.out.println("Server: "+in.readLine());
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}