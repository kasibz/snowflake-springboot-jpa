package com.example.snowTest.repository;

import com.example.snowTest.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    // spring boot just knows what to look for if I name it correctly
    List<Comment> findByPersonId(String personId);
}
