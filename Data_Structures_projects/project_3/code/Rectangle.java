public class Rectangle {

    private int xmin, ymin, xmax, ymax;

    Rectangle(int xmin, int ymin, int xmax, int ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public int xmin() { return xmin; } // minimum x-coordinate of rectangle
    public int ymin() { return ymin; } // minimum y-coordinate of rectangle
    public int xmax() { return xmax; } // maximum x-coordinate of rectangle
    public int ymax() { return ymax; } // maximum y-coordinate of rectangle
    public boolean contains(Point p) // does p belong to the rectangle?
    {
        // check whether the points' coordinates fall within the bounds of the rectangle
        return (p.x() >= xmin && p.x() <= xmax) && (p.y() >= ymin && p.y() <= ymax );
    }
    public boolean intersects(Rectangle that) // do the two rectangles intersect?
    {
        // check whether this rectangle contains one of the corners of that rectangle
        Point that_bottom_left = new Point(that.xmin(), that.ymin());
        Point that_bottom_right = new Point(that.xmax(), that.ymin());
        Point that_top_left = new Point(that.xmin(), that.ymax());
        Point that_top_right = new Point(that.xmax(), that.ymax());

        if (contains(that_bottom_left) || contains(that_bottom_right) || contains(that_top_left) || contains(that_top_right))
            return true;

        // check whether that rectangle contains one of the corners of this rectangle
        Point bottom_left = new Point(xmin, ymin);
        Point bottom_right = new Point(xmax, ymin);
        Point top_left = new Point(xmin, ymax);
        Point top_right = new Point(xmax, ymax);

        if (that.contains(bottom_left) || that.contains(bottom_right) || that.contains(top_left) || that.contains(top_right))
            return true;

        // if neither rectangle contains one or more corners of the other, then they do not intersect
        return false;
    }
    public double distanceTo(Point p) // Euclidean distance from p to the closest point in rectangle
    {
        if (contains(p)) return 0.0; // check whether p belongs to rectangle

        if (p.x() >= xmin && p.x() <= xmax) { // check whether p is within the horizontal bounds of the rectangle
            if (p.y() < ymin) return ymin - p.y(); // check if p is below or above the rectangle
            else return p.y() - ymax;              // and return the vertical distance
        }

        if (p.y() >= ymin && p.y() <= ymax) { // check whether p is within the vertical bounds of the rectangle
            if (p.x() < xmin) return xmin - p.x(); // check if p is to the left or to the right of the rectangle
            else return p.x() - xmax;              // and return the horizontal distance
        }

        if (p.x() < xmin && p.y() < ymin) { // check whether p is to the left and below the rectangle
            Point bottom_left = new Point(xmin, ymin);
            return bottom_left.distanceTo(p); // return its distance to the bottom left corner of the rectangle
        }

        if (p.x() > xmax && p.y() < ymin) { // check whether p is to the right and below the rectangle
            Point bottom_right = new Point(xmax, ymin);
            return bottom_right.distanceTo(p); // return its distance to the bottom right corner of the rectangle
        }

        if (p.x() < xmin && p.y() > ymax) { // check whether p is to the left and above the rectangle
            Point top_left = new Point(xmin, ymax);
            return top_left.distanceTo(p); // return its distance to the top left corner of the rectangle
        }

        else { // if (p.x() > xmax && p.y() > ymax) // check whether p is to the right and above the rectangle
            Point top_right = new Point(xmax, ymax);
            return top_right.distanceTo(p); // return its distance to the top right corner of the rectangle
        }
    }
    public int squareDistanceTo(Point p) // square of Euclidean distance from p to the closest point in rectangle
    {
        if (contains(p)) return 0; // check whether p belongs to rectangle

        if (p.x() >= xmin && p.x() <= xmax) { // check whether p is within the horizontal bounds of the rectangle
            if (p.y() < ymin) return (ymin - p.y()) * (ymin - p.y()); // check if p is below or above the rectangle
            else return (p.y() - ymax) * (p.y() - ymax); // and return the square of the vertical distance
        }

        if (p.y() >= ymin && p.y() <= ymax) { // check whether p is within the vertical bounds of the rectangle
            if (p.x() < xmin) return (xmin - p.x()) * (xmin - p.x()); // check if p is to the left or to the right of the rectangle
            else return (p.x() - xmax) * (p.x() - xmax); // and return the square of the horizontal distance
        }

        if (p.x() < xmin && p.y() < ymin) { // check whether p is to the left and below the rectangle
            Point bottom_left = new Point(xmin, ymin);
            return bottom_left.squareDistanceTo(p); // return the square of its distance to the bottom left corner of the rectangle
        }

        if (p.x() > xmax && p.y() < ymin) { // check whether p is to the right and below the rectangle
            Point bottom_right = new Point(xmax, ymin);
            return bottom_right.squareDistanceTo(p); // return the square of its distance to the bottom right corner of the rectangle
        }

        if (p.x() < xmin && p.y() > ymax) { // check whether p is to the left and above the rectangle
            Point top_left = new Point(xmin, ymax);
            return top_left.squareDistanceTo(p); // return the square of its distance to the top left corner of the rectangle
        }

        else { // if (p.x() > xmax && p.y() > ymax) // check whether p is to the right and above the rectangle
            Point top_right = new Point(xmax, ymax);
            return top_right.squareDistanceTo(p); // return the square of its distance to the top right corner of the rectangle
        }
    }
    public String toString() // string representation: [xmin, xmax] × [ymin, ymax]
    {
        return "[" + xmin + ", " + xmax + "] × [" + ymin + ", " + ymax + "]";
    }
}
