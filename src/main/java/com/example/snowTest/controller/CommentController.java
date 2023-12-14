package com.example.snowTest.controller;

import com.example.snowTest.dto.CommentRequest;
import com.example.snowTest.model.Comment;
import com.example.snowTest.model.Person;
import com.example.snowTest.repository.CommentRepository;
import com.example.snowTest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PersonRepository personRepo;

    @GetMapping("/comment")
    public ResponseEntity<List<Comment>> getAllComments() {
        try {
            List<Comment> commentList = new ArrayList<>();
            commentRepo.findAll().forEach(commentList::add);

            if (commentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        Optional<Comment> commentData = commentRepo.findById(id);

        if(commentData.isPresent()) {
            return new ResponseEntity<>(commentData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Hibernate knew that I wanted to search the models by foreign keys
    @GetMapping("/person/{id}/comment")
    public ResponseEntity<List<Comment>> getCommentByPersonId(@PathVariable String id) {
        try {
            List<Comment> commentList = commentRepo.findByPersonId(id);

            if (commentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest commentRequest) {
        Optional<Person> personData = personRepo.findById(commentRequest.getPersonId());

        if (personData.isPresent()) {
            Person existingPerson = personData.get();

            Comment newComment = new Comment();
            String uuid = UUID.randomUUID().toString();
            newComment.setId(uuid);
            newComment.setDescription(commentRequest.getDescription());
            newComment.setDateCreated(commentRequest.getDateCreated());
            newComment.setPerson(existingPerson);

            return new ResponseEntity<>(commentRepo.save(newComment), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<Comment> putComment(@PathVariable String id, @RequestBody CommentRequest commentRequest) {
        Optional<Comment> commentData = commentRepo.findById(id);

        if(commentData.isPresent() ) {
            Comment existingComment = commentData.get();
            // you need to get the existing person but the optional might not be needed here
            Person existingPerson = existingComment.getPerson();

            if (commentRequest.getDescription() != null) {
                existingComment.setDescription(commentRequest.getDescription());
            }

            if (commentRequest.getDateCreated() != null) {
                existingComment.setDateCreated(commentRequest.getDateCreated());
            }

            if (commentRequest.getPersonId() != null) {
                existingComment.setPerson(existingPerson);
            }
            return new ResponseEntity<>(commentRepo.save(existingComment), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Comment> removeComment(@PathVariable String id) {
        Optional<Comment> commentData = commentRepo.findById(id);

        if(commentData.isPresent()) {
            Comment deletedComment = commentData.get();
            commentRepo.delete(deletedComment);
            return new ResponseEntity<>(deletedComment, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
