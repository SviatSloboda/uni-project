package ua.foxminded.backend.model.course;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.timetable.Timetable;
import ua.foxminded.backend.model.user.Student;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @ManyToMany(mappedBy = "groups")
    private List<Course> courses;

    @OneToMany(mappedBy = "group")
    private List<Lesson> lessons;

    @OneToOne(mappedBy = "group")
    private Timetable timetable;

    public Group(String name, List<Student> students, List<Course> courses, List<Lesson> lessons, Timetable timetable) {
        this(UUID.randomUUID().toString(), name, students, courses, lessons, timetable);
    }
}
