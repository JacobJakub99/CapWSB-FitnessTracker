package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> deleteUser(long id) {
        return userRepository.findById(id).map(user ->
        {
            userRepository.deleteById(id);
            return user;
        });
    }

    @Override
    public List<User> findByEmailContaining(String query) {
        return userRepository.findByEmailContainingIgnoreCase(query);
    }

    @Override
    public List<User> getUsersOlderThanProvided(final LocalDate time) {
        final List<User> olderUsers = new ArrayList<>();
        final List<User> allUsers = userRepository.findAll();
        if(!allUsers.isEmpty()) {
            allUsers.forEach(user -> {
                if(user.getBirthdate().isBefore(time)) {
                    olderUsers.add(user);
                }
            });
        }
        return olderUsers;
    }

    @Override
    public User updateUser(Long userId, User user) {
        user.setId(userId);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}