package model;

import java.nio.file.Path;
import java.util.Set;

public interface StoredDocumentLocator {

    void registerDocumentPath(Document document, Path path);

    Set<Path> getPaths(Document document);
}
