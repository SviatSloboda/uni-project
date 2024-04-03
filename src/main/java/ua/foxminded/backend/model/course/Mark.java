package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.user.Student;

import java.util.UUID;

@Entity
@Table(name = "mark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "mark_value")
    private byte markValue;

    @Column(name = "feedback")
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
