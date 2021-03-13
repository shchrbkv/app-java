import java.util.Scanner;

public class Square extends Rhombus{
    public Square(Scanner s){
        super(s);
    }
    public Square(double a){
        edge = a;
    }

    @Override
    public double getArea(){
        return edge*edge;
    }

    @Override
    public double getHeight(){
        return edge;
    }

    @Override
    public String toString(){
        return "Square:\nEdge length: "+ edge+"\nPerimeter: "+getLength()+"\nArea: "+getArea()+"\nHeight: "+ getHeight();
    }
}
