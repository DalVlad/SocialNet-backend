package com.practice.SocialNetback.controller;

import com.practice.SocialNetback.models.Chat;
import com.practice.SocialNetback.models.Messages;
import com.practice.SocialNetback.models.User;
import com.practice.SocialNetback.queris.response.MessageInfoResponse;
import com.practice.SocialNetback.queris.response.MessageResponse;
import com.practice.SocialNetback.repository.ChatsRepository;
import com.practice.SocialNetback.repository.MessagesRepository;
import com.practice.SocialNetback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/chats/{idChat}")
public class MessagesController {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatsRepository chatsRepository;

    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    @GetMapping("/messages")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<MessageInfoResponse>> getMessagesByChatId(@PathVariable("idChat") long id, @RequestParam(required = false) String messages) {
        Optional<Chat> chatData = chatsRepository.findById(id);
        if (chatData.isPresent()) {
            List<Messages> messagesList = new ArrayList<>();
            List<MessageInfoResponse> messagesInfoList = new ArrayList<>();
            List<MessageInfoResponse> foundMessages = new ArrayList<>();
            chatData.get().getMessagesList().forEach(messagesList::add);

            for(Messages message: messagesList) {
                var messageInfo = new MessageInfoResponse(
                        message.getId(),
                        message.getMessage(),
                        message.getDateOfDispatch(),
                        message.getUser().getId(),
                        message.getUser().getUsername());

                messagesInfoList.add(messageInfo);
            }
            if (messagesList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            if (messages != null) {
                for(MessageInfoResponse message: messagesInfoList) {
                    if (message.getMessage().contains(messages))
                        foundMessages.add(message);
                }
            }

            if (!foundMessages.isEmpty())
                return new ResponseEntity<>(foundMessages.stream().sorted((a1, b1) -> Long.compare(a1.getId(), b1.getId())).toList(), HttpStatus.OK);

            return new ResponseEntity<>(messagesInfoList.stream().sorted((a1, b1) -> Long.compare(a1.getId(), b1.getId())).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/messages/{idMess}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getMessageById(@PathVariable("idMess") long id) {
        Optional<Messages> messagesData = messagesRepository.findById(id);

        if (messagesData.isPresent()) {
            return ResponseEntity.ok()
                    .body(new MessageInfoResponse(messagesData.get().getId(),
                            messagesData.get().getMessage(),
                            messagesData.get().getDateOfDispatch(),
                            messagesData.get().getUser().getId(),
                            messagesData.get().getUser().getUsername()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/message")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Messages> createMessage(@PathVariable("idChat") long id, @RequestBody Messages messages) {
        try {
            User user = userRepository.findByUsername(getCurrentUserName())
                    .orElseThrow(() -> new RuntimeException("Error: User is not found."));

            Chat chat = chatsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: chat is not found."));

            Messages _messages = messagesRepository
                    .save(new Messages(messages.getMessage(), new Date(), chat, user, false));

            return new ResponseEntity<>(_messages, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/messages/{idMess}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Messages> updateMessage(@PathVariable("idMess") long idMess, @RequestBody Messages messages) {
        Optional<Messages> messageData = messagesRepository.findById(idMess);

        User user = userRepository.findByUsername(getCurrentUserName())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));

        if (messageData.isPresent()) {
            Messages _messages = messageData.get();
            if (user.getId() == _messages.getUser().getId()) {
                _messages.setMessage(messages.getMessage());
                return new ResponseEntity<>(messagesRepository.save(_messages), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("messages/likeMess/{idMess}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Messages> likeMessage(@PathVariable("idMess") long idMess) {
        Optional<Messages> messageData = messagesRepository.findById(idMess);
        if (messageData.isPresent()) {
            Messages _messages = messageData.get();
            _messages.setLike(!messageData.get().isLike());
            return new ResponseEntity<>(messagesRepository.save(_messages), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/messages/{idMess}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteMessage(@PathVariable("idMess") long idMess) {
        Optional<Messages> messageData = messagesRepository.findById(idMess);

        User user = userRepository.findByUsername(getCurrentUserName())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));

        try {
            if (messageData.isPresent()) {
                if(user.getId() == messageData.get().getUser().getId()) {
                    messagesRepository.deleteById(idMess);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return ResponseEntity.ok(new MessageResponse("no access to this function"));
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/messages")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllMessages() {
        try {
            messagesRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
