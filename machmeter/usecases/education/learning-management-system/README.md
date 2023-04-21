### Education Learning Management System (LMS) template

Implement a learning management system comprising of three primary entities `instructors`, `students`, `courses`. Instructors are allowed to create courses and students are allowed to enroll for courses. There is another entity `enrolled_courses` which is a trnasaction binding the three primary entities. Students can enroll for courses created by instructors.

The usecase makes use of a variety of read-write query patterns to evaluate performance. For reads we have standards reads, stale reads and indexed reads. For writes we have standard write and wrties using mutations.
