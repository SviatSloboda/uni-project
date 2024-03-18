package ua.foxminded.backend.model.institution;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Institute {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "institute")
    private List<Faculty> faculties;

    public Institute(String name, List<Faculty> faculties) {
        this(UUID.randomUUID().toString(), name, faculties);
    }
}
