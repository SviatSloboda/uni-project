-- Insert Institutes
INSERT INTO institute (id, name) VALUES ('1', 'Institute of Science');
INSERT INTO institute (id, name) VALUES ('2', 'Institute of Arts');
INSERT INTO institute (id, name) VALUES ('3', 'Institute of Technology');
INSERT INTO institute (id, name) VALUES ('4', 'Institute of Humanities');
INSERT INTO institute (id, name) VALUES ('5', 'Institute of Health Sciences');

-- Insert Faculties
INSERT INTO faculty (id, name, institute_id) VALUES ('1', 'Faculty of Computer Science', '3');
INSERT INTO faculty (id, name, institute_id) VALUES ('2', 'Faculty of Mechanical Engineering', '3');
INSERT INTO faculty (id, name, institute_id) VALUES ('3', 'Faculty of Fine Arts', '2');
INSERT INTO faculty (id, name, institute_id) VALUES ('4', 'Faculty of Biology', '1');
INSERT INTO faculty (id, name, institute_id) VALUES ('5', 'Faculty of Medicine', '5');

-- Insert Courses
INSERT INTO course (id, name, credits) VALUES ('1', 'Algorithms', 3);
INSERT INTO course (id, name, credits) VALUES ('2', 'Data Structures', 4);
INSERT INTO course (id, name, credits) VALUES ('3', 'Painting', 2);
INSERT INTO course (id, name, credits) VALUES ('4', 'Sculpture', 3);
INSERT INTO course (id, name, credits) VALUES ('5', 'Anatomy', 4);

-- Insert Groups
INSERT INTO groups (id, name) VALUES ('1', 'Group A');
INSERT INTO groups (id, name) VALUES ('2', 'Group B');
INSERT INTO groups (id, name) VALUES ('3', 'Group C');
INSERT INTO groups (id, name) VALUES ('4', 'Group D');
INSERT INTO groups (id, name) VALUES ('5', 'Group E');

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
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('1', '1');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('2', '1');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('3', '3');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('4', '3');
INSERT INTO faculty_course (course_id, faculty_id) VALUES ('5', '5');

-- Courses and Groups Relationship
INSERT INTO group_course (group_id, course_id) VALUES ('1', '1');
INSERT INTO group_course (group_id, course_id) VALUES ('2', '2');
INSERT INTO group_course (group_id, course_id) VALUES ('3', '3');
INSERT INTO group_course (group_id, course_id) VALUES ('4', '4');
INSERT INTO group_course (group_id, course_id) VALUES ('5', '5');

-- Courses and Teachers Relationship
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('1', '1');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('2', '2');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('3', '3');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('4', '4');
INSERT INTO teacher_course (teacher_id, course_id) VALUES ('5', '5');

-- Timetable
INSERT INTO timetable (id, group_id) VALUES ('1', '1');
INSERT INTO timetable (id, group_id) VALUES ('2', '2');
INSERT INTO timetable (id, group_id) VALUES ('3', '3');
INSERT INTO timetable (id, group_id) VALUES ('4', '4');
INSERT INTO timetable (id, group_id) VALUES ('5', '5');

-- Timeslots
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('1', '2023-09-01 08:00:00', '2023-09-01 10:00:00', '2023-09-01');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('2', '2023-09-01 10:00:00', '2023-09-04 12:00:00', '2023-09-01');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('3', '2023-09-02 08:00:00', '2023-09-04 10:00:00', '2023-09-02');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('4', '2023-09-02 10:00:00', '2023-09-25 12:00:00', '2023-09-02');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('5', '2023-09-03 08:00:00', '2023-09-25 10:00:00', '2023-09-03');


-- Lessons
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('1', 'Introduction to Algorithms', 'Room 101', '1', '1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('2', 'Advanced Data Structures', 'Room 102', '2', '2');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('3', 'Mastering Painting', 'Art Studio 1', '3', '3');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('4', 'Sculpture Techniques', 'Art Studio 2', '4', '4');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('5', 'Human Anatomy for Artists', 'Lab 1', '5', '5');

-- Marks
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('1', 85, 'Excellent work!', '1');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('2', 75, 'Good effort, but there is room for improvement.', '2');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('3', 65, 'Please review the last chapter again.', '3');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('4', 95, 'Outstanding performance!', '4');
INSERT INTO mark (id, mark_value, feedback, student_id) VALUES ('5', 55, 'Needs more work, please see me.', '5');

-- Tasks
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('1', 'Algo Assignment 1', 'Solve problems 1 to 10', TRUE, '2023-10-01 23:59:59', 'UNDER_REVIEW', '1', '1', '1');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('2', 'Data Structure Quiz', 'Complete the quiz online', TRUE, '2023-10-05 23:59:59', 'REVIEWED', '2', '2', '2');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('3', 'Painting: Landscape', 'Create a landscape painting', TRUE, '2023-10-10 23:59:59', 'NOT_COMPLETED', '3', '3', '3');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('4', 'Sculpture Project', 'Sculpt a figure from clay', TRUE, '2023-10-15 23:59:59', 'UNDER_REVIEW', '4', '4', '4');
INSERT INTO task (id, title, description, is_compulsory, deadline, status, course_id, student_id, mark_id) VALUES ('5', 'Anatomy Final Exam', 'Complete the final exam on human anatomy', TRUE, '2023-10-20 23:59:59', 'REVIEWED', '5', '5', '5');

-- Timetable Entries
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('1', '1', '1', '1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('2', '2', '2', '2');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('3', '3', '3', '3');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('4', '4', '4', '4');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('5', '5', '5', '5');


-- Additional Timeslots
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('6', '2023-09-01 12:00:00', '2023-09-01 14:00:00', '2023-09-01');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('7', '2023-09-02 08:00:00', '2023-09-02 10:00:00', '2023-09-02');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('8', '2023-09-05 10:00:00', '2023-09-05 12:00:00', '2023-09-05');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('9', '2023-09-05 08:00:00', '2023-09-05 10:00:00', '2023-09-05');
INSERT INTO timeslot (id, start_time, end_time, local_date) VALUES ('10', '2023-09-23 10:00:00', '2023-09-23 12:00:00', '2023-09-23');

-- Additional Lessons
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('6', 'Computational Theory', 'Room 103', '1', '1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('7', 'Quantum Computing', 'Room 104', '2', '1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('8', 'Modern Art Concepts', 'Art Studio 3', '3', '1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('9', 'Digital Sculpting', 'Art Studio 4', '4', '1');
INSERT INTO lesson (id, name, location, teacher_id, group_id) VALUES ('10', 'Biomedical Engineering', 'Lab 2', '5', '1');

-- Additional Timetable Entries
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('6', '6', '6', '1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('7', '7', '7', '1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('8', '8', '8', '1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('9', '9', '9', '1');
INSERT INTO timetable_entry (id, lesson_id, timeslot_id, timetable_id) VALUES ('10', '10', '10', '1');
