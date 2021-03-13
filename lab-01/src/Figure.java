import java.awt.geom.Point2D;
import java.util.Scanner;

public abstract class Figure implements Comparable<Figure>{
    protected Point2D[] points;

    public Figure(){}

    public void getPoints(int base, Scanner s){
        points = new Point2D[base];
        for (int i = 0; i < base; i++) {
            System.out.println("Enter x and y of point " + (i+1) + " as [x y]: ");
            points[i] = new Point2D.Double(s.nextDouble(), s.nextDouble());
        }
    }

    abstract public double getArea();
    abstract public double getLength();

    public int compareTo(Figure target){
        return Double.compare(getArea(), target.getArea());
    }
}