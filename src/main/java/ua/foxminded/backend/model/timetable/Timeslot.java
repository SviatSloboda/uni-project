package ua.foxminded.backend.model.timetable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "timeslot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "local_date")
    private LocalDate localDate;

    public Timeslot(LocalDateTime startTime, LocalDateTime endTime, LocalDate localDate) {
        this(UUID.randomUUID().toString(), startTime, endTime, localDate);
    }
}
