package presentation.javafx;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import presentation.javafx.model.LiteratureEditOperation;
import presentation.javafx.model.LiteratureItemPresentation;

public class MappedLiteratureView extends TreeView<LiteratureItemPresentation> {
    private LiteratureEditOperation editOperation;

    public MappedLiteratureView(TreeItem<LiteratureItemPresentation> root) {
        super(root);
    }

    public void setEditOperation(LiteratureEditOperation editOperation) {
        this.editOperation = editOperation;
    }

    public LiteratureEditOperation getEditOperation() {
        return editOperation;
    }
}
