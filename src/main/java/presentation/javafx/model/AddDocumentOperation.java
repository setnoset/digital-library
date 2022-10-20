package presentation.javafx.model;

import appservice.DocumentRegistrationService;

public class AddDocumentOperation implements LiteratureEditOperation {
    DocumentRegistrationService documentRegistrationService;
    LiteratureItemPresentation parentTopic;
    LiteratureItemPresentation document;

    public AddDocumentOperation(LiteratureItemPresentation parentTopic, DocumentRegistrationService documentRegistrationService) {
        this.parentTopic = parentTopic;
        this.documentRegistrationService = documentRegistrationService;
    }

    @Override
    public void performEdit(String title) {
        documentRegistrationService.registerDocument(title, parentTopic.getName());
        document = LiteratureItemPresentation.fromDocument(documentRegistrationService.getLastDocumentRegistered());
    }

    @Override
    public LiteratureItemPresentation getResult() {
        return document;
    }
}
