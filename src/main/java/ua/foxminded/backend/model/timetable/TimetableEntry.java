package ua.foxminded.backend.model.timetable;

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
import ua.foxminded.backend.model.course.Lesson;

import java.util.UUID;

@Entity
@Table(name = "timetable_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetableEntry {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    public TimetableEntry(Lesson lesson, Timeslot timeslot, Timetable timetable) {
        this(UUID.randomUUID().toString(), lesson, timeslot, timetable);
    }
}
