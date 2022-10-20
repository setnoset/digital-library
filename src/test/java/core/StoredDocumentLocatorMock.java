package core;

import model.Document;
import model.StoredDocumentLocator;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StoredDocumentLocatorMock implements StoredDocumentLocator {
    Map<Document, Set<Path>> documentPathMap = new HashMap<>();

    @Override
    public void registerDocumentPath(Document document, Path path) {
        if (documentPathMap.containsKey(document))
            documentPathMap.get(document).add(path);
        else
            documentPathMap.put(document, Set.of(path));
    }

    @Override
    public Set<Path> getPaths(Document document) {
        return documentPathMap.getOrDefault(document, new HashSet<>());
    }
}
