package appservice;

import model.Document;
import model.DocumentFactory;
import model.DocumentRepository;

import java.util.NoSuchElementException;

public class DocumentUpdateService {
    private DocumentRepository documentRepository;
    private Document lastDocumentUpdated;

    public DocumentUpdateService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void updateTitle(Long documentId, String newTitle) {
        documentRepository.findDocumentById(documentId).orElseThrow(() -> new NoSuchElementException("Document %s not found".formatted(documentId)));
        Document document = new DocumentFactory(() -> documentId).create(newTitle);
        documentRepository.saveDocument(document);
        lastDocumentUpdated = document;
    }

    public Document getLastDocumentUpdated() {
        return lastDocumentUpdated;
    }
}
