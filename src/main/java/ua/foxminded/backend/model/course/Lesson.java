package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.timetable.TimetableEntry;
import ua.foxminded.backend.model.user.Teacher;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "lesson")
    private List<TimetableEntry> timetableEntries;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private Group group;

    @PrePersist
    private void prePersist() {
        if (this.id == null || this.id.trim().isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public Lesson(String name, String location, List<TimetableEntry> timetableEntries, Teacher teacher, Group group) {
        this(UUID.randomUUID().toString(), name, location, timetableEntries, teacher, group);
    }
}
