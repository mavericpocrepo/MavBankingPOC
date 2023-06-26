package com.example.demo.repository;

import com.example.demo.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.activation.CommandMap;

public interface CommentsRepository extends JpaRepository<Comments,String> {
}
