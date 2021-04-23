package Commons;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface SquaresRoots extends Remote {
    List<Double> getSquares(List<Double> sequence) throws RemoteException;
    List<Double> getRoots(List<Double> sequence) throws RemoteException;
}
