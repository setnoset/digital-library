package model;

import java.util.Optional;

public interface TopicRepository {

    void saveTopic(Topic topic);

    Optional<NamedTopic> findTopicByName(String name);
}
