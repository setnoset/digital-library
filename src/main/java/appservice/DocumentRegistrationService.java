package appservice;

import model.*;

import java.util.NoSuchElementException;

public class DocumentRegistrationService {
    private DocumentRepository documentRepository;
    private TopicRepository topicRepository;
    private DocumentFactory documentFactory;
    private Document lastDocumentRegistered;

    public DocumentRegistrationService(DocumentRepository documentRepository, TopicRepository topicRepository, DocumentFactory documentFactory) {
        this.documentRepository = documentRepository;
        this.topicRepository = topicRepository;
        this.documentFactory = documentFactory;
    }

    public void registerDocument(String documentTitle, String topicName) {
        Document document = documentFactory.create(documentTitle);
        documentRepository.saveDocument(document);

        if (topicName != null) {
            NamedTopic topic = topicRepository.findTopicByName(topicName).orElseThrow(() -> new NoSuchElementException("Topic '%s' not found".formatted(topicName)));
            topic.addToBaseLiterature(document);
        }

        lastDocumentRegistered = document;
    }

    public Document getLastDocumentRegistered() {
        return lastDocumentRegistered;
    }
}
