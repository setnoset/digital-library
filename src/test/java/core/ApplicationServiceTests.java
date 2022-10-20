package core;

import appservice.*;
import infrastructure.mock.CounterIdGenerator;
import infrastructure.mock.DocumentRepositoryMock;
import infrastructure.mock.TopicHierarchyMock;
import infrastructure.mock.TopicRepositoryMock;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationServiceTests {
    DocumentRepository documentRepository;
    TopicRepository topicRepository;
    TopicHierarchy topicHierarchy;
    DocumentFactory documentFactory;
    TopicFactory topicFactory;

    @BeforeEach
    public void setup() {
        documentRepository = new DocumentRepositoryMock();
        topicRepository = new TopicRepositoryMock();
        topicHierarchy = new TopicHierarchyMock();
        documentFactory = new DocumentFactory(new CounterIdGenerator());
        topicFactory = new TopicFactory(new CounterIdGenerator());
    }

    @Test
    public void queryDocumentByTopic() {
        NamedTopic topic = topicFactory.createNamed("Group Theory");
        Document appliedFiniteGroupActionsBook = documentFactory.create("Applied Finite Group Actions");
        topic.addToBaseLiterature(appliedFiniteGroupActionsBook);
        topicRepository.saveTopic(topic);
        DocumentQueryService documentQueryService = new DocumentQueryService(topicRepository, topicHierarchy);

        Set<Document> results = documentQueryService.getDocumentsRelevantToTopic("Group Theory");
        Set<Document> filteredResults = results.stream().filter(d -> d.getTitle().equals("Applied Finite Group Actions")).collect(Collectors.toSet());
        Document foundDocument = filteredResults.stream().findAny().orElseThrow();

        assertEquals(1, results.size());
        assertEquals(1, filteredResults.size());
        assertEquals("Applied Finite Group Actions", foundDocument.getTitle());
    }

    @Test
    public void queryDocumentByNonExistingTopic() {
        DocumentQueryService documentQueryService = new DocumentQueryService(topicRepository, topicHierarchy);

        assertThrows(NoSuchElementException.class, () -> documentQueryService.getDocumentsRelevantToTopic("Group Theory"));
    }

    @Test
    public void registerDocument() {
        DocumentRegistrationService documentRegistrationService = new DocumentRegistrationService(documentRepository, topicRepository, documentFactory);

        documentRegistrationService.registerDocument("Applied Finite Group Actions", null);
        Set<Document> results = documentRepository.findAllDocumentsByTitle("Applied Finite Group Actions");
        Document foundDocument = results.stream().findAny().orElseThrow();

        assertEquals(1, results.size());
        assertEquals("Applied Finite Group Actions", foundDocument.getTitle());
    }

    @Test
    public void registerTopic() {
        TopicRegistrationService topicRegistrationService = new TopicRegistrationService(topicRepository, topicHierarchy, topicFactory);

        topicRegistrationService.registerTopic("Group Theory", null);
        NamedTopic topic = topicRepository.findTopicByName("Group Theory").orElseThrow();

        assertEquals("Group Theory", topic.getName());
    }

    @Test
    public void updateDocumentTitle() {
        Document document = new Document(0L, "Applied Finite Actions");
        documentRepository.saveDocument(document);
        DocumentUpdateService documentUpdateService = new DocumentUpdateService(documentRepository);

        documentUpdateService.updateTitle(0L, "Applied Finite Group Actions");
        Document foundDocument = documentRepository.findDocumentById(0L).orElseThrow();

        assertEquals("Applied Finite Group Actions", foundDocument.getTitle());
    }

    @Test
    public void updateTopicName() {
        NamedTopic topic = new TopicFactory(() -> 0L).createNamed("Groups Thor");
        topicRepository.saveTopic(topic);
        TopicUpdateService topicUpdateService = new TopicUpdateService(topicRepository);

        topicUpdateService.updateTopicName("Groups Thor", "Group Theory");
        NamedTopic foundTopic = topicRepository.findTopicByName("Group Theory").orElseThrow();

        assertEquals("Group Theory", foundTopic.getName());
    }

    @Test
    public void openDocument() {
        Document document = new DocumentFactory(() -> 0L).create("Applied Finite Group Actions");
        documentRepository.saveDocument(document);
        StoredDocumentLocator storedDocumentLocator = new StoredDocumentLocatorMock();
        storedDocumentLocator.registerDocumentPath(document, Paths.get("C:\\Users\\Meu Computador\\Home\\Library\\Applied Finite Group Actions"));
        StoredDocumentOpener storedDocumentOpener = new StoredDocumentOpenerMock();
        DocumentOpeningService documentOpeningService = new DocumentOpeningService(documentRepository, storedDocumentLocator, storedDocumentOpener);

        documentOpeningService.openDocument(0L);
    }
}
