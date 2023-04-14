package za.co.bank.x.interview.services.impl.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class AbstractAuditEntity {
    private static final long serialVersionUID = 8412567098449432389L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(50)")
    private String id;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractAuditEntity that = (AbstractAuditEntity) obj;

        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public String toString() {
        try {
            return Jackson2ObjectMapperBuilder.json().build().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}


