CREATE TABLE instructors (
  id STRING(36) NOT NULL,
  first_name STRING(100),
  last_name STRING(100),
  enroll_date TIMESTAMP,
  rating INT64,
) PRIMARY KEY(id);

CREATE TABLE students (
  id STRING(36) NOT NULL,
  first_name STRING(100),
  last_name STRING(100),
  location STRING(100),
  enroll_date TIMESTAMP,
  email_id STRING(100),
) PRIMARY KEY(id);

CREATE INDEX GetStudentsByLocation ON students(location);

CREATE TABLE courses (
    course_id STRING(36) NOT NULL,
    instructor_id STRING(36) NOT NULL,
    course_name STRING(100),
    course_duration INT64 NOT NULL,
    category STRING(100),
    CONSTRAINT instructors_courses_fk1 FOREIGN KEY(instructor_id) REFERENCES instructors(id),
) PRIMARY KEY(course_id);

CREATE INDEX GetCourseByInstructor ON courses(instructor_id);
CREATE INDEX GetCourseByCategory ON courses(category);

CREATE TABLE student_enrolled_courses (
    enrollment_id STRING(36) NOT NULL,
    student_id STRING(36) NOT NULL,
    course_id STRING(36) NOT NULL,
    enrollment_date TIMESTAMP,
    CONSTRAINT students_enrolled_courses_fk1 FOREIGN KEY(student_id) REFERENCES students(id),
    CONSTRAINT courses_fk1 FOREIGN KEY(course_id) REFERENCES courses(course_id),
) PRIMARY KEY(enrollment_id);

CREATE INDEX GetCoursesForStudentID ON student_enrolled_courses(student_id);
CREATE INDEX GetStudentsForEnrolledCourse ON student_enrolled_courses(course_id);