package presentation.javafx;

import model.Document;
import model.Topic;
import model.TopicHierarchy;
import model.NamedTopic;
import presentation.javafx.model.LiteratureItemPresentation;

import java.util.Set;

public class TopicItemFactory {
    TopicHierarchy topicHierarchy;

    public TopicItemFactory(TopicHierarchy topicHierarchy) {
        this.topicHierarchy = topicHierarchy;
    }

    public LiteratureItem create(Topic topic) {
        Set<Topic> subtopics = topicHierarchy.getDirectSubtopics(topic);
        Set<Document> literature = topic.getBaseLiterature();

        LiteratureItem topicItem = new LiteratureItem(LiteratureItemPresentation.fromTopic(topic));
        for (Document document : literature)
            topicItem.getChildren().add(new LiteratureItem(LiteratureItemPresentation.fromDocument(document)));
        for (Topic subtopic : subtopics)
            topicItem.getChildren().add(create(subtopic));

        return topicItem;
    }
}
