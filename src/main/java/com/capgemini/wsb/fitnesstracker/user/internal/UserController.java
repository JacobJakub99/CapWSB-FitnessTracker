package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
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

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Long> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(userId, NO_CONTENT);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUser(id).map(userMapper::toDto).get();
    }

    @GetMapping("/email")
    public List<UserMailIDDto> getAllUsersByEmailContaining(@RequestParam("email") String email){
        return userService.findByEmailContaining(email).stream().map(userMailIDMapper::toDto).toList();
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> getUsersOlderThanGivenAge(@PathVariable LocalDate time) {
        final List<User> olderUsers = userService.getUsersOlderThanProvided(time);
        if(olderUsers.isEmpty()) {
            ResponseEntity.notFound();
        }
        return ok(olderUsers.stream().map(userMapper::toDto).collect(toList()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto changedUser) {
        return new ResponseEntity<>(userService.updateUser(userId, userMapper.toEntitySave(changedUser)), ACCEPTED);
    }

}