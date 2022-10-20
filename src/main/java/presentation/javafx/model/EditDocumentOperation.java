package presentation.javafx.model;

import appservice.DocumentUpdateService;

public class EditDocumentOperation implements LiteratureEditOperation {
    DocumentUpdateService documentUpdateService;
    LiteratureItemPresentation document;

    public EditDocumentOperation(LiteratureItemPresentation document, DocumentUpdateService documentUpdateService) {
        this.document = document;
        this.documentUpdateService = documentUpdateService;
    }

    @Override
    public void performEdit(String newTitle) {
        documentUpdateService.updateTitle(Long.valueOf(document.getId()), newTitle);
        document = LiteratureItemPresentation.fromDocument(documentUpdateService.getLastDocumentUpdated());
    }

    @Override
    public LiteratureItemPresentation getResult() {
        return document;
    }
}
