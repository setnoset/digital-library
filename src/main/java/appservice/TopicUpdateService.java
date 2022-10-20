package appservice;

import model.NamedTopic;
import model.TopicFactory;
import model.TopicRepository;

import java.util.NoSuchElementException;

public class TopicUpdateService {
    private TopicRepository topicRepository;
    private NamedTopic lastTopicUpdated;

    public TopicUpdateService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void updateTopicName(String oldName, String newName) {
        NamedTopic oldTopic = topicRepository.findTopicByName(oldName).orElseThrow(() -> new NoSuchElementException("Topic '%s' not found".formatted(oldName)));
        NamedTopic topic = new TopicFactory(() -> oldTopic.getId()).createNamed(newName);
        topicRepository.saveTopic(topic);
        lastTopicUpdated = topic;
    }

    public NamedTopic getLastTopicUpdated() {
        return lastTopicUpdated;
    }
}
