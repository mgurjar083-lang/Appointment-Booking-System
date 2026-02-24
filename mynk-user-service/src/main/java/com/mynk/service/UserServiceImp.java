package com.mynk.service;

import com.mynk.exception.UserException;
import com.mynk.modal.User;
import com.mynk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> op = userRepository.findById(id);

        if(op.isPresent()){
            return op.get();
        }else {
            throw new UserException("User Not Found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty()){
            throw new UserException("User Not Found By Id : "+id);
        }

        userRepository.deleteById(op.get().getId());
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty()){
            throw new UserException("User not found by id : "+id);
        }

        User existingUSer = op.get();

        existingUSer.setFullName(user.getFullName());
        existingUSer.setUserName(user.getUserName());
        existingUSer.setEmail(user.getEmail());
        existingUSer.setPhoneNumber(user.getPhoneNumber());
        existingUSer.setRole(user.getRole());

        return userRepository.save(existingUSer);
    }
}
