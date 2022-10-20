package infrastructure.hibernate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TOPICS")
public class TopicDAO {

    @Id
    private Long id;

    @NotNull
    private String name;

    public TopicDAO() {}

    public TopicDAO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
