import java.util.Scanner;

public class Rhombus extends Figure{
    protected double edge;
    private double d1, d2;

    public Rhombus() {}

    public Rhombus(Scanner s){
        getPoints(4, s);
        edge = points[0].distance(points[1]);
        d1 = points[0].distance(points[2]);
        d2 = points[1].distance(points[3]);
    }

    public Rhombus(double d1, double d2){
        edge = Math.sqrt(Math.pow(d1, 2)+Math.pow(d2, 2))/2;
        this.d1 = d1;
        this.d2 = d2;
    }

    public double getEdge(){
        return edge;
    }

    public double getLength(){
        return edge*4;
    }

    public double getArea(){
        return d1*d2/2;
    }

    public double getHeight(){
        return d1*d2/2/edge;
    }

    @Override
    public String toString(){
        return "Rhombus:\nDiagonals: "+ d1 + " " + d2+"\nEdge length: "+
                edge+"\nPerimeter: "+getLength()+"\nArea: "+getArea()+"\nHeight: "+ getHeight();
    }
}
