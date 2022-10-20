package model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class MultiplePathsException extends RuntimeException {
    Set<Path> paths;

    public MultiplePathsException(String message, Set<Path> paths) {
        super(message);
        this.paths = new HashSet<>(paths);
    }

    public Set<Path> getPaths() {
        return paths;
    }
}
