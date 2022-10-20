package presentation.javafx;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import presentation.javafx.model.LiteratureEditOperation;
import presentation.javafx.model.LiteratureEditOperationFactory;
import presentation.javafx.model.LiteratureItemPresentation;

public class LiteratureCell extends TreeCell<LiteratureItemPresentation> {
    private ContextMenu topicMenu = new ContextMenu();
    private TextField textField;

    public LiteratureCell(LiteratureEditOperationFactory editOperationFactory) {
        super();

        MenuItem addDocument = new MenuItem("Add Document");
        addDocument.setOnAction(e -> {
            LiteratureItem documentItem = new LiteratureItem(LiteratureItemPresentation.uncreatedFromDocumentTitle(""));
            getTreeItem().getChildren().add(documentItem);
            getTreeView().edit(documentItem);
            LiteratureEditOperation editOperation = editOperationFactory.createAddDocumentOperation(getItem());
            ((MappedLiteratureView) getTreeView()).setEditOperation(editOperation);
        });

        MenuItem addSubtopic = new MenuItem("Add Subtopic");
        topicMenu.getItems().addAll(addDocument, addSubtopic);


    }

    @Override
    public void startEdit() {
        super.startEdit();
        System.out.println(getItem().getName());

        if (textField == null)
            createTextField();
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem().getName());
        setGraphic(getTreeItem().getGraphic());

        if (!getItem().isCreated()) {
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        }
    }

    @Override
    protected void updateItem(LiteratureItemPresentation entity, boolean empty) {
        super.updateItem(entity, empty);

        if (entity == null || entity.getId() == null || entity.getName() == null)
            setText("Bug detected: Unexpected null object, name or id");
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }

        if (!isEditing()) {
            setText(entity.getName());

            if (entity.isTopic())
                setContextMenu(topicMenu);
            else
                setContextMenu(null);

            return;
        }

        textField.setText(entity.getName());
        setGraphic(textField);
    }

    private void createTextField() {
        textField = new TextField(getItem().getName());
        textField.setOnKeyReleased(t -> {
            if (t.getCode().equals(KeyCode.ENTER)) {
                LiteratureEditOperation editOperation = ((MappedLiteratureView) getTreeView()).getEditOperation();
                editOperation.performEdit(textField.getText());
                commitEdit(editOperation.getResult());
                textField = null;
            } else if (t.getCode().equals(KeyCode.ESCAPE))
                cancelEdit();
        });
    }

}
