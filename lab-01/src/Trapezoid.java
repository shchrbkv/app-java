import java.util.Scanner;

public class Trapezoid extends Figure{
    protected double[] edges = new double[4];

    public Trapezoid(Scanner s){
        System.out.println("Start with the larger base points!");
        getPoints(4, s);
        for (int i = 0; i < 4; i++) {
            edges[i] = points[i].distance(points[(i+1)%4]);
        }
    }

    public double[] getEdges(){
        return edges;
    }

    public double getLength(){
        return edges[0]+edges[1]+edges[2]+edges[3];
    }

    public double getArea(){
        return getHeight()*(edges[0]+edges[2])/2;
    }

    public double getHeight(){
        double sina = Math.sqrt(1 - Math.pow(((points[0].getX()-points[1].getX())*(points[2].getX()-points[1].getX())+
                (points[0].getY()-points[1].getY())*(points[2].getY()-points[1].getY()))/edges[0]/edges[1], 2));
        return edges[1]*sina;
    }

    @Override
    public String toString(){
        return "Trapezoid:\nCoordinates: ("+
                points[0].getX()+", "+points[0].getY()+"), ("+
                points[1].getX()+", "+points[1].getY()+"), ("+
                points[2].getX()+", "+points[2].getY()+"), ("+
                points[3].getX()+", "+points[3].getY()+")\nBase lengths: "+ edges[0] + " " + edges[2]+
                "\nSide lengths:" + edges[1] + " " + edges[3]+
                "\nPerimeter: "+getLength()+"\nArea: "+getArea()+"\nHeight: "+ getHeight();
    }
}
