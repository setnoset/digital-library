package model;

import java.util.Optional;
import java.util.Set;

public interface DocumentRepository {

    void saveDocument(Document document);

    Optional<Document> findDocumentById(Long id);

    Set<Document> findAllDocumentsByTitle(String title);
}
