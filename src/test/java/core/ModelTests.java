package core;

import infrastructure.mock.CounterIdGenerator;
import infrastructure.mock.DocumentRepositoryMock;
import infrastructure.mock.TopicHierarchyMock;
import infrastructure.mock.TopicRepositoryMock;
import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class ModelTests {

    @Test
    public void queryDocumentByTopic() {
        TopicRepository topicRepository = new TopicRepositoryMock();
        TopicHierarchy topicHierarchy = new TopicHierarchyMock();
        NamedTopic groupTheory = new TopicFactory(() -> 0L).createNamed("Group Theory");
        Document document = new DocumentFactory(() -> 0L).create("Applied Finite Group Actions");
        groupTheory.addToBaseLiterature(document);
        topicRepository.saveTopic(groupTheory);

        NamedTopic topic = topicRepository.findTopicByName("Group Theory").orElseThrow();
        Set<Document> results = topic.getBaseLiterature();
        Document foundDocument = results.stream().findAny().orElseThrow();

        assertEquals(1, results.size());
        assertEquals("Applied Finite Group Actions", foundDocument.getTitle());
    }

    @Test
    public void registerDocument() {
        DocumentRepository documentRepository = new DocumentRepositoryMock();
        IdGenerator<Long> documentIdGenerator = new CounterIdGenerator();

        Document document = new DocumentFactory(documentIdGenerator).create("Applied Finite Group Actions");
        documentRepository.saveDocument(document);
        Set<Document> results = documentRepository.findAllDocumentsByTitle("Applied Finite Group Actions");
        Document foundDocument = results.stream().findAny().orElseThrow();

        assertEquals(1, results.size());
        assertEquals("Applied Finite Group Actions", foundDocument.getTitle());
    }

    @Test
    public void locateStoredDocument() {
        Document appliedFiniteGroupActionsBook = new DocumentFactory(() -> 0L).create("Applied Finite Group Actions");
        Path pathToStoredDocument = Paths.get("C:\\Users\\Meu Computador\\Home\\Library\\Applied Finite Group Actions");
        StoredDocumentLocator storedDocumentLocator = new StoredDocumentLocatorMock();
        storedDocumentLocator.registerDocumentPath(appliedFiniteGroupActionsBook, pathToStoredDocument);

        Set<Path> paths = storedDocumentLocator.getPaths(appliedFiniteGroupActionsBook);
        Path foundPath = paths.stream().findAny().orElseThrow();

        assertEquals(1, paths.size());
        assertEquals("C:\\Users\\Meu Computador\\Home\\Library\\Applied Finite Group Actions", foundPath.toString());
    }

    @Test
    public void findDirectSubtopicsOfTopic() {
        TopicHierarchyMock knowledgeHierarchy = new TopicHierarchyMock();
        NamedTopic mathematics = new TopicFactory(() -> 0L).createNamed("Mathematics");
        NamedTopic groupTheory = new TopicFactory(() -> 1L).createNamed("Group Theory");
        SubtopicRelationship subtopicRelationship = new SubtopicRelationship.Builder()
                .topic(mathematics).hasSubtopic(groupTheory);
        knowledgeHierarchy.addSubtopicRelationship(subtopicRelationship);

        Set<Topic> mathSubtopics = mathematics.getDirectSubtopics(knowledgeHierarchy);

        assertEquals(1, mathSubtopics.size());
        assertTrue(mathSubtopics.contains(groupTheory));
    }

    @Test
    public void findDocumentsAboutTopic() {
        TopicHierarchyMock knowledgeHierarchy = new TopicHierarchyMock();
        NamedTopic groupTheory = new TopicFactory(() -> 1L).createNamed("Group Theory");
        Document appliedFiniteGroupActionsBook = new DocumentFactory(() -> 0L).create("Applied Finite Group Actions");
        groupTheory.addToBaseLiterature(appliedFiniteGroupActionsBook);

        Set<Document> groupTheoryLiterature = groupTheory.getBaseLiterature();

        assertEquals(1, groupTheoryLiterature.size());
        assertTrue(groupTheoryLiterature.contains(appliedFiniteGroupActionsBook));
    }
}
