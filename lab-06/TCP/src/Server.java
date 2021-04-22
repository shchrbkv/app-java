import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static int connectionCount = 0;

    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(32123);
            while (true)
                new ConnectionHandler(serverSocket.accept()).start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try { serverSocket.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
    private static class ConnectionHandler extends Thread {
        private final Socket clientSocket;

        public ConnectionHandler(Socket socket) {
            connectionCount++;
            this.clientSocket = socket;
        }

        public void run() {
            try {
                System.out.println("Connection "+connectionCount+" established.");

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("Client: " + clientMessage);
                    if ("exit".equals(clientMessage)) {
                        out.println("Connection closed.");
                        System.out.println("Connection "+connectionCount+" closed.");
                        break;
                    }
                    out.println(clientMessage);
                }

                in.close();
                out.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}