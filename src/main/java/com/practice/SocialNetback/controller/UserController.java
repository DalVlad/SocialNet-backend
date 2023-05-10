package com.practice.SocialNetback.controller;

import com.practice.SocialNetback.models.Chat;
import com.practice.SocialNetback.models.Messages;
import com.practice.SocialNetback.models.User;
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

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    ChatsRepository chatsRepository;

    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = new ArrayList<>();

        User user = userRepository.findByUsername(getCurrentUserName())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));

        userRepository.findAll().forEach(userList::add);

        userList.remove(user);

        if (userList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{idMess}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<User> getUsername(@PathVariable("idMess") long id) {
        Optional <Messages> messData = messagesRepository.findById(id);

        if (messData.isPresent()) {
            User user = messData.get().getUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userInChat/{idChat}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsersByChatId(@PathVariable("idChat") long id) {
        Optional <Chat> chatData = chatsRepository.findById(id);

        if (chatData.isPresent()) {
            Set<User> users = new HashSet<>();
            chatData.get().getUsers().forEach(users::add);
            return new ResponseEntity<>(users.stream().sorted((a1, b1) -> Long.compare(a1.getId(), b1.getId())).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userOutChat/{idChat}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsersOutByChatId(@PathVariable("idChat") long id) {
        Optional <Chat> chatData = chatsRepository.findById(id);

        if (chatData.isPresent()) {
            Set<User> usersInChat = new HashSet<>();
            Set<User> usersOutChat = new HashSet<>();

            chatData.get().getUsers().forEach(usersInChat::add);
            userRepository.findAll().forEach(usersOutChat::add);

            for(var user: usersInChat)
                usersOutChat.remove(user);

            return new ResponseEntity<>(usersOutChat.stream().sorted((a1, b1) -> Long.compare(a1.getId(), b1.getId())).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
