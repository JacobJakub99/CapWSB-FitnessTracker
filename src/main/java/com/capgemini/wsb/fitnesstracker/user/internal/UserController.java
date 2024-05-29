package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserIDMapper userIDMapper;
    private final UserMailIDMapper userMailIDMapper;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userMapper.toEntity(userDto)), CREATED);
    }

    @GetMapping("/simple")
    public List<UserIDDto> getAllUserSimpleInformation(){
        return userService.findAllUsers().stream().map(userIDMapper::toDto).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@RequestParam long id){
        return userService.deleteUser(id).map(userMapper::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/emailInfo")
    public List<UserMailIDDto> getAllUsersByEmailContaining(@RequestParam String query){
        return userService.findByEmailContaining(query).stream().map(userMailIDMapper::toDto).toList();
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> getUsersOlderThanGivenAge(@PathVariable LocalDate time) {
        final List<User> olderUsers = userService.getUsersOlderThanProvided(time);
        if(olderUsers.isEmpty()) {
            ResponseEntity.notFound();
        }
        return ok(olderUsers.stream().map(userMapper::toDto).collect(toList()));
    }

    @PutMapping("/updateUserEmail")
    public ResponseEntity<User> updateUserEmail(@RequestBody UserMailIDDto userMailIDDto){
        return userService.updateUserEmail(userMailIDDto.ID(), userMailIDDto.email()).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}