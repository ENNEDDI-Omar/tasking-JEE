package org.tasking.util;

import org.tasking.domain.entities.User;

public class UserValidation
{
    public static boolean isValidUser(User user) {
        return user != null &&
                AuthValidation.isValidUsername(user.getUsername()) &&
                AuthValidation.isValidEmail(user.getEmail()) &&
                user.getRole() != null;
    }
}
