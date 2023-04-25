package com.spring.security.auth.repository.redis;

import com.spring.security.auth.entity.redis.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {}
