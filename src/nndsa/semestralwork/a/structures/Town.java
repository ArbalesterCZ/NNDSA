package nndsa.semestralwork.a.structures;

import java.util.Objects;

/**
 *
 * @author milan.horak
 */
public class Town {

    private final String name;
    private final Point coordinate;

    public Town(String name, Point coordinate) {
        this.name = name;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {     
        return String.format("City: [%s] Coordinate: %s", name, coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Town other = (Town) obj;
        return Objects.equals(coordinate, other.coordinate);
    }

    public String getName() {
        return name;
    }

    public Point getCoordinate() {
        return coordinate;
    }
}
