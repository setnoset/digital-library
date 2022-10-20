package infrastructure.hibernate.model;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DOCUMENTS")
public class DocumentDAO {

    @Id
    private Long id;

    @NotNull
    private String title;

    public DocumentDAO() {}

    public DocumentDAO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
