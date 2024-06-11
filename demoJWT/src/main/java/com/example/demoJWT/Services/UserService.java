package com.example.demoJWT.Services;


import com.example.demoJWT.DAO.UserDAO;

import com.example.demoJWT.Entities.User;
import com.example.demoJWT.Errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> readAllUsers() {
        return userDAO.findAll();
    }

    public User createUser(User user) {

        try {
            User savedUser = userDAO.save(user);
            return savedUser;
        } catch (DataIntegrityViolationException ex) {

            throw new RuntimeException("Error al crear el usuario: " + ex.getMessage(), ex);
        }
    }

    public void deleteUser(Integer id) {
        try {
            if (!userDAO.existsById(id)) {
                throw new ResourceNotFoundException("No se puede borrar, porque no existe ningún usuario con esa ID: " + id);
            }
            userDAO.deleteById(id);
        } catch (Exception ex) {
            String errorMessage = "Error al intentar eliminar el usuario con ID: " + id;
            throw new RuntimeException(errorMessage, ex);
        }
    }

    public User updateUser(Integer id, User updatedUser) {
        try {
            User existingUser = userDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado al usuario con el id: " + id));

            existingUser.setNombre(updatedUser.getNombre());
            existingUser.setApellidos(updatedUser.getApellidos());
            existingUser.setEmail(updatedUser.getEmail());


            return userDAO.save(existingUser);
        } catch (Exception ex) {
            String errorMessage = "Error al intentar actualizar el usuario con ID: " + id;
            throw new RuntimeException(errorMessage, ex);
        }
    }

    public User readUserById(Integer id) {
        try {
            return userDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado al usuario con el ID: " + id));

        } catch (Exception ex) {
            String errorMessage = "EL usuario: " + id + " no existe";
            throw new RuntimeException(errorMessage, ex);
        }
    }
}

