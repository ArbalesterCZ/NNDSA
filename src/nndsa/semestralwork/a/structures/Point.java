package nndsa.semestralwork.a.structures;

import java.util.Objects;

/**
 *
 * @author milan.horak
 */
public class Point {

    public int x;
    public int y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    public Point add(Point addend) {
        return new Point(x + addend.x, y + addend.y);
    }

    public Point multiple(Point multiply) {
        return new Point(x * multiply.x, y * multiply.y);
    }
}
