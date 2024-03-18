package ua.foxminded.backend.model.institution;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import ua.foxminded.backend.model.course.Course;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Faculty {
    @Id
    private String id;

    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "courses_faculties",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id")
    )
    private List<Course> courses;

    public Faculty(String name, Institute institute, List<Course> courses) {
        this(UUID.randomUUID().toString(), name, institute, courses);
    }
}
