package core;

import model.StoredDocumentOpener;

import java.nio.file.Path;

public class StoredDocumentOpenerMock implements StoredDocumentOpener {

    @Override
    public void openDocument(Path path) {
        System.out.println("Opening document at " + path);
    }
}
