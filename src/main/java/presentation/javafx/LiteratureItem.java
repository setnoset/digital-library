package presentation.javafx;

import javafx.scene.control.TreeItem;
import presentation.javafx.model.LiteratureItemPresentation;

public class LiteratureItem extends TreeItem<LiteratureItemPresentation> {
    public LiteratureItem(LiteratureItemPresentation literatureItemPresentation) {
        super(literatureItemPresentation);
    }

    @Override
    public boolean isLeaf() {
        return getValue().isDocument();
    }
}
