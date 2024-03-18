package ua.foxminded.backend.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.course.Course;
import ua.foxminded.backend.model.course.Lesson;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {
    @Id
    private String id;

    @ManyToMany(mappedBy = "teachers")
    private List<Course> courses;

    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessons;

    public Teacher(List<Course> courses, List<Lesson> lessons) {
        this(UUID.randomUUID().toString(), courses, lessons);
    }

    @PrePersist
    private void prePersist() {
        if (this.id == null || this.id.trim().isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
