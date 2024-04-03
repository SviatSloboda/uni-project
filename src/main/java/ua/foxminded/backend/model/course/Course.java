package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.model.user.Teacher;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private byte credits;

    @ManyToMany(mappedBy = "courses")
    private List<Faculty> faculties;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "group_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "course")
    private List<Task> tasks;

    public Course(String name, byte credits, List<Faculty> faculties, List<Group> groups, List<Teacher> teachers, List<Task> tasks) {
        this(UUID.randomUUID().toString(), name, credits, faculties, groups, teachers, tasks);
    }
}
