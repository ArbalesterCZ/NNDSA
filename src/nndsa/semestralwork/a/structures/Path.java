package nndsa.semestralwork.a.structures;

import java.util.Objects;

/**
 *
 * @author milan.horak
 */
public class Path {

    private final int length;
    private final Town start;
    private final Town end;

    public Path(int length, Town start, Town end) {
        this.length = length;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("Length: [%d] From: [%s] To: [%s]", length, start, end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
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
        return Objects.equals(start, other.start) && Objects.equals(end, other.end);
    }

    public int getLength() {
        return length;
    }
}
