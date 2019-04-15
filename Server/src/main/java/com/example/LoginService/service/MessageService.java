package com.example.LoginService.service;

import com.example.LoginService.common.IMessageService;
import com.example.LoginService.common.MessageRepository;
import com.example.LoginService.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository repository;

    @Override
    public ResponseEntity<Message> sendMessage(Message message) {

        if(repository.findByid(message.getId()) == null) {
            repository.save(message);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Message>> getPendingMessages(User user) {
        return null;
    }
}
