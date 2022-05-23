package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.fotosalon.dto.UserClient;
import ru.project.fotosalon.dto.UserDto;
import ru.project.fotosalon.dto.UserSotrudnik;
import ru.project.fotosalon.models.Client;
import ru.project.fotosalon.models.Role;
import ru.project.fotosalon.models.Sotrudnik;
import ru.project.fotosalon.models.User;
import ru.project.fotosalon.repos.ClientRepository;
import ru.project.fotosalon.repos.RoleRepository;
import ru.project.fotosalon.repos.UserRepository;
import ru.project.fotosalon.services.UserService;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@Controller
public class RegistrationController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/registration/add-client")
    public @ResponseBody
    Client saveUserClient(@RequestBody UserClient userClient) {
        System.out.println(userClient.toString());
        User user = new User(userClient.getUsername(), userClient.getPassword(), userClient.getPassword());
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("CLIENT"));
        user.setRoles(roles);
        if (userService.saveUser(user)) {
            Client client = new Client(userClient.getUsername(), userClient.getFio(), userClient.getEmail(), userClient.getPhone());
            return clientRepository.save(client);
        } else {
            return new Client(null, null, null);
        }
    }

    @RequestMapping(value = "/registration/client-by-username/{username}", method = RequestMethod.GET)
    public @ResponseBody
    Client getClientByUser(@PathVariable("username") String username) {
        return clientRepository.findByUsername(username);
    }
}
