package Client;

import Commons.SquaresRoots;
import Server.SquaresRootsEngine;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args){
        try{
            //Получение удалённого объекта из регистра
            Registry registry = LocateRegistry.getRegistry(1234);
            SquaresRoots squaresRoots = (SquaresRoots) registry.lookup("SquaresRoots");

            // Тестовые данные
            List<Double> numbers = Arrays.asList(2.0, 4.0, 16.0);

            // Удалённый вызов getSquares
            List<Double> squares = squaresRoots.getSquares(numbers);
            System.out.println("Server for squares: "+squares);

            // Удалённый вызов getRoots
            List<Double> roots = squaresRoots.getRoots(numbers);
            System.out.println("Server for roots: "+roots);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
