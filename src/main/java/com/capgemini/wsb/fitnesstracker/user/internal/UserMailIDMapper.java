package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserIDDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserMailIDDto;
import org.springframework.stereotype.Component;

@Component
public class UserMailIDMapper
{
    UserMailIDDto toDto(User user)
    {
        return new UserMailIDDto
                (user.getId(),
                user.getEmail());
    }

}