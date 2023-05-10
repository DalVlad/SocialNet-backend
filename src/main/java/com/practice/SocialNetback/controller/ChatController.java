package com.practice.SocialNetback.controller;

import com.practice.SocialNetback.models.Chat;
import com.practice.SocialNetback.models.User;
import com.practice.SocialNetback.queris.request.CreateOrUpdateChatRequest;
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

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatsRepository chatsRepository;

    @Autowired
    MessagesRepository messagesRepository;

    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Chat>> getAllUserChats(@RequestParam(required = false) String nameChat) {
        try {
            List<Chat> chatList = new ArrayList<>();
            List<Chat> foundChats = new ArrayList<>();
            Set<User> users = new HashSet<>();

            var user = userRepository.findByUsername(getCurrentUserName())
                    .orElseThrow(() -> new RuntimeException("Error: User is not found."));

            chatList = user.getChats();

            if (chatList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            if (nameChat != null) {
                for (var chat: chatList) {
                    if (chat.getNameChat().contains(nameChat))
                        foundChats.add(chat);
                }
            }

            if(!foundChats.isEmpty())
                return new ResponseEntity<>(foundChats, HttpStatus.OK);

            return new ResponseEntity<>(chatList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Chat> getChatById(@PathVariable("id") long id) {
        Optional<Chat> chatData = chatsRepository.findById(id);
        if (chatData.isPresent())
            return new ResponseEntity<>(chatData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createChat(@RequestBody CreateOrUpdateChatRequest createOrUpdateChatRequest) {
        if (createOrUpdateChatRequest.getNameChat() == "") {
            String nameChat = createOrUpdateChatRequest.getUsers().stream().findAny().orElse(getCurrentUserName());
            createOrUpdateChatRequest.setNameChat(nameChat);
        }
        if (chatsRepository.existsByNameChat(createOrUpdateChatRequest.getNameChat())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: This name for chat already taken"));
        }

        List<String> strUser = createOrUpdateChatRequest.getUsers();
        Set<User> users = new HashSet<>();

        strUser.forEach(u -> {
            User user = userRepository.findByUsername(u)
                    .orElseThrow(() -> new RuntimeException("Error: User is not found."));
            users.add(user);
        });

        User user = userRepository.findByUsername(getCurrentUserName())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
        users.add(user);

        Chat chat = new Chat(createOrUpdateChatRequest.getNameChat(), users);

        chatsRepository.save(chat);

        return ResponseEntity.ok(new MessageResponse("Chat creating successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Chat> updateChat(@PathVariable("id") long id, @RequestBody CreateOrUpdateChatRequest createOrUpdateChatRequest){
        Optional<Chat> chatDate = chatsRepository.findById(id);
        if (chatDate.isPresent()) {

            Chat _chat = chatDate.get();
            _chat.setNameChat(createOrUpdateChatRequest.getNameChat());

            List<String> strUser = createOrUpdateChatRequest.getUsers();
            Set<User> users = new HashSet<>();

            strUser.forEach(u -> {
                User user = userRepository.findByUsername(u)
                        .orElseThrow(() -> new RuntimeException("Error: User is not found."));
                users.add(user);
            });

            _chat.setUsers(users);

            return new ResponseEntity<>(chatsRepository.save(_chat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable("id") long id) {
        try {
            chatsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
