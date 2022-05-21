package ru.project.fotosalon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.project.fotosalon.models.User;
import ru.project.fotosalon.repos.RoleRepository;
import ru.project.fotosalon.repos.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public User loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User not found");
           // throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            System.out.println("Пользователь с таким username уже существует!");
            return false;
        }

        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean isLoginUser(String username, String password){
        User userFromDB = userRepository.findByUsername(username);
        if (userFromDB == null) {
            System.out.println("Пользователя с таким username не существует!");
            return false;
        }
        return (userFromDB.getUsername().equals(username)) && (userFromDB.getPassword().equals(password));
       // return bCryptPasswordEncoder.matches(password, userFromDB.getPassword());
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


}
