package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.user.Student;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Id
    private String id;
    private byte markValue;
    private String feedback;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(mappedBy = "mark")
    private Task task;

    public Mark(byte markValue, String feedback, Student student, Task task) {
        this(UUID.randomUUID().toString(), markValue, feedback, student, task);
    }
}
