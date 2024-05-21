CREATE TABLE IF NOT EXISTS course
(
    credits SMALLINT     NOT NULL,
    id      VARCHAR(255) NOT NULL,
    name    VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS faculty_course
(
    course_id  VARCHAR(255) NOT NULL,
    faculty_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS faculty
(
    id           VARCHAR(255) NOT NULL,
    institute_id VARCHAR(255),
    name         VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS groups
(
    id   VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS group_course
(
    course_id VARCHAR(255) NOT NULL,
    group_id  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS institute
(
    id   VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS lesson
(
    group_id   VARCHAR(255),
    id         VARCHAR(255) NOT NULL,
    location   VARCHAR(255),
    name       VARCHAR(255),
    teacher_id VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mark
(
    mark_value SMALLINT     NOT NULL,
    feedback   VARCHAR(255),
    id         VARCHAR(255) NOT NULL,
    student_id VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS student
(
    first_name VARCHAR(255),
    group_id   VARCHAR(255),
    id         VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255),
    login      VARCHAR(255),
    password   VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS task
(
    is_compulsory BOOLEAN      NOT NULL,
    deadline      TIMESTAMP,
    course_id     VARCHAR(255),
    description   VARCHAR(255),
    id            VARCHAR(255) NOT NULL,
    mark_id       VARCHAR(255) UNIQUE,
    status        VARCHAR(255) CHECK (status IN ('NOT_COMPLETED', 'UNDER_REVIEW', 'REVIEWED')),
    student_id    VARCHAR(255),
    title         VARCHAR(255),
    user_answer   VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teacher
(
    first_name VARCHAR(255),
    id         VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255),
    login      VARCHAR(255),
    password   VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teacher_course
(
    course_id  VARCHAR(255) NOT NULL,
    teacher_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS timeslot
(
    local_date DATE,
    end_time   TIMESTAMP,
    start_time TIMESTAMP,
    id         VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS timetable
(
    group_id VARCHAR(255) UNIQUE,
    id       VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS timetable_entry
(
    id           VARCHAR(255) NOT NULL,
    lesson_id    VARCHAR(255),
    timeslot_id  VARCHAR(255) UNIQUE,
    timetable_id VARCHAR(255),
    PRIMARY KEY (id)
);
