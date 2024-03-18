package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.model.user.Teacher;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;

    private String name;

    private byte credits;

    @ManyToMany(mappedBy = "courses")
    private List<Faculty> faculties;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "groups_courses", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Group> groups;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "teachers_courses", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "course")
    private List<Task> tasks;

    public Course(String name, byte credits, List<Faculty> faculties, List<Group> groups, List<Teacher> teachers, List<Task> tasks) {
        this(UUID.randomUUID().toString(), name, credits, faculties, groups, teachers, tasks);
    }
}
