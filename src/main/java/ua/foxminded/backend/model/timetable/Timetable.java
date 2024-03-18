package ua.foxminded.backend.model.timetable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import ua.foxminded.backend.model.course.Group;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Timetable {
    @Id
    private String id;

    @OneToMany(mappedBy = "timetable")
    private List<TimetableEntry> entries;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "group_id")
    private Group group;

    public Timetable(List<TimetableEntry> entries, Group group) {
        this(UUID.randomUUID().toString(), entries, group);
    }
}
