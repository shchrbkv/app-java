import java.util.Scanner;

public class Parallelogram extends Figure{
    protected double[] edges = new double[2];

    public Parallelogram(){}

    public Parallelogram(Scanner s){
        getPoints(4, s);
        edges[0] = points[0].distance(points[1]);
        edges[1] = points[1].distance(points[2]);
    }

    public double[] getEdges(){
        return edges;
    }

    public double getLength(){
        return edges[0]*2+edges[1]*2;
    }

    public double getArea(){
        double sina = Math.sqrt(1 - Math.pow(((points[0].getX()-points[1].getX())*(points[2].getX()-points[1].getX())+
                (points[0].getY()-points[1].getY())*(points[2].getY()-points[1].getY()))/edges[0]/edges[1], 2));
        return edges[0]*edges[1]*sina;
    }

    public double[] getHeights(){
        double[] heights = new double[2];
        heights[0] = getArea()/edges[0];
        heights[1] = getArea()/edges[1];
        return heights;
    }

    @Override
    public String toString(){
        double[] heights = getHeights();
        return "Parallelogram:\nCoordinates: ("+
                points[0].getX()+", "+points[0].getY()+"), ("+
                points[1].getX()+", "+points[1].getY()+"), ("+
                points[2].getX()+", "+points[2].getY()+"), ("+
                points[3].getX()+", "+points[3].getY()+")\nEdge lengths: "+ edges[0] + " " + edges[1]+
                "\nPerimeter: "+getLength()+"\nArea: "+getArea()+"\nHeight lengths: "+ heights[0] + " " +heights[1];
    }
}
