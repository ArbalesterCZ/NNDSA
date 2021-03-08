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
public class Path implements Serializable {

    public int length;
    public Town start;
    public Town target;

    public Path(Town start, Town target) {
        length = (int) start.getCoordinate().distance(target.getCoordinate());
        this.start = start;
        this.target = target;
    }

    @Override
    public String toString() {
        return String.format("Length: [%d] From: [%s] To: [%s]", length, start, target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Path other = (Path) obj;
        return Objects.equals(start, other.start) && Objects.equals(target, other.target);
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeInt(length);
        aOutputStream.writeObject(start);
        aOutputStream.writeObject(target);
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        length = aInputStream.readInt();
        start = (Town) aInputStream.readObject();
        target = (Town) aInputStream.readObject();
    }
}
