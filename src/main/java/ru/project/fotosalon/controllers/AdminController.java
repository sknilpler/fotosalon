package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.project.fotosalon.dto.Login;
import ru.project.fotosalon.dto.UserDto;
import ru.project.fotosalon.dto.UserSotrudnik;
import ru.project.fotosalon.models.Role;
import ru.project.fotosalon.models.Sotrudnik;
import ru.project.fotosalon.models.User;
import ru.project.fotosalon.repos.RoleRepository;
import ru.project.fotosalon.repos.SotrudnikRepository;
import ru.project.fotosalon.repos.UserRepository;
import ru.project.fotosalon.services.UserService;
import ru.project.fotosalon.utils.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@CrossOrigin
@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @Autowired
    SotrudnikRepository sotrudnikRepository;

    @RequestMapping(value = "/admin/get-roles", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @RequestMapping(value = "/admin/get-users", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/admin/add-new-user")
    public @ResponseBody
    ResponseEntity<String> saveNewUser(@RequestBody User user) {
        System.out.println(user.toString());
        if (userService.saveUser(user))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/admin/add-new-role")
    public @ResponseBody
    ResponseEntity<String> saveNewRole(@RequestBody Role role) {
        System.out.println(role.toString());
        roleRepository.save(role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/add-new-user-with-role")
    public @ResponseBody
    ResponseEntity<String> saveNewUserWithRole(@RequestBody UserDto userDto) {
        System.out.println(userDto.toString());
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getPasswordConfirm());
        Set<Role> roles = new HashSet<>();
        userDto.getRoles().forEach(r -> roles.add(roleRepository.findByName(r.getName())));
        user.setRoles(roles);
        if (userService.saveUser(user))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/admin/get-user-details/{id}")
    public @ResponseBody
    User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/admin/get-role-details/{id}")
    public @ResponseBody
    Role getRole(@PathVariable("id") Long id) {
        return roleRepository.findById(id).orElse(null);
    }

//    @PostMapping("/admin/save-editing-user")
//    public String saveEditUser(@RequestBody User user) {
//        userRepository.save(user);
//        return "Пользователь успешно сохранен!";
//    }

    @PostMapping("/admin/save-editing-role")
    public @ResponseBody
    ResponseEntity<String> saveEditRole(@RequestBody Role role) {
        roleRepository.save(role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/del-user/{id}")
    public @ResponseBody
    ResponseEntity<String> delUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/del-role/{id}")
    public @ResponseBody
    ResponseEntity<String> delRole(@PathVariable("id") Long id) {
        roleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public @ResponseBody
    User login(@RequestBody Login login) {
        System.out.println("Login info input\n" + login.toString());
        if (userService.isLoginUser(login.getUsername(), login.getPassword()))
            return userService.loadUserByUsername(login.getUsername());
        else return new User(null, null, null);
    }

    @RequestMapping(value = "/admin/sotrudnik/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Sotrudnik> getAllSotrudniks() {
        return sotrudnikRepository.findAll();
    }

    @RequestMapping(value = "/admin/sotrudnik/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Sotrudnik getSotrudnik(@PathVariable("id") Long id) {
        return sotrudnikRepository.findById(id).orElse(new Sotrudnik(null, null, null, null, 0, 0));
    }

    @RequestMapping(value = "/admin/sotrudnik/by-username/{username}", method = RequestMethod.GET)
    public @ResponseBody
    Sotrudnik getSotrudnikByUser(@PathVariable("username") String username) {
        return sotrudnikRepository.findByUsername(username);
    }

    @PostMapping("/admin/sotrudnik/add")
    public @ResponseBody
    Sotrudnik saveNewSotrudnik(@RequestBody Sotrudnik sotrudnik) {
        System.out.println(sotrudnik.toString());
        return sotrudnikRepository.save(sotrudnik);
    }

    @PostMapping("/admin/sotrudnik/edit")
    public @ResponseBody
    Sotrudnik saveEditSotrudnik(@RequestBody Sotrudnik sotrudnik) {
        System.out.println(sotrudnik.toString());
        return sotrudnikRepository.save(sotrudnik);
    }

    @RequestMapping(value = "/admin/sotrudnik/{id}/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> delSotrudnik(@PathVariable("id") Long id) {
        User user = userService.loadUserByUsername(Objects.requireNonNull(sotrudnikRepository.findById(id).orElse(null)).getUsername());
        userService.deleteUser(user.getId());
        sotrudnikRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/user-sotrudnik/add")
    public @ResponseBody
    Sotrudnik saveUserSotrudnik(@RequestBody UserSotrudnik userSotrudnik) {
        System.out.println(userSotrudnik.toString());
        UserDto userDto = userSotrudnik.getUser();
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getPasswordConfirm());
        Set<Role> roles = new HashSet<>();
        userDto.getRoles().forEach(r -> roles.add(roleRepository.findByName(r.getName())));
        user.setRoles(roles);
        userService.saveUser(user);
        Sotrudnik sotrudnik = userSotrudnik.getSotrudnik();
        sotrudnik.setUsername(user.getUsername());
        return sotrudnikRepository.save(sotrudnik);
    }

    @PostMapping("/admin/sotrudnik/add-avatar/{id}")
    public @ResponseBody Sotrudnik addAvatar(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file){
        Sotrudnik sotrudnik = sotrudnikRepository.findById(id).orElse(null);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        sotrudnik.setAvatar(fileName);

        String uploadDir = "photos/avatar/" + sotrudnik.getId();

        try {
            FileUploadUtil.saveFile(uploadDir, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sotrudnikRepository.save(sotrudnik);
    }

    @GetMapping("/admin/sotrudnik/get-avatar/{id}")
    @ResponseBody
    String showImage(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Sotrudnik sotrudnik = sotrudnikRepository.findById(id).orElse(null);
        return sotrudnik.getAvatarImagePath();
    }

    @RequestMapping(value = "/admin/sotrudnik/all-by-post/{post}", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Sotrudnik> getAllByPost(@PathVariable("post") String post) {
        return sotrudnikRepository.findAllByPost(post);
    }


}
