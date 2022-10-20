package infrastructure.mock;

import model.Document;
import model.DocumentRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentRepositoryMock implements DocumentRepository {
    Set<Document> documents = new HashSet<>();

    @Override
    public void saveDocument(Document document) {
        documents.remove(document);
        documents.add(document);
    }

    @Override
    public Optional<Document> findDocumentById(Long id) {
        return documents.stream().filter(t -> t.getId().equals(id)).findAny();
    }

    @Override
    public Set<Document> findAllDocumentsByTitle(String title) {
        return documents.stream().filter(t -> t.getTitle().equals(title)).collect(Collectors.toSet());
    }
}
