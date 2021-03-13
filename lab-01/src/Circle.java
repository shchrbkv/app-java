import java.awt.geom.Point2D;
import java.util.Scanner;

public class Circle extends Figure{
    private final double radius;

    public Circle(Scanner s){
        getPoints(2, s);
        radius = points[0].distance(points[1]);
    }

    public Circle(double radius){
        this.radius = radius;
    }

    public double getArea(){
        return Math.PI*Math.pow(radius, 2);
    }

    public double getLength(){
        return 2*Math.PI*radius;
    }

    public double getDiameter(){
        return radius*2;
    }

    @Override
    public String toString(){
        return "Circle:\nCircumference: "+ getLength()+
                "\nPerimeter: "+getLength()+
                "\nArea: "+getArea()+
                "\nDiameter: "+ getDiameter();
    }
}
