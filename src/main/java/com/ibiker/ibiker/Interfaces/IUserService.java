package com.ibiker.ibiker.Interfaces;

import com.ibiker.ibiker.Models.AuthData;
import com.ibiker.ibiker.Models.User;

public interface IUserService {
    String loginUser(AuthData authData, Long expiration, String secretKey);
    User registerUser(AuthData authData);
}
