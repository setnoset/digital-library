package presentation.javafx.model;

import appservice.DocumentRegistrationService;
import appservice.DocumentUpdateService;
import appservice.TopicRegistrationService;
import appservice.TopicUpdateService;
import presentation.javafx.model.AddDocumentOperation;
import presentation.javafx.model.LiteratureEditOperation;
import presentation.javafx.model.LiteratureItemPresentation;

public class LiteratureEditOperationFactory {
    DocumentRegistrationService documentRegistrationService;
    DocumentUpdateService documentUpdateService;
    TopicRegistrationService topicRegistrationService;
    TopicUpdateService topicUpdateService;

    public LiteratureEditOperationFactory(
            DocumentRegistrationService documentRegistrationService, DocumentUpdateService documentUpdateService,
            TopicRegistrationService topicRegistrationService, TopicUpdateService topicUpdateService) {
        this.documentRegistrationService = documentRegistrationService;
        this.documentUpdateService = documentUpdateService;
        this.topicRegistrationService = topicRegistrationService;
        this.topicUpdateService = topicUpdateService;
    }

    public LiteratureEditOperation createAddDocumentOperation(LiteratureItemPresentation parentTopic) {
        return new AddDocumentOperation(parentTopic, documentRegistrationService);
    }

    public LiteratureEditOperation createEditDocumentOperation(LiteratureItemPresentation document) {
        return new EditDocumentOperation(document, documentUpdateService);
    }

    public LiteratureEditOperation createAddTopicOperation(LiteratureItemPresentation parentTopic) {
        return new AddTopicOperation(parentTopic, topicRegistrationService);
    }

    public LiteratureEditOperation createEditTopicOperation(LiteratureItemPresentation topic) {
        return new EditTopicOperation(topic, topicUpdateService);
    }
}
