package com.nttdata.esercitazione.repository;

import com.nttdata.esercitazione.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
