package ua.foxminded.backend.model.course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.foxminded.backend.model.user.Student;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean isCompulsory;
    private LocalDateTime deadline;
    private String userAnswer;

    @Enumerated(EnumType.STRING)
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
