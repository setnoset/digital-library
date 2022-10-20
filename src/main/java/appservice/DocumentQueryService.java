package appservice;

import model.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class DocumentQueryService {
    private TopicRepository topicRepository;
    private TopicHierarchy topicHierarchy;

    public DocumentQueryService(TopicRepository topicRepository, TopicHierarchy topicHierarchy) {
        this.topicRepository = topicRepository;
        this.topicHierarchy = topicHierarchy;
    }

    public Set<Document> getDocumentsRelevantToTopic(String topicName) {
        Optional<NamedTopic> topicOptional = topicRepository.findTopicByName(topicName);
        Topic topic = topicOptional.orElseThrow(() -> new NoSuchElementException("Topic '" + topicName + "' not found"));

        return topic.getBaseLiterature();
    }
}
