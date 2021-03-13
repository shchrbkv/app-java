import java.util.Scanner;

public class EquilateralTriangle extends IsoscelesTriangle{
    public EquilateralTriangle(double side){
        edges[0] = edges[1] = edges[2] = side;
    }
    public EquilateralTriangle(Scanner s){
        getPoints(2, s);
        edges[0] = edges[1] = edges[2] = points[0].distance(points[1]);
    }

    @Override
    public double getArea(){
        return Math.sqrt(3)*edges[0]*edges[0]/4;
    }

    @Override
    public double[] getMedians(){
        double[] medians = new double[3];
        for (int i = 0; i < 3; i++) {
            medians[i] = Math.sqrt(edges[0]*edges[0]*3)/2;
        }
        return medians;
    }

    @Override
    public double[] getHeights(){
        return getMedians();
    }

    @Override
    public double[] getBisections(){
        return getMedians();
    }

    @Override
    public String toString() {
        double[] medians = getMedians();
        double[] heights = getHeights();
        double[] bisections = getBisections();
        return "Equilateral Triangle:\nEdge length: " + edges[0] +
                "\nArea: " + getArea() + "\nPerimeter: " + getLength() + "\nMedian lengths: " +
                medians[0] + "\nHeight lengths: " + heights[0] + "\nBisections lengths: " + bisections[0] +
                "\nCircumcircle radius: " + getCircumcircleRadius() + "\nIncircle radius: " + getIncircleRadius();
    }
}
