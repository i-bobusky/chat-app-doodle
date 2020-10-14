package com.example.chatappdoodle.repository;

import com.example.chatappdoodle.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, String> {

    List<MessageEntity> findAll();
}