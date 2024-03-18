package ua.foxminded.backend.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCharacteristics {
    private String title;
    private String description;
    private boolean isCompulsory;
    private LocalDateTime deadline;
    private String userAnswer;
    private TaskStatus status;
}
