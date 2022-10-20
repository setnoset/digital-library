package presentation.javafx.model;

import appservice.TopicRegistrationService;

public class AddTopicOperation implements LiteratureEditOperation {
    TopicRegistrationService topicRegistrationService;
    LiteratureItemPresentation parentTopic;
    LiteratureItemPresentation topic;

    public AddTopicOperation(LiteratureItemPresentation parentTopic, TopicRegistrationService topicRegistrationService) {
        this.parentTopic = parentTopic;
        this.topicRegistrationService = topicRegistrationService;
    }

    @Override
    public void performEdit(String topicName) {
        topicRegistrationService.registerTopic(topicName, parentTopic.getName());
        topic = LiteratureItemPresentation.fromTopic(topicRegistrationService.getLastTopicRegistered());
    }

    @Override
    public LiteratureItemPresentation getResult() {
        return topic;
    }
}
