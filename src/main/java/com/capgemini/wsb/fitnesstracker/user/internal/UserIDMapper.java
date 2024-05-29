package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserIDDto;
import org.springframework.stereotype.Component;

@Component
public class UserIDMapper {
    UserIDDto toDto(User user)
    {
        return new UserIDDto(user.getId(),
                user.getFirstName(),
                user.getLastName()
                );
    }

}
