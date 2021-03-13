import java.util.Scanner;

public class RightTriangle extends Triangle{
    public RightTriangle(Scanner s){
        super();
    }

    @Override
    public String toString() {
        double[] medians = getMedians();
        double[] heights = getHeights();
        double[] bisections = getBisections();
        return "Right Triangle:\nEdge length: " + edges[0] +
                "\nArea: " + getArea() + "\nPerimeter: " + getLength() + "\nMedian lengths: " +
                medians[0] + "\nHeight lengths: " + heights[0] + "\nBisections lengths: " + bisections[0] +
                "\nCircumcircle radius: " + getCircumcircleRadius() + "\nIncircle radius: " + getIncircleRadius();
    }
}
