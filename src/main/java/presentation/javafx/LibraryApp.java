package presentation.javafx;

import appservice.DocumentRegistrationService;
import appservice.DocumentUpdateService;
import appservice.TopicRegistrationService;
import appservice.TopicUpdateService;
import infrastructure.hibernate.DocumentRepositoryImpl;
import infrastructure.hibernate.IdGeneratorFactory;
import infrastructure.mock.CounterIdGenerator;
import infrastructure.mock.TopicHierarchyMock;
import infrastructure.mock.TopicRepositoryMock;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;
import presentation.javafx.model.LiteratureEditOperation;
import presentation.javafx.model.LiteratureEditOperationFactory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LibraryApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Set up infrastructure
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");
        IdGeneratorFactory idGeneratorFactory = new IdGeneratorFactory(emf);
        TopicRepository topicRepository = new TopicRepositoryMock();
        DocumentRepository documentRepository = new DocumentRepositoryImpl(emf);
        TopicHierarchy topicHierarchy = new TopicHierarchyMock();
        TopicFactory topicFactory = new TopicFactory(new CounterIdGenerator());
        DocumentFactory documentFactory = new DocumentFactory(idGeneratorFactory.createDocumentIdGenerator());
        DocumentRegistrationService documentRegistrationService = new DocumentRegistrationService(documentRepository, topicRepository, documentFactory);
        DocumentUpdateService documentUpdateService = new DocumentUpdateService(documentRepository);
        TopicRegistrationService topicRegistrationService = new TopicRegistrationService(topicRepository, topicHierarchy, topicFactory);
        TopicUpdateService topicUpdateService = new TopicUpdateService(topicRepository);

        // Populate root with test data
        Document doc0 = documentFactory.create("Applied Finite Group Actions");
        Document doc1 = documentFactory.create("Applied Mathematics for Database Professionals");
        documentRepository.saveDocument(doc0);
        documentRepository.saveDocument(doc1);

        NamedTopic topic0 = topicFactory.createNamed("All");
        NamedTopic topic1 = topicFactory.createNamed("Group Theory");
        topicRepository.saveTopic(topic1);
        topicHierarchy.addSubtopicRelationship(topic0, topic1);

        topic1.addToBaseLiterature(doc0);
        topic1.addToBaseLiterature(doc1);

        LiteratureItem root = new TopicItemFactory(topicHierarchy).create(topic0);

        // Create a RelevantDocumentsView component using the data
        LiteratureEditOperationFactory editOperationFactory = new LiteratureEditOperationFactory(
                documentRegistrationService, documentUpdateService, topicRegistrationService, topicUpdateService);
        MappedLiteratureView mappedLiteratureView = new MappedLiteratureView(root);
        mappedLiteratureView.setShowRoot(false);
        mappedLiteratureView.setEditable(true);
        mappedLiteratureView.setCellFactory(v -> new LiteratureCell(editOperationFactory));
        mappedLiteratureView.setMinWidth(400);

        mappedLiteratureView.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                e.consume();
            }
        });

        mappedLiteratureView.addEventHandler(MappedLiteratureView.editCommitEvent(), e -> {
            mappedLiteratureView.setEditOperation(null);
        });

        mappedLiteratureView.setOnEditStart(e -> {
            LiteratureEditOperation editOperation = null;

            if (!e.getOldValue().isCreated() && e.getOldValue().isDocument())
                editOperation = editOperationFactory.createAddDocumentOperation(e.getTreeItem().getParent().getValue());
            if (e.getOldValue().isCreated() && e.getOldValue().isDocument())
                editOperation = editOperationFactory.createEditDocumentOperation(e.getOldValue());
            if (!e.getOldValue().isCreated() && e.getOldValue().isTopic())
                editOperation = editOperationFactory.createEditTopicOperation(e.getTreeItem().getParent().getValue());
            if (e.getOldValue().isCreated() && e.getOldValue().isTopic())
                editOperation = editOperationFactory.createAddTopicOperation(e.getOldValue());

            mappedLiteratureView.setEditOperation(editOperation);
        });

        // Set up scene and stage for displaying the component
        Scene scene = new Scene(mappedLiteratureView);
        stage.setScene(scene);
        stage.show();
    }
}
