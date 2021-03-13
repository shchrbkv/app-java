import java.util.Scanner;

public class Triangle extends Figure{
    protected double[] edges = new double[3];

    public Triangle(){}
    public Triangle(Scanner s){
        getPoints(3, s);
        for (int i = 0; i < 3; i++) {
            edges[i] = points[i].distance(points[(i+1)%3]);
        }
    }

    public double[] getEdges(){
        return edges;
    }

    public double getLength(){
        return edges[0]+edges[1]+edges[2];
    }

    public double getArea(){
        double p = getLength()/2;
        return Math.sqrt(p*(p-edges[0])*(p-edges[1])*(p-edges[2]));
    }

    public double[] getMedians(){
        double[] medians = new double[3];
        for (int i = 0; i < 3; i++) {
            medians[i] = Math.sqrt(2*Math.pow(edges[(i+1)%3], 2)+2*Math.pow(edges[(i+2)%3], 2)-Math.pow(edges[i], 2))/2;
        }
        return medians;
    }

    public double[] getHeights(){
        double[] heights = new double[3];
        for (int i = 0; i < 3; i++) {
            heights[i] = 2*getArea()/edges[i];
        }
        return heights;
    }

    public double[] getBisections(){
        double[] bisections = new double[3];
        double p = getLength()/2;
        for (int i = 0; i < 3; i++) {
            bisections[i] = 2*Math.sqrt(edges[(i+1)%3]*edges[(i+2)%3]*p*(p-edges[i]))/(edges[(i+1)%3]+edges[(i+2)%3]);
        }
        return bisections;
    }

    public double getCircumcircleRadius(){
        return edges[0]*edges[1]*edges[2]/4/getArea();
    }

    public double getIncircleRadius(){
        return 2*getArea()/getLength();
    }

    @Override
    public String toString(){
        double[] medians = getMedians();
        double[] heights = getHeights();
        double[] bisections = getBisections();
        return "Triangle:\nCoordinates: ("+
                points[0].getX()+", "+points[0].getY()+"), ("+
                points[1].getX()+", "+points[1].getY()+"), ("+
                points[2].getX()+", "+points[2].getY()+")\nEdge lengths: "+
                edges[0]+" "+edges[1]+" "+edges[2]+"\nArea: "+getArea()+
                "\nPerimeter: "+getLength()+"\nMedian lengths: "+
                medians[0]+" "+medians[1]+" "+medians[2]+"\nHeight lengths: "+
                heights[0]+" "+heights[1]+" "+heights[2]+"\nBisections lengths: "+
                bisections[0]+" "+bisections[1]+" "+bisections[2]+"\nCircumcircle radius: "+
                getCircumcircleRadius()+"\nIncircle radius: "+getIncircleRadius();
    }
}
