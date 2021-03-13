import java.io.FileWriter;
import java.util.*;
import java.io.*;

public class Program {
    public static void exportToFile(Figure f, String fileName){
        try(FileWriter writer = new FileWriter(fileName, false)){
            writer.write(f.toString());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        Circle circle = new Circle(5);
        System.out.println(circle);

        Triangle triangle =  new Triangle(s); //Input through console
        System.out.println(triangle);

        Rhombus rhombus = new Rhombus(3, 4);
        System.out.println(rhombus);

//        Parallelogram paral = new Parallelogram(s);
//        System.out.println(paral);

//        Trapezoid trapez = new Trapezoid(s);
//        System.out.println(trapez);

        Rectangle rect = new Rectangle(4, 8);
        System.out.println(rect);

        Square square = new Square(5);
        System.out.println(square);

        EquilateralTriangle eqtr = new EquilateralTriangle(5);
        System.out.println(eqtr);

        //ComparaBLE implementation for TreeSet
        TreeSet<Figure> availableFiguresT = new TreeSet<Figure>();
        availableFiguresT.add(circle);
        availableFiguresT.add(rhombus);
        availableFiguresT.add(rect);
        availableFiguresT.add(square);
        availableFiguresT.add(eqtr);

        System.out.println("\n# TreeSet comparison with Comparable by area:");
        for (Figure f: availableFiguresT) {
            System.out.println(f.getClass().getSimpleName()+" with area of "+f.getArea());
        }

        //ComparaTOR implementation for ArrayList
        FigureAreaComparator facomp = new FigureAreaComparator();
        List<Figure> availableFiguresA = new ArrayList<Figure>();
        availableFiguresA.add(circle);
        availableFiguresA.add(rhombus);
        availableFiguresA.add(rect);
        availableFiguresA.add(square);
        availableFiguresA.add(eqtr);

        availableFiguresA.sort(facomp);

        System.out.println("\n# ArrayList comparison with Comparable by area (inverted):");
        for (Figure f: availableFiguresA) {
            System.out.println(f.getClass().getSimpleName()+" with area of "+f.getArea());
        }

        exportToFile(triangle, "triangle_sample.txt");
    }
}
