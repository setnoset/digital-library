package presentation.javafx.model;

import appservice.TopicUpdateService;

public class EditTopicOperation implements LiteratureEditOperation {
    TopicUpdateService topicUpdateService;
    LiteratureItemPresentation topic;

    public EditTopicOperation(LiteratureItemPresentation topic, TopicUpdateService topicUpdateService) {
        this.topic = topic;
        this.topicUpdateService = topicUpdateService;
    }

    @Override
    public void performEdit(String newName) {
        topicUpdateService.updateTopicName(topic.getName(), newName);
        topic = LiteratureItemPresentation.fromTopic(topicUpdateService.getLastTopicUpdated());
    }

    @Override
    public LiteratureItemPresentation getResult() {
        return topic;
    }
}
