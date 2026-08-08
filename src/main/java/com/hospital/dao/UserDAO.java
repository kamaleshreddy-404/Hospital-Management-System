package com.hospital.dao;

import com.hospital.model.User;
import java.util.List;

public interface UserDAO {
    User authenticate(String email, String password);
    User findById(int userId);
    User findByEmail(String email);
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean updatePassword(int userId, String newHashedPassword);
    boolean deleteUser(int userId);
    List<User> findAllUsers();
    List<User> findUsersByRole(int roleId);
    int getTotalUsersCount();
}
