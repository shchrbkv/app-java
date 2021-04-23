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
                // При каждом входящем запросе передача сокета в обработчик
                new ClientHandler(serverSocket.accept()).start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try { serverSocket.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
    // Обработчик подключившихся клиентов в новом потоке
    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            connectionCount++;
            this.clientSocket = socket;
        }

        public void run() {
            try {
                System.out.println("Connection "+connectionCount+" established.");

                // Потоки вывода и ввода для сокета
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Обработка сообщений клиента
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Client: " + message);
                    // Закрытие соединения при ключевом слове stop
                    if (message.equals("stop")) {
                        out.println("Connection closed.");
                        System.out.println("Connection "+connectionCount+" closed.");
                        break;
                    }
                    out.println(message);
                }

                // Закрытие потоков ввода/вывода для сокета
                in.close();
                out.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}