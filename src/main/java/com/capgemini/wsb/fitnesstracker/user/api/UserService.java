package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);
    Optional<User> deleteUser(long ID);
    List<User> findByEmailContaining(String query);
    public List<User> getUsersOlderThanProvided(final LocalDate time);
    User updateUser(Long userID, User user);
}
