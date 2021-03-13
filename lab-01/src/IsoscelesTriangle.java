import java.util.Scanner;

public class IsoscelesTriangle extends Triangle{
    public IsoscelesTriangle(){}
    public IsoscelesTriangle(Scanner s){
        super();
    }
    @Override
    public String toString(){
        double[] medians = getMedians();
        double[] heights = getHeights();
        double[] bisections = getBisections();
        return "Isosceles Triangle:\nCoordinates: ("+
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
