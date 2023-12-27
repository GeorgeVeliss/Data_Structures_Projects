import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Point;

public class Thiseas {

    private static char[][] maze;
    private static int rows;
    private static int columns;
    private static int E_row;
    private static int E_col;

    public static void main(String[] args) throws FileNotFoundException {

        // Read filepath from command line
        String filepath = args[0];
        for (int i = 1; i < args.length; i++)
            filepath += " " + args[i];

        // Open file, if it exists
        File file;
        try {
            file = new File(filepath);
        } catch (Exception FileNotFoundException) {
            throw new FileNotFoundException();
        }

        Scanner scan = new Scanner(file);

        // Scan first line, if it exists
        String firstLine = "";
        try{
            firstLine = scan.nextLine();
        }
        catch(Exception NoSuchElementException) {
            System.out.println("Input file is empty. Please format the input text file correctly.");
            System.exit(-1);
        }

        // Split firstLine String, and check if there are more or less than the required two elements
        String[] firstLineElems = firstLine.split(" ");
        if (firstLineElems.length < 2) {
            System.out.println("First line not specifying both maze dimensions. Please format the input text file correctly.");
            System.exit(-1);
        }
        if (firstLineElems.length > 2) {
            System.out.println("First line contains more than the specification of maze dimensions. Please format the input text file correctly.");
            System.exit(-1);
        }

        // Assign the first given integer to rows and the given integer to columns
        try {
            rows = Integer.parseInt(firstLineElems[0]);
            columns = Integer.parseInt(firstLineElems[1]);
        }
        catch(Exception NumberFormatException) {
            System.out.println("Non-integers given as dimensions. Please format the input text file correctly.");
            System.exit(-1);
        }

        // Follow a similar procedure for the second line, 'E' coordinates
        String secondLine = "";
        try{
            secondLine = scan.nextLine();
        }
        catch(Exception NoSuchElementException) {
            System.out.println("Input file contains only one line. Please format the input text file correctly.");
            System.exit(-1);
        }

        String[] secondLineElems = secondLine.split(" ");
        if (secondLineElems.length < 2) {
            System.out.println("Second line not specifying both 'E' coordinates. Please format the input text file correctly.");
            System.exit(-1);
        }
        if (secondLineElems.length > 2) {
            System.out.println("Second line contains more than the specification of 'E' coordinates. Please format the input text file correctly.");
            System.exit(-1);
        }

        try {
            E_row = Integer.parseInt(secondLineElems[0]);
            E_col = Integer.parseInt(secondLineElems[1]);
        }
        catch(Exception NumberFormatException) {
            System.out.println("Non-integers given as 'E' coordinates. Please format the input text file correctly.");
            System.exit(-1);
        }

        maze = new char[rows][columns];

        int countE = 0;

        // Scan through given maze, and fill the maze array
        try {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    maze[i][j] = scan.next().charAt(0);
                    // If you meet '0' or '1', continue
                    if (maze[i][j] == '0' || maze[i][j] == '1') continue;
                    // If you meet English 'E', increment countE and continue
                    else if (maze[i][j] == 'E') countE++;
                    // If you meet some other character, exit
                    else {
                        System.out.println("Maze element at position ("+i+", "+j+") not '0', '1' or English 'E'. Please format the input text file correctly.");
                        System.exit(-1);
                    }
                }
            }
        }
        catch(Exception NoSuchElementException) {
            System.out.println("Too few elements for maze dimensions designated. Please format the input text file correctly.");
            System.exit(-1);
        }

        if (scan.hasNext()) {
            System.out.println("Too many elements for maze dimensions designated. Please format the input text file correctly.");
            System.exit(-1);
        }

        if (countE == 0) {
            System.out.println("'E' character not present in the maze. Please format the input text file correctly.");
            System.exit(-1);
        }

        if (countE > 1) {
            System.out.println("Multiple 'E' characters in the maze. Please format the input text file correctly.");
            System.exit(-1);
        }

        if (maze[E_row][E_col] != 'E') {
            System.out.println("'E' character not in designated position of the maze. Please format the input text file correctly.");
            System.exit(-1);
        }

        //Initialize stack of Point objects, and push the Point where the 'E' character was found
        StringStackImpl<Point> mazeStack = new StringStackImpl<>();
        Point curr_point = new Point(E_row, E_col);
        mazeStack.push(curr_point);

        // Loop through maze until exit is found, or stack is empty
        while (!mazeStack.isEmpty()) {

            // Exit found!
            if (atEdge(curr_point.x, curr_point.y) && maze[curr_point.x][curr_point.y] == '0') {
                System.out.println("Exit from the maze found at position (" + curr_point.x + ", " + curr_point.y + ")");
                System.exit(0);
            }

            // Check top point and if it is '0', push current point to the stack, make it '1' and go to the top point
            else if (inBounds(curr_point.x - 1, curr_point.y) && maze[curr_point.x - 1][curr_point.y] == '0') {
                mazeStack.push(curr_point);
                maze[curr_point.x][curr_point.y] = '1';
                curr_point = new Point(curr_point.x - 1, curr_point.y);
            }

            // Check left point and if it is '0', push current point to the stack, make it '1' and go to the left point
            else if (inBounds(curr_point.x, curr_point.y - 1) && maze[curr_point.x][curr_point.y - 1] == '0') {
                mazeStack.push(curr_point);
                maze[curr_point.x][curr_point.y] = '1';
                curr_point = new Point(curr_point.x, curr_point.y - 1);
            }

            // Check bottom point and if it is '0', push current point to the stack, make it '1' and go to the bottom point
            else if (inBounds(curr_point.x + 1, curr_point.y) && maze[curr_point.x + 1][curr_point.y] == '0') {
                mazeStack.push(curr_point);
                maze[curr_point.x][curr_point.y] = '1';
                curr_point = new Point(curr_point.x + 1, curr_point.y);
            }

            // Check right point and if it is '0', push current point to the stack, make it '1' and go to the right point
            else if (inBounds(curr_point.x, curr_point.y + 1) && maze[curr_point.x][curr_point.y + 1] == '0') { // && !mazeStack.peek().equals(new Point(curr_point.x, curr_point.y + 1))) {
                mazeStack.push(curr_point);
                maze[curr_point.x][curr_point.y] = '1';
                curr_point = new Point(curr_point.x, curr_point.y + 1);
            }

            // If no neighbours are '0', make current point '1', and return to previous point
            else {
                maze[curr_point.x][curr_point.y] = '1';
                curr_point = mazeStack.pop();
            }
        }
        System.out.println("No exit from the maze exists.");

    }
    // Determine if a point is within the bounds of the maze
    private static boolean inBounds(int X, int Y) {
        return X >= 0 && X < rows && Y >= 0 && Y < columns;
    }
    // Determine if a point is at the edge of the maze
    private static boolean atEdge(int X, int Y) {
        return X == 0 || X == rows-1 || Y == 0 || Y == columns-1;
    }
}
