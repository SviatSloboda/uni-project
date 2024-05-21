-- Insert Institutes
INSERT INTO institute (id, name) VALUES ('institute_1', 'Institute of Science');
INSERT INTO institute (id, name) VALUES ('institute_2', 'Institute of Arts');
INSERT INTO institute (id, name) VALUES ('institute_3', 'Institute of Technology');
INSERT INTO institute (id, name) VALUES ('institute_4', 'Institute of Humanities');
INSERT INTO institute (id, name) VALUES ('institute_5', 'Institute of Health Sciences');

-- Insert Faculties
INSERT INTO faculty (id, name, institute_id) VALUES ('faculty_1', 'Faculty of Computer Science', 'institute_3');
INSERT INTO faculty (id, name, institute_id) VALUES ('faculty_2', 'Faculty of Mechanical Engineering', 'institute_3');
INSERT INTO faculty (id, name, institute_id) VALUES ('faculty_3', 'Faculty of Fine Arts', 'institute_2');
INSERT INTO faculty (id, name, institute_id) VALUES ('faculty_4', 'Faculty of Biology', 'institute_1');
INSERT INTO faculty (id, name, institute_id) VALUES ('faculty_5', 'Faculty of Medicine', 'institute_5');

-- Insert Courses
INSERT INTO course (id, name, credits) VALUES ('course_1', 'Algorithms', 3);
INSERT INTO course (id, name, credits) VALUES ('course_2', 'Data Structures', 4);
INSERT INTO course (id, name, credits) VALUES ('course_3', 'Painting', 2);
INSERT INTO course (id, name, credits) VALUES ('course_4', 'Sculpture', 3);
INSERT INTO course (id, name, credits) VALUES ('course_5', 'Anatomy', 4);

-- Insert Groups
INSERT INTO groups (id, name) VALUES ('group_1', 'Group A');
INSERT INTO groups (id, name) VALUES ('group_2', 'Group B');
INSERT INTO groups (id, name) VALUES ('group_3', 'Group C');
INSERT INTO groups (id, name) VALUES ('group_4', 'Group D');
INSERT INTO groups (id, name) VALUES ('group_5', 'Group E');

-- Insert Teachers
INSERT INTO teacher (id, first_name, last_name, login, password) VALUES ('1', 'John', 'Doe', 'johndoe', 'pass1');
INSERT INTO teacher (id, first_name, last_name, login, password) VALUES ('2', 'Jane', 'Doe', 'janedoe', 'pass2');
INSERT INTO teacher (id, first_name, last_name, login, password) VALUES ('3', 'Jim', 'Beam', 'jimbeam', 'pass3');
INSERT INTO teacher (id, first_name, last_name, login, password) VALUES ('4', 'Jack', 'Daniels', 'jackdaniels', 'pass4');
INSERT INTO teacher (id, first_name, last_name, login, password) VALUES ('5', 'Johnny', 'Walker', 'johnnywalker', 'pass5');

-- Insert Students
INSERT INTO student (id, first_name, last_name, login, password, group_id) VALUES ('1', 'Alice', 'Wonderland', 'alice', 'alicepass', '1');
INSERT INTO student (id, first_name, last_name, login, password, group_id) VALUES ('2', 'Bob', 'Builder', 'bob', 'bobpass', '2');
INSERT INTO student (id, first_name, last_name, login, password, group_id) VALUES ('3', 'Charlie', 'Chocolate', 'charlie', 'charliepass', '3');
INSERT INTO student (id, first_name, last_name, login, password, group_id) VALUES ('4', 'David', 'Duchovny', 'david', 'davidpass', '4');
INSERT INTO student (id, first_name, last_name, login, password, group_id) VALUES ('5', 'Eve', 'Online', 'eve', 'evepass', '5');

-- Courses and Faculties Relationship
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('course_1', 'faculty_1');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('course_2', 'faculty_1');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('course_3', 'faculty_3');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('course_4', 'faculty_3');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('course_5', 'faculty_5');

-- Courses and Groups Relationship
INSERT INTO group_course (group_id, course_id) VALUES ('group_1', 'course_1');
INSERT INTO group_course (group_id, course_id) VALUES ('group_2', 'course_2');
INSERT INTO group_course (group_id, course_id) VALUES ('group_3', 'course_3');
INSERT INTO group_course (group_id, course_id) VALUES ('group_4', 'course_4');
INSERT INTO group_course (group_id, course_id) VALUES ('group_5', 'course_5');

-- Courses and Teachers Relationship
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('teacher_1', 'course_1');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('teacher_2', 'course_2');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('teacher_3', 'course_3');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('teacher_4', 'course_4');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('teacher_5', 'course_5');

-- Timetable
INSERT INTO timetable (id, group_id) VALUES ('timetable_1', 'group_1');
INSERT INTO timetable (id, group_id) VALUES ('timetable_2', 'group_2');
INSERT INTO timetable (id, group_id) VALUES ('timetable_3', 'group_3');
INSERT INTO timetable (id, group_id) VALUES ('timetable_4', 'group_4');
INSERT INTO timetable (id, group_id) VALUES ('timetable_5', 'group_5');

-- Timeslots
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_1', '2023-09-01 08:00:00', '2023-09-01 10:00:00', '2023-09-01');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_2', '2023-09-01 10:00:00', '2023-09-04 12:00:00', '2023-09-01');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_3', '2023-09-02 08:00:00', '2023-09-04 10:00:00', '2023-09-02');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_4', '2023-09-02 10:00:00', '2023-09-25 12:00:00', '2023-09-02');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_5', '2023-09-03 08:00:00', '2023-09-25 10:00:00', '2023-09-03');

-- Lessons
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_1', 'Introduction to Algorithms', 'Room 101', 'teacher_1', 'group_1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_2', 'Advanced Data Structures', 'Room 102', 'teacher_2', 'group_2');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_3', 'Mastering Painting', 'Art Studio 1', 'teacher_3', 'group_3');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_4', 'Sculpture Techniques', 'Art Studio 2', 'teacher_4', 'group_4');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_5', 'Human Anatomy for Artists', 'Lab 1', 'teacher_5', 'group_5');

-- Marks
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('mark_1', 85, 'Excellent work!', 'student_1');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('mark_2', 75, 'Good effort, but there is room for improvement.', 'student_2');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('mark_3', 65, 'Please review the last chapter again.', 'student_3');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('mark_4', 95, 'Outstanding performance!', 'student_4');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('mark_5', 55, 'Needs more work, please see me.', 'student_5');

-- Tasks
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('task_1', 'Algo Assignment 1', 'Solve problems 1 to 10', TRUE, '2023-10-01 23:59:59', 'UNDER_REVIEW', 'course_1', 'student_1', 'mark_1');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('task_2', 'Data Structure Quiz', 'Complete the quiz online', TRUE, '2023-10-05 23:59:59', 'REVIEWED', 'course_2', 'student_2', 'mark_2');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('task_3', 'Painting: Landscape', 'Create a landscape painting', TRUE, '2023-10-10 23:59:59', 'NOT_COMPLETED', 'course_3', 'student_3', 'mark_3');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('task_4', 'Sculpture Project', 'Sculpt a figure from clay', TRUE, '2023-10-15 23:59:59', 'UNDER_REVIEW', 'course_4', 'student_4', 'mark_4');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('task_5', 'Anatomy Final Exam', 'Complete the final exam on human anatomy', TRUE, '2023-10-20 23:59:59', 'REVIEWED', 'course_5', 'student_5', 'mark_5');

-- Timetable Entries
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_1', 'lesson_1', 'timeslot_1', 'timetable_1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_2', 'lesson_2', 'timeslot_2', 'timetable_2');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_3', 'lesson_3', 'timeslot_3', 'timetable_3');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_4', 'lesson_4', 'timeslot_4', 'timetable_4');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_5', 'lesson_5', 'timeslot_5', 'timetable_5');

-- Additional Timeslots
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_6', '2023-09-01 12:00:00', '2023-09-01 14:00:00', '2023-09-01');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_7', '2023-09-02 08:00:00', '2023-09-02 10:00:00', '2023-09-02');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_8', '2023-09-05 10:00:00', '2023-09-05 12:00:00', '2023-09-05');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_9', '2023-09-05 08:00:00', '2023-09-05 10:00:00', '2023-09-05');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('timeslot_10', '2023-09-23 10:00:00', '2023-09-23 12:00:00', '2023-09-23');

-- Additional Lessons
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_6', 'Computational Theory', 'Room 103', 'teacher_1', 'group_1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_7', 'Quantum Computing', 'Room 104', 'teacher_2', 'group_1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_8', 'Modern Art Concepts', 'Art Studio 3', 'teacher_3', 'group_1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_9', 'Digital Sculpting', 'Art Studio 4', 'teacher_4', 'group_1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('lesson_10', 'Biomedical Engineering', 'Lab 2', 'teacher_5', 'group_1');

-- Additional Timetable Entries
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_6', 'lesson_6', 'timeslot_6', 'timetable_1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_7', 'lesson_7', 'timeslot_7', 'timetable_1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_8', 'lesson_8', 'timeslot_8', 'timetable_1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_9', 'lesson_9', 'timeslot_9', 'timetable_1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('timetable_entry_10', 'lesson_10', 'timeslot_10', 'timetable_1');
