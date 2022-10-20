package presentation.javafx.model;

import model.Document;
import model.NamedTopic;
import model.Topic;

public class LiteratureItemPresentation {
    private String id;
    private String name;
    private Type type;
    private boolean created;

    public enum Type {
        TOPIC,
        DOCUMENT
    }

    public LiteratureItemPresentation(String id, String name, Type type, boolean created) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isTopic() {
        return type.equals(Type.TOPIC);
    }

    public boolean isDocument() {
        return type.equals(Type.DOCUMENT);
    }

    public boolean isCreated() {
        return created;
    }

    public static LiteratureItemPresentation fromDocument(Document document) {
        return new LiteratureItemPresentation(document.getId().toString(), document.getTitle(), Type.DOCUMENT, true);
    }

    public static LiteratureItemPresentation fromTopic(Topic topic) {
        if (topic instanceof NamedTopic)
            return new LiteratureItemPresentation(topic.getId().toString(), ((NamedTopic) topic).getName(), Type.TOPIC, true);
        return null;
    }

    public static LiteratureItemPresentation uncreatedFromDocumentTitle(String title) {
        return new LiteratureItemPresentation(null, title, Type.DOCUMENT, false);
    }

    public static LiteratureItemPresentation uncreatedFromTopicName(String name) {
        return new LiteratureItemPresentation(null, name, Type.TOPIC, false);
    }
}
