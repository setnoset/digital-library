package model;

import java.nio.file.Path;

public interface StoredDocumentOpener {

    void openDocument(Path path);
}
