package Server;

import Commons.SquaresRoots;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SquaresRootsEngine implements SquaresRoots {
    public List<Double> getSquares(List<Double> sequence) throws RemoteException {
        System.out.println("Client for squares: "+sequence);
        return sequence.stream().map(s -> s*s).collect(Collectors.toList());
    }
    public List<Double> getRoots(List<Double> sequence) throws RemoteException {
        System.out.println("Client for roots: "+sequence);
        return sequence.stream().map(Math::sqrt).collect(Collectors.toList());
    }
}
