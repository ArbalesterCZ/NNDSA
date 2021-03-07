package nndsa.semestralwork.a.structures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author milan.horak
 */
public class Town implements Serializable {

    public String name;
    private Point coordinate;

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
        return Objects.hashCode(name);
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
        return Objects.equals(name, other.name);
    }

    public int getX() {
        return coordinate.x;
    }

    public int getY() {
        return coordinate.y;
    }

    public void setX(int value) {
        coordinate.x = value;
    }

    public void setY(int value) {
        coordinate.y = value;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeUTF(name);
        aOutputStream.writeInt(coordinate.x);
        aOutputStream.writeInt(coordinate.y);
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        name = aInputStream.readUTF();
        coordinate = new Point(aInputStream.readInt(), aInputStream.readInt());
    }
}
