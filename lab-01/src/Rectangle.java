import java.util.Scanner;

public class Rectangle extends Parallelogram{
    public Rectangle(double a, double b){
        edges[0] = a;
        edges[1] = b;
    }

    @Override
    public double getArea(){
        return edges[0]*edges[1];
    }

    @Override
    public double[] getHeights(){
        return edges;
    }

    public String toString() {
        double[] heights = getHeights();
        return "Rectangle:\nSides: " + edges[0] + " " + edges[1] + "\nPerimeter: " + getLength() +
                "\nArea: " + getArea() + "\nHeight: " + heights[0] + " " + heights[1];
    }
}
