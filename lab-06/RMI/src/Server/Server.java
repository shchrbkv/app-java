package Server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try{
            // Создание удалённого объекта
            SquaresRootsEngine squaresRootsEngine = new SquaresRootsEngine();

            // Зашлушка
            Remote squaresRootsStub = UnicastRemoteObject.exportObject(squaresRootsEngine, 1234);

            // Создание регистра и регистрация заглушки
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.bind("SquaresRoots", squaresRootsStub);

            System.out.println("Remote engine started");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}