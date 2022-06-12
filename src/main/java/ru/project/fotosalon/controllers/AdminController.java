package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.project.fotosalon.dto.*;
import ru.project.fotosalon.models.*;
import ru.project.fotosalon.repos.*;
import ru.project.fotosalon.services.UserService;
import ru.project.fotosalon.utils.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SotrudnikRepository sotrudnikRepository;
    @Autowired
    private GrafikRepository grafikRepository;
    @Autowired
    private UslugaRepository uslugaRepository;
    @Autowired
    private SkladRepository skladRepository;
    @Autowired
    private RashodnikRepository rashodnikRepository;

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
       // UserDto userDto = userSotrudnik.getUser();
       // User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getPasswordConfirm());
        User user = new User(userSotrudnik.getUsername(), userSotrudnik.getPassword(), userSotrudnik.getPassword());
        Set<Role> roles = new HashSet<>();
       // userDto.getRoles().forEach(r -> roles.add(roleRepository.findByName(r.getName())));
        userSotrudnik.getRoles().forEach(r -> roles.add(roleRepository.findByName(r.getName())));
        user.setRoles(roles);
       if (userService.saveUser(user)){
           Sotrudnik sotrudnik = new Sotrudnik(userSotrudnik.getUsername(),userSotrudnik.getFio(),userSotrudnik.getPost(),userSotrudnik.getPhone(),userSotrudnik.getOklad(),userSotrudnik.getPremiya());
           return sotrudnikRepository.save(sotrudnik);
       } else return new Sotrudnik(null,null,null,null,0,0);
        //Sotrudnik sotrudnik = userSotrudnik.getSotrudnik();
        //sotrudnik.setUsername(user.getUsername());
        //return sotrudnikRepository.save(sotrudnik);
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

    @PostMapping("/admin/sotrudnik/add-grafik")
    public @ResponseBody
    List<Grafik> addGrafik(@RequestBody AddGrafikDto grafikDto){
        System.out.println(grafikDto.toString());
        Sotrudnik sotrudnik = sotrudnikRepository.findById(grafikDto.getIdSotr()).orElse(null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (DatesDto d:grafikDto.getDates()) {
            try {
                grafikRepository.save(new Grafik(simpleDateFormat.parse(d.getDate()), sotrudnik));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return grafikRepository.findAllBySotrudnikId(grafikDto.getIdSotr());
    }

    @RequestMapping(value = "/admin/sotrudnik/{id}/get-grafik/from/{date1}/to/{date2}", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Grafik> getGrafikBySotrudnikAndDate(@PathVariable("id") Long id, @PathVariable("date1") String str_date1, @PathVariable("date2") String str_date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = simpleDateFormat.parse(str_date1);
            date2 = simpleDateFormat.parse(str_date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return grafikRepository.findBySotrudnikAndDate(id,date1,date2);
    }

    @RequestMapping(value = "/admin/sotrudnik/{id}/get-grafik/from/{date}", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Grafik> getGrafikBySotrudnikByDate(@PathVariable("id") Long id, @PathVariable("date") String str_date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date1 = new Date();
        Date date2 = new Date();
        String d1 = str_date+" 00:00";
        String d2 = str_date+" 23:59";
        try {
            date1 = simpleDateFormat.parse(d1);
            date2 = simpleDateFormat.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return grafikRepository.findBySotrudnikAndDate(id,date1,date2);
    }


    @RequestMapping(value = "/admin/grafik/{id}/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> delGrafik(@PathVariable("id") Long id) {
        grafikRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/usluga/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Usluga> getAllUsluga() {
        return uslugaRepository.findAll();
    }

    @RequestMapping(value = "/admin/usluga/by-sotrudnik/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Usluga> getAllUslugaBySotrudnik( @PathVariable("id") Long id) {
        return uslugaRepository.findAllBySotrudnikId(id);
    }


    @PostMapping("/admin/usluga/add/{id}")
    public @ResponseBody
    Usluga addUsluga(@RequestBody Usluga usluga, @PathVariable("id") Long id){
        System.out.println(usluga.toString());
        Sotrudnik sotr = sotrudnikRepository.findById(id).orElse(null);
        usluga.setSotrudnik(sotr);
        return uslugaRepository.save(usluga);
    }

    @PostMapping("/admin/usluga/add-with-rashodniks")
    public @ResponseBody
    Usluga addUslugaWithRash(@RequestBody UslugaRashodnikiDto u) {
        System.out.println(u.toString());
        Sotrudnik sotr = sotrudnikRepository.findById(u.getId_sotr()).orElse(null);
        Usluga usluga = uslugaRepository.save(new Usluga(u.getName(),u.getPrice(),u.getDuration(),u.getNumbers(),sotr));
        u.getList().forEach(r -> {
            rashodnikRepository.save(new Rashodnik(r.getNumbers(),usluga,skladRepository.findById(r.getId_sklad()).orElse(null)));
        });

        return usluga;
    }

    @PostMapping("/admin/usluga/edit")
    public @ResponseBody
    Usluga addUsluga(@RequestBody UslugaDto ustemp){
        System.out.println(ustemp.toString());
        Usluga usluga = uslugaRepository.findById(ustemp.getId()).orElse(null);
        Sotrudnik sotr = sotrudnikRepository.findById(ustemp.getId_sotr()).orElse(null);
        usluga.setDuration(ustemp.getDuration());
        usluga.setName(ustemp.getName());
        usluga.setNumbers(ustemp.getNumbers());
        usluga.setPrice(ustemp.getPrice());
        usluga.setSotrudnik(sotr);
        return uslugaRepository.save(usluga);
    }

    @PostMapping("/admin/rashodnik/add")
    public @ResponseBody
    Rashodnik addRashodnik(@RequestBody RashodnikDto rashodnikDto){
        System.out.println(rashodnikDto.toString());
        Sklad sklad = skladRepository.findById(rashodnikDto.getId_sklad()).orElse(null);
        Usluga usluga = uslugaRepository.findById(rashodnikDto.getId_uslugi()).orElse(null);
        return rashodnikRepository.save(new Rashodnik(rashodnikDto.getNumbers(),usluga,sklad));
    }

    @RequestMapping(value = "/admin/usluga/{id}/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> delUsluga(@PathVariable("id") Long id) {
        uslugaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/rashodnik/{id}/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> delRashodnik(@PathVariable("id") Long id) {
        rashodnikRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/rashodniki-all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Rashodnik> getAllRashodnik() {
        return rashodnikRepository.findAll();
    }

    @RequestMapping(value = "/admin/rashodniki-from-sklad/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Rashodnik> getRashodnikSklad(@PathVariable("id") Long id) {
        return rashodnikRepository.findAllBySkladId(id);
    }

    @RequestMapping(value = "/admin/get-usluga/{id}", method = RequestMethod.GET)
    public @ResponseBody
    UslugaRashodnikiDto2 getUsluga(@PathVariable("id") Long id) {
        Usluga usluga = uslugaRepository.findById(id).orElse(null);
        List<Sklad> skladList = new ArrayList<>();
        usluga.getRashodnikList().forEach(r -> {
            skladList.add(r.getSklad());
        });
        UslugaRashodnikiDto2 dto = new UslugaRashodnikiDto2(
            usluga.getName(),
                usluga.getPrice(),
                usluga.getDuration(),
                usluga.getNumbers(),
                usluga.getSotrudnik(),
                skladList
        );
        return dto;
    }


}
