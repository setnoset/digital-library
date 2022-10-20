package infrastructure.mock;

import model.NamedTopic;
import model.Topic;
import model.TopicRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TopicRepositoryMock implements TopicRepository {
    Set<Topic> topics = new HashSet<>();

    @Override
    public void saveTopic(Topic topic) {
        topics.remove(topic);
        topics.add(topic);
    }

    @Override
    public Optional<NamedTopic> findTopicByName(String name) {
        return topics.stream().filter(NamedTopic.class::isInstance).map(NamedTopic.class::cast).filter(t -> t.getName().equals(name)).findAny();
    }
}
