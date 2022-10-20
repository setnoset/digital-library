package appservice;

import model.*;

import java.util.NoSuchElementException;

public class TopicRegistrationService {
    private TopicRepository topicRepository;
    private TopicHierarchy topicHierarchy;
    private TopicFactory topicFactory;
    private NamedTopic lastTopicRegistered;

    public TopicRegistrationService(TopicRepository topicRepository, TopicHierarchy topicHierarchy, TopicFactory topicFactory) {
        this.topicRepository = topicRepository;
        this.topicHierarchy = topicHierarchy;
        this.topicFactory = topicFactory;
    }

    public void registerTopic(String topicName, String parentTopicName) {
        NamedTopic topic = topicFactory.createNamed(topicName);
        topicRepository.saveTopic(topic);

        if (parentTopicName != null) {
            NamedTopic parentTopic = topicRepository.findTopicByName(parentTopicName).orElseThrow(() -> new NoSuchElementException("Topic '%s' not found".formatted(parentTopicName)));
            parentTopic.addSubtopic(topic, topicHierarchy);
        }

        lastTopicRegistered = topic;
    }

    public NamedTopic getLastTopicRegistered() {
        return lastTopicRegistered;
    }
}
