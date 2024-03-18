package ua.foxminded.backend.model.timetable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
    @Id
    private String id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate localDate;

    public Timeslot(LocalDateTime startTime, LocalDateTime endTime, LocalDate localDate) {
        this(UUID.randomUUID().toString(), startTime, endTime, localDate);
    }
}
