package com.hospital.service;

import com.hospital.dao.UserDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.impl.UserDAOImpl;
import com.hospital.dao.impl.PatientDAOImpl;
import com.hospital.model.User;
import com.hospital.model.Patient;

public class AuthService {

    private UserDAO userDAO = new UserDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();

    public User login(String email, String password) {
        return userDAO.authenticate(email, password);
    }

    public boolean registerPatientUser(User user, Patient patient) {
        user.setRoleId(5); // Patient Role
        if (userDAO.findByEmail(user.getEmail()) != null) {
            return false; // Duplicate Email
        }
        boolean userCreated = userDAO.createUser(user);
        if (userCreated) {
            patient.setUserId(user.getUserId());
            patient.setEmail(user.getEmail());
            if (patient.getPhone() == null || patient.getPhone().isEmpty()) {
                patient.setPhone(user.getPhone());
            }
            return patientDAO.create(patient);
        }
        return false;
    }
}
