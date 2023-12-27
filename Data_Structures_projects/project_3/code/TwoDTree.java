import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TwoDTree {

    public static void main(String[] args) {

        // Read input path from command line
        String filepath = args[0];
        for (int i = 1; i < args.length; i++)
            filepath += " " + args[i];
        File input = new File(filepath);


        // Scan first line, if it exists
        Scanner scanFile = null;
        int numPoints = 0;
        try {
            scanFile = new Scanner(input);
            String firstLine = scanFile.nextLine();

            // Split firstLine String, and check if there are more or less than the required two elements
            String[] firstLineElems = firstLine.split(" ");
            if (firstLineElems.length > 1) {
                System.out.println("First line contains more than the declaration of the number of points in the file." +
                        " Please format the input text file correctly.");
                System.exit(-1);
            }

            numPoints = Integer.parseInt(firstLine);
            if (numPoints < 0) {
                System.out.println("Declared negative number of points in the file." +
                        " Please format the input text file correctly.");
                System.exit(-1);
            }

        }
        catch(FileNotFoundException e) {
            System.out.println("The path passed in as a command line argument does not correspond to a file.");
            System.exit(-1);
        }
        catch (NoSuchElementException e) {
            System.out.println("Input file is empty. Please format the input text file correctly.");
            System.exit(-1);
        }
        catch (NumberFormatException e) {
            System.out.println("Declared a non-integer as number of points in the file." +
                    " Please format the input text file correctly.");
            System.exit(-1);
        }

        TwoDTree tree = new TwoDTree();

        for (int i = 0; i < numPoints; i++) {

            int currX, currY;

            try {
                String currLine = scanFile.nextLine();

                String[] currLineElems = currLine.split(" ");
                if (currLineElems.length < 2) {
                    System.out.println("Line " + (i+2) + " does not specify both point coordinates." +
                            " Please format the input text file correctly.");
                    System.exit(-1);
                }
                if (currLineElems.length > 2) {
                    System.out.println("Line " + (i+2) + " specifies more than the two point coordinates." +
                            " Please format the input text file correctly.");
                    System.exit(-1);
                }

                currX = Integer.parseInt(currLineElems[0]);
                currY = Integer.parseInt(currLineElems[1]);

                if (!(currX >= 0 && currX <= 100)) {
                    System.out.println("Line " + (i+2) + " specifies out of bounds x-coordinate for the respective point." +
                            " Please format the input text file correctly.");
                    System.exit(-1);
                }
                if (!(currY >= 0 && currY <= 100)) {
                    System.out.println("Line " + (i+2) + " specifies out of bounds y-coordinate for the respective point." +
                            " Please format the input text file correctly.");
                    System.exit(-1);
                }

                Point currPoint = new Point(currX, currY);
                tree.insert(currPoint);

            }
            catch (NoSuchElementException e) {
                System.out.println("Input file has less lines than specified. Please format the input text file correctly.");
                System.exit(-1);
            }
            catch (NumberFormatException e) {
                System.out.println("Line " + (i+2) + " declares a non-integer as a coordinate of the respective point." +
                        " Please format the input text file correctly.");
                System.exit(-1);
            }
        }

        if (scanFile.hasNext()) {
            System.out.println("Input file has more lines than specified. Please format the input text file correctly.");
            System.exit(-1);
        }

        scanFile.close();

        Scanner scanIn = new Scanner(System.in);
        int userChoice;

        while (true) {
            System.out.println("Select desired action, by pressing the corresponding key from options below:");
            System.out.println("1. Compute the size of the tree");
            System.out.println("2. Insert a new point");
            System.out.println("3. Search if a given point exists in the tree");
            System.out.println("4. Provide a query rectangle");
            System.out.println("5. Provide a query point");
            System.out.println("6. Exit");

            try {
                userChoice = scanIn.nextInt();
                switch (userChoice) {
                    case 1 -> {
                        System.out.println("Tree size is = " + tree.size());
                        System.out.println();
                    }
                    case 2 -> {
                        try {
                            int x, y;
                            System.out.println("Please provide x-coordinate: ");
                            x = scanIn.nextInt();
                            if (!(x >= 0 && x <= 100)) throw new InputMismatchException();
                            System.out.println("Please provide y-coordinate: ");
                            y = scanIn.nextInt();
                            if (!(y >= 0 && y <= 100)) throw new InputMismatchException();

                            int initialSize = tree.size();
                            Point newPoint = new Point(x, y);
                            tree.insert(newPoint);

                            if (tree.size() == initialSize + 1)
                                System.out.println("Point " + newPoint + " successfully inserted in tree.");

                            System.out.println();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please pass in two non-negative integers up to and including 100" +
                                    "\nthat correspond to the coordinates of the point to be inserted.");
                            scanIn.nextLine();
                            System.out.println();
                        }
                    }
                    case 3 -> {
                        try {
                            int x, y;
                            System.out.println("Please provide x-coordinate: ");
                            x = scanIn.nextInt();
                            if (!(x >= 0 && x <= 100)) throw new InputMismatchException();
                            System.out.println("Please provide y-coordinate: ");
                            y = scanIn.nextInt();
                            if (!(y >= 0 && y <= 100)) throw new InputMismatchException();

                            Point searchPoint = new Point(x, y);
                            boolean foundPoint = tree.search(searchPoint);

                            if (foundPoint)
                                System.out.println("Point " + searchPoint + " found in the tree.");
                            else
                                System.out.println("Point " + searchPoint + " not found in the tree.");

                            System.out.println();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please pass in two non-negative integers up to and including 100" +
                                    "\nthat correspond to the coordinates of the point to be searched for in the tree.");
                            scanIn.nextLine();
                            System.out.println();
                        }
                    }
                    case 4 -> {
                        try {
                            int xmin, xmax, ymin, ymax;
                            System.out.println("Please provide minimum x-coordinate: ");
                            xmin = scanIn.nextInt();
                            if (!(xmin >= 0 && xmin <= 100)) throw new InputMismatchException();
                            System.out.println("Please provide maximum x-coordinate: ");
                            xmax = scanIn.nextInt();
                            if (!(xmax >= xmin && xmax <= 100)) throw new InputMismatchException();
                            System.out.println("Please provide minimum y-coordinate: ");
                            ymin = scanIn.nextInt();
                            if (!(ymin >= 0 && ymin <= 100)) throw new InputMismatchException();
                            System.out.println("Please provide maximum y-coordinate: ");
                            ymax = scanIn.nextInt();
                            if (!(ymax >= ymin && ymax <= 100)) throw new InputMismatchException();

                            Rectangle queryRect = new Rectangle(xmin, ymin, xmax, ymax);
                            List<Point> queryPoints = tree.rangeSearch(queryRect);

                            int numPointsFound = queryPoints.size();

                            if (numPointsFound == 0)
                                System.out.println("No points of the tree found within bounds of rectangle " + queryRect + ".");
                            else {
                                System.out.println(numPointsFound + " points of the tree found within bounds of rectangle " + queryRect + ":");
                                for (int i = 0; i < numPointsFound; i++)
                                    System.out.println((i+1) + ": Point " + queryPoints.removeFromFront());
                            }
                            System.out.println();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please pass in four non-negative integers up to and including 100" +
                                    " that correspond to the minimum and maximum\nx and y coordinates of the rectangle (where each maximum" +
                                    " is at least as great as the minimum), \nto display the points of the tree that lie within the" +
                                    " specified rectangles' bounds.");
                            scanIn.nextLine();
                            System.out.println();
                        }
                    }
                    case 5 -> {
                        if (tree.isEmpty())
                            System.out.println("Tree is empty, therefore there exists no nearest neighbor to any query point.");
                        try {
                            int x, y;
                            System.out.println("Please provide x-coordinate: ");
                            x = scanIn.nextInt();
                            if (!(x >= 0 && x <= 100)) throw new InputMismatchException();
                            System.out.println("Please provide y-coordinate: ");
                            y = scanIn.nextInt();
                            if (!(y >= 0 && y <= 100)) throw new InputMismatchException();

                            Point queryPoint = new Point(x, y);
                            Point nearestNeighbor = tree.nearestNeighbor(queryPoint);

                           System.out.println("Nearest neighbor of point " + queryPoint + " in the tree is point "
                                   + nearestNeighbor + ".");

                            System.out.println();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please pass in two non-negative integers up to and including 100" +
                                    " that correspond\nto the coordinates of the point for which the nearest neighbors is to" +
                                    " be searched for in the tree.");
                            scanIn.nextLine();
                            System.out.println();
                        }
                    }
                    case 6 ->
                        System.exit(0);
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please pass in the integer that corresponds to desired action.");
                // InputMismatchException causes System.In to continually pass in the newline character,
                // so we use nextLine to stop that, and take in user input again.
                scanIn.nextLine();
                System.out.println();
            }

        }

    }

    private int size;

    private class TreeNode {
        Point item;
        TreeNode r;
        TreeNode l;

        TreeNode(Point item) {
            this.item = item;
            r = l = null;
        }
    }

    private TreeNode head; //root of the tree
    public TwoDTree() // construct an empty tree
    {
        head = null;
        size = 0;
    }
    public boolean isEmpty() // is the tree empty?
    {
        return head == null;
    }
    public int size() // number of points in the tree
    {
        return size;
    }
    public void insert(Point p) // inserts the point p to the tree
    {
        head = insertX(head, p);
    }
    private TreeNode insertX(TreeNode h, Point p) {
        // check whether we reached a null node, and if so insert point
        if (h == null) {
            size++;
            return new TreeNode(p);
        }
        if (h.item.x() == p.x() && h.item.y() == p.y()) {
            System.out.println("Point " + p + " is already present in tree.");
            return h;
        }
        // if current x-coordinate is less than point x-coordinate, continue left, otherwise continue right
        if (p.x() < h.item.x()) h.l = insertY(h.l, p);
        else h.r = insertY(h.r, p);
        return h;
    }
    private TreeNode insertY(TreeNode h, Point p) {
        if (h == null) {
            size++;
            return new TreeNode(p);
        }
        if (h.item.x() == p.x() && h.item.y() == p.y()) {
            System.out.println("Point " + p + " is already present in tree.");
            return h;
        }
        if (p.y() < h.item.y()) h.l = insertX(h.l, p);
        else h.r = insertX(h.r, p);
        return h;
    }

    public boolean search(Point p) // does the tree contain p?
    {
        return searchX(head, p);
    }
    private boolean searchX(TreeNode h, Point p) {
        // if reached null node, the point is not in the tree
        if (h == null) return false;
        // check whether the current point is the point we are searching for
        if (h.item.x() == p.x() && h.item.y() == p.y()) return true;
        // if current x-coordinate is less than point x-coordinate, continue left, otherwise continue right
        if (p.x() < h.item.x()) return searchY(h.l, p);
        else return searchY(h.r, p);
    }
    private boolean searchY(TreeNode h, Point p) {
        if (h == null) return false;
        if (h.item.x() == p.x() && h.item.y() == p.y()) return true;
        if (p.y() < h.item.y()) return searchX(h.l, p);
        else return searchX(h.r, p);
    }

    public Point nearestNeighbor(Point p) // point in the tree that is closest to p (null if tree is empty)
    {
        if (head == null) return null;
        Rectangle fullSpace = new Rectangle(0, 0, 100, 100);
        return nearestNeighborX(head, fullSpace, head.item, p);
    }
    private Point nearestNeighborX(TreeNode h, Rectangle r, Point nearestNeighbor, Point p)
    {
        // check whether we found the point we are searching the nearest neighbor of, and if so return it
        if (h.item.x() == p.x() && h.item.y() == p.y()) return h.item;

        // check whether current point is closer to query point than previous nearest neighbor,
        // and if so, make that the nearest neighbor
        nearestNeighbor = p.squareDistanceTo(nearestNeighbor) <= p.squareDistanceTo(h.item) ?
                            nearestNeighbor : h.item;

        // check whether current node has no children, and if so, return nearest neighbor
        if (h.l == null && h.r == null) return nearestNeighbor;

        // declare subspaces for each child, and check whether they are worth searching through
        Rectangle leftSpace = new Rectangle(r.xmin(), r.ymin(), h.item.x(), r.ymax());
        Rectangle rightSpace = new Rectangle(h.item.x(), r.ymin(), r.xmax(), r.ymax());
        boolean leftSpaceWorthSearching = p.squareDistanceTo(nearestNeighbor) > leftSpace.squareDistanceTo(p);
        boolean rightSpaceWorthSearching = p.squareDistanceTo(nearestNeighbor) > rightSpace.squareDistanceTo(p);

        // if the left child exists and its subspace is worth searching, continue searching there
        Point leftChild = null;
        if (h.l != null && leftSpaceWorthSearching)
            leftChild = nearestNeighborY(h.l, leftSpace, nearestNeighbor, p);

        // if the right child exists and its subspace is worth searching, continue searching there
        Point rightChild = null;
        if (h.r != null && rightSpaceWorthSearching)
            rightChild = nearestNeighborY(h.r, rightSpace, nearestNeighbor, p);

        // Compute nearest child
        Point nearestChild;
        if (leftChild == null)
            nearestChild = rightChild;
        else if (rightChild == null)
            nearestChild = leftChild;
        else
            nearestChild = p.squareDistanceTo(leftChild) < p.squareDistanceTo(rightChild) ? leftChild : rightChild;

        // check whether there is no nearest child (the subspaces of the existing children are not worth searching through),
        // and if so, return the current nearest neighbor
        if (nearestChild == null) return nearestNeighbor;

        // if there exists a nearest child, compare it to nearest neighbor, and update it accordingly
        if (p.squareDistanceTo(nearestChild) < p.squareDistanceTo(nearestNeighbor))
            nearestNeighbor = nearestChild;

        return nearestNeighbor;
    }
    private Point nearestNeighborY(TreeNode h, Rectangle r, Point nearestNeighbor, Point p)
    {
        if (h.item.x() == p.x() && h.item.y() == p.y()) return h.item;

        nearestNeighbor = p.squareDistanceTo(nearestNeighbor) <= p.squareDistanceTo(h.item) ?
                nearestNeighbor : h.item;

        Rectangle lowerSpace = new Rectangle(r.xmin(), r.ymin(), r.xmax(), h.item.y());
        Rectangle upperSpace = new Rectangle(r.xmin(), h.item.y(), r.xmax(), r.ymax());

        if (h.l == null && h.r == null) return nearestNeighbor;

        boolean lowerSpaceWorthSearching = p.squareDistanceTo(nearestNeighbor) > lowerSpace.squareDistanceTo(p);
        boolean upperSpaceWorthSearching = p.squareDistanceTo(nearestNeighbor) > upperSpace.squareDistanceTo(p);

        Point leftChild = null;
        if (h.l != null && lowerSpaceWorthSearching)
            leftChild = nearestNeighborX(h.l, lowerSpace, nearestNeighbor, p);

        Point rightChild = null;
        if (h.r != null && upperSpaceWorthSearching)
            rightChild = nearestNeighborX(h.r, upperSpace, nearestNeighbor, p);

        Point nearestChild;

        if (leftChild == null)
            nearestChild = rightChild;
        else if (rightChild == null)
            nearestChild = leftChild;
        else
            nearestChild = p.squareDistanceTo(leftChild) < p.squareDistanceTo(rightChild) ? leftChild : rightChild;

        if (nearestChild == null) return nearestNeighbor;

        if (p.squareDistanceTo(nearestChild) < p.squareDistanceTo(nearestNeighbor))
            nearestNeighbor = nearestChild;

        return nearestNeighbor;
    }

    public List<Point> rangeSearch(Rectangle rect) // Returns a list with the Points that are contained in the rectangle
    {
        List<Point> containedPoints = new List<>();
        if (head == null) return containedPoints;
        Rectangle fullSpace = new Rectangle(0, 0, 100, 100);
        rangeSearchX(head, fullSpace, rect, containedPoints);

        return containedPoints;
    }
    private void rangeSearchX(TreeNode h, Rectangle curr, Rectangle r, List<Point> list)
    {
        // if current point is contained within the rectangle, add it to the list
        if (r.contains(h.item)) list.insertAtFront(h.item);

        // check whether current node has no children, and if so, stop running
        if (h.l == null && h.r == null) return;

        // declare subspaces for each child, and check whether they are worth searching through
        Rectangle leftSpace = new Rectangle(curr.xmin(), curr.ymin(), h.item.x(), curr.ymax());
        Rectangle rightSpace = new Rectangle(h.item.x(), curr.ymin(), curr.xmax(), curr.ymax());
        boolean leftSpaceWorthSearching = r.intersects(leftSpace);
        boolean rightSpaceWorthSearching = r.intersects(rightSpace);

        // if the left child exists and its subspace is worth searching, continue searching there
        if (h.l != null && leftSpaceWorthSearching)
            rangeSearchY(h.l, leftSpace, r, list);

        // if the right child exists and its subspace is worth searching, continue searching there
        if (h.r != null && rightSpaceWorthSearching)
            rangeSearchY(h.r, rightSpace, r, list);
    }
    private void rangeSearchY(TreeNode h, Rectangle curr, Rectangle r, List<Point> list)
    {
        if (r.contains(h.item)) list.insertAtFront(h.item);

        Rectangle lowerSpace = new Rectangle(curr.xmin(), curr.ymin(), curr.xmax(), h.item.y());
        Rectangle upperSpace = new Rectangle(curr.xmin(), h.item.y(), curr.xmax(), curr.ymax());

        boolean lowerSpaceWorthSearching = r.intersects(lowerSpace);
        boolean upperSpaceWorthSearching = r.intersects(upperSpace);

        if (h.l == null && h.r == null) return;

        if (h.l != null && lowerSpaceWorthSearching)
            rangeSearchX(h.l, lowerSpace, r, list);

        if (h.r != null && upperSpaceWorthSearching)
            rangeSearchX(h.r, upperSpace, r, list);
    }
}
