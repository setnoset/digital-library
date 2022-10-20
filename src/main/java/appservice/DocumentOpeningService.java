package appservice;

import model.*;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class DocumentOpeningService {
    private DocumentRepository documentRepository;
    private StoredDocumentLocator storedDocumentLocator;
    private StoredDocumentOpener storedDocumentOpener;

    public DocumentOpeningService(DocumentRepository documentRepository, StoredDocumentLocator storedDocumentLocator, StoredDocumentOpener storedDocumentOpener) {
        this.documentRepository = documentRepository;
        this.storedDocumentLocator = storedDocumentLocator;
        this.storedDocumentOpener = storedDocumentOpener;
    }


    public void openDocument(Long documentId) {
        Optional<Document> documentOptional = documentRepository.findDocumentById(documentId);
        Document document = documentOptional.orElseThrow(() -> new NoSuchElementException("Document " + documentId + " not found"));

        Set<Path> paths = storedDocumentLocator.getPaths(document);

        if (paths.size() == 0)
            throw new NoSuchElementException("No system paths found for opening document " + documentId);
        if (paths.size() > 1)
            throw new MultiplePathsException("Multiple paths can found for opening document " + documentId, paths);

        Path path = paths.stream().findAny().orElseThrow();

        storedDocumentOpener.openDocument(path);
    }
}
