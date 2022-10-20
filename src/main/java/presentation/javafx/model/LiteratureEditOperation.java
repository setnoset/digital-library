package presentation.javafx.model;

import presentation.javafx.model.LiteratureItemPresentation;

public interface LiteratureEditOperation {
    void performEdit(String newItemName);

    LiteratureItemPresentation getResult();
}
