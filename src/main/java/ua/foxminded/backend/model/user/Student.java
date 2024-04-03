package ua.foxminded.backend.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.course.Group;
import ua.foxminded.backend.model.course.Task;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    @Id
    private String id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
    private List<Task> tasks;

    public Student(Group group, List<Task> tasks) {
        this(UUID.randomUUID().toString(), group, tasks);
    }
}
