package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_compulsory")
    private boolean isCompulsory;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "user_answer")
    private String userAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Mark mark;

    public Task(TaskCharacteristics taskCharacteristics, Course course, Student student, Mark mark) {
        this(
                UUID.randomUUID().toString(),
                taskCharacteristics.getTitle(),
                taskCharacteristics.getDescription(),
                taskCharacteristics.isCompulsory(),
                taskCharacteristics.getDeadline(),
                taskCharacteristics.getUserAnswer(),
                taskCharacteristics.getStatus(),
                course,
                student,
                mark
        );
    }
    public Task(String id, TaskCharacteristics taskCharacteristics, Course course, Student student, Mark mark) {
        this(
                id,
                taskCharacteristics.getTitle(),
                taskCharacteristics.getDescription(),
                taskCharacteristics.isCompulsory(),
                taskCharacteristics.getDeadline(),
                taskCharacteristics.getUserAnswer(),
                taskCharacteristics.getStatus(),
                course,
                student,
                mark
        );
    }
}
