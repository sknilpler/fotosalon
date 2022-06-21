package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private ZakazRepository zakazRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UslugaSotrudnikRepository uslugaSotrudnikRepository;

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
        if (userService.saveUser(user)) {
            Sotrudnik sotrudnik = new Sotrudnik(userSotrudnik.getUsername(), userSotrudnik.getFio(), userSotrudnik.getPost(), userSotrudnik.getPhone(), userSotrudnik.getOklad(), userSotrudnik.getPremiya());
            return sotrudnikRepository.save(sotrudnik);
        } else return new Sotrudnik(null, null, null, null, 0, 0);
        //Sotrudnik sotrudnik = userSotrudnik.getSotrudnik();
        //sotrudnik.setUsername(user.getUsername());
        //return sotrudnikRepository.save(sotrudnik);
    }

    @PostMapping("/admin/sotrudnik/add-avatar/{id}")
    public @ResponseBody
    Sotrudnik addAvatar(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
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
    List<Grafik> addGrafik(@RequestBody AddGrafikDto grafikDto) {
        System.out.println(grafikDto.toString());
        Sotrudnik sotrudnik = sotrudnikRepository.findById(grafikDto.getIdSotr()).orElse(null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (DatesDto d : grafikDto.getDates()) {
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
        return grafikRepository.findBySotrudnikAndDate(id, date1, date2);
    }

    @RequestMapping(value = "/admin/sotrudnik/{id}/get-grafik/from/{date}", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Grafik> getGrafikBySotrudnikByDate(@PathVariable("id") Long id, @PathVariable("date") String str_date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date1 = new Date();
        Date date2 = new Date();
        String d1 = str_date + " 00:00";
        String d2 = str_date + " 23:59";
        try {
            date1 = simpleDateFormat.parse(d1);
            date2 = simpleDateFormat.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return grafikRepository.findBySotrudnikAndDate(id, date1, date2);
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
    List<Usluga> getAllUslugaBySotrudnik(@PathVariable("id") Long id) {
        return uslugaRepository.findAllBySotrudnikId(id);
    }


    @PostMapping("/admin/usluga/add/{id}")
    public @ResponseBody
    Usluga addUsluga(@RequestBody Usluga usluga, @PathVariable("id") Long id) {
        System.out.println(usluga.toString());
        Sotrudnik sotr = sotrudnikRepository.findById(id).orElse(null);
        Usluga usluga1 = uslugaRepository.save(usluga);
        UslugaSotrudnik us = new UslugaSotrudnik(usluga1, sotr);
        uslugaSotrudnikRepository.save(us);
        // usluga.setSotrudnik(sotr);
        return usluga1;
    }

    @PostMapping("/admin/usluga/add-with-rashodniks")
    public @ResponseBody
    Usluga addUslugaWithRash(@RequestBody UslugaRashodnikiDto u) {
        System.out.println(u.toString());
        Sotrudnik sotr = sotrudnikRepository.findById(u.getId_sotr()).orElse(null);
        Usluga usluga = uslugaRepository.save(new Usluga(u.getName(), u.getPrice(), u.getDuration(), u.getNumbers()));
        UslugaSotrudnik us = new UslugaSotrudnik(usluga, sotr);
        uslugaSotrudnikRepository.save(us);
        u.getList().forEach(r -> {
            rashodnikRepository.save(new Rashodnik(r.getNumbers(), usluga, skladRepository.findById(r.getId_sklad()).orElse(null)));
        });
        return usluga;
    }

    @PostMapping("/admin/usluga/edit")
    public @ResponseBody
    Usluga addUsluga(@RequestBody UslugaDto ustemp) {
        System.out.println(ustemp.toString());
        Usluga usluga = uslugaRepository.findById(ustemp.getId()).orElse(null);
        usluga.setDuration(ustemp.getDuration());
        usluga.setName(ustemp.getName());
        usluga.setNumbers(ustemp.getNumbers());
        usluga.setPrice(ustemp.getPrice());
        //  usluga.setSotrudnik(sotr);
        usluga.setFile(ustemp.getFile());
        usluga.setType(ustemp.getType());
        return uslugaRepository.save(usluga);
    }

    @PostMapping("/admin/usluga/add-sotrudniks-to-usluga/{id}")
    public @ResponseBody
    List<UslugaSotrudnik> addUsluga(@RequestBody List<IdsDto> ids, @PathVariable("id") Long id) {
        System.out.println(ids.toString());
        Usluga usluga = uslugaRepository.findById(id).orElse(null);
        List<UslugaSotrudnik> list = new ArrayList<>();
        ids.forEach(idsDto -> {
            list.add(uslugaSotrudnikRepository.save(new UslugaSotrudnik(usluga, sotrudnikRepository.findById(idsDto.getId()).orElse(null))));
        });
        return list;
    }

    @PostMapping("/admin/rashodnik/add")
    public @ResponseBody
    Rashodnik addRashodnik(@RequestBody RashodnikDto rashodnikDto) {
        System.out.println(rashodnikDto.toString());
        Sklad sklad = skladRepository.findById(rashodnikDto.getId_sklad()).orElse(null);
        Usluga usluga = uslugaRepository.findById(rashodnikDto.getId_uslugi()).orElse(null);
        return rashodnikRepository.save(new Rashodnik(rashodnikDto.getNumbers(), usluga, sklad));
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
        List<Sotrudnik> sotrudniks = sotrudnikRepository.findAllByUsluga(id);

        UslugaRashodnikiDto2 dto = new UslugaRashodnikiDto2(
                usluga.getName(),
                usluga.getPrice(),
                usluga.getDuration(),
                usluga.getNumbers(),
                skladList, sotrudniks
        );
        return dto;
    }

    @RequestMapping(value = "/admin/usluga/by-type/{type}", method = RequestMethod.GET)
    public @ResponseBody
    List<Usluga> getAllUslugaByType(@PathVariable("type") String type) {
        return uslugaRepository.findAllByType(type);
    }

    /**
     * @param d1 yyyy-MM-dd
     * @param d2 yyyy-MM-dd
     */
    @RequestMapping(value = "/admin/usluga/statistic-by-date/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<UslugaRenderedDto> getServicesStatisticByDate(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        List<UslugaRenderedDto> dtos = new ArrayList<>();
        List<Object[]> list = uslugaRepository.getRendered(d1, d2);

        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String name = (String) obj[1];
            double price = Double.parseDouble(obj[2].toString());
            int num = Integer.parseInt(obj[3].toString());
            double total = Double.parseDouble(obj[4].toString());
            dtos.add(new UslugaRenderedDto(id, name, price, num, total));
        }

        return dtos;
    }


    @RequestMapping(value = "/admin/usluga/statistic-by-year/{year}", method = RequestMethod.GET)
    public @ResponseBody
    List<UslugaTotalByYearDto> getServicesStatisticByDate(@PathVariable("year") String d1) {
        List<UslugaTotalByYearDto> dtos = new ArrayList<>();
        List<Object[]> list = new ArrayList<>();
        Object[] obj1 = uslugaRepository.getUslugaTotalByDate(d1 + "-01-01", d1 + "-01-31");
        Object[] obj2 = uslugaRepository.getUslugaTotalByDate(d1 + "-02-01", d1 + "-02-28");
        Object[] obj3 = uslugaRepository.getUslugaTotalByDate(d1 + "-03-01", d1 + "-03-31");
        Object[] obj4 = uslugaRepository.getUslugaTotalByDate(d1 + "-04-01", d1 + "-04-30");
        Object[] obj5 = uslugaRepository.getUslugaTotalByDate(d1 + "-05-01", d1 + "-05-31");
        Object[] obj6 = uslugaRepository.getUslugaTotalByDate(d1 + "-06-01", d1 + "-06-30");
        Object[] obj7 = uslugaRepository.getUslugaTotalByDate(d1 + "-07-01", d1 + "-07-31");
        Object[] obj8 = uslugaRepository.getUslugaTotalByDate(d1 + "-08-01", d1 + "-08-31");
        Object[] obj9 = uslugaRepository.getUslugaTotalByDate(d1 + "-09-01", d1 + "-09-30");
        Object[] obj10 = uslugaRepository.getUslugaTotalByDate(d1 + "-10-01", d1 + "-10-31");
        Object[] obj11 = uslugaRepository.getUslugaTotalByDate(d1 + "-11-01", d1 + "-11-30");
        Object[] obj12 = uslugaRepository.getUslugaTotalByDate(d1 + "-12-01", d1 + "-12-31");

        dtos.add(new UslugaTotalByYearDto("Январь", Integer.parseInt(((Object[]) obj1[0])[0].toString()), Double.parseDouble(((Object[]) obj1[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Фквраль", Integer.parseInt(((Object[]) obj2[0])[0].toString()), Double.parseDouble(((Object[]) obj2[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Март", Integer.parseInt(((Object[]) obj3[0])[0].toString()), Double.parseDouble(((Object[]) obj3[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Апрель", Integer.parseInt(((Object[]) obj4[0])[0].toString()), Double.parseDouble(((Object[]) obj4[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Май", Integer.parseInt(((Object[]) obj5[0])[0].toString()), Double.parseDouble(((Object[]) obj5[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Июнь", Integer.parseInt(((Object[]) obj6[0])[0].toString()), Double.parseDouble(((Object[]) obj6[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Июль", Integer.parseInt(((Object[]) obj7[0])[0].toString()), Double.parseDouble(((Object[]) obj7[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Август", Integer.parseInt(((Object[]) obj8[0])[0].toString()), Double.parseDouble(((Object[]) obj8[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Сентябрь", Integer.parseInt(((Object[]) obj9[0])[0].toString()), Double.parseDouble(((Object[]) obj9[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Октябрь", Integer.parseInt(((Object[]) obj10[0])[0].toString()), Double.parseDouble(((Object[]) obj10[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Ноябрь", Integer.parseInt(((Object[]) obj11[0])[0].toString()), Double.parseDouble(((Object[]) obj11[0])[1].toString())));
        dtos.add(new UslugaTotalByYearDto("Декабрь", Integer.parseInt(((Object[]) obj12[0])[0].toString()), Double.parseDouble(((Object[]) obj12[0])[1].toString())));

        return dtos;
    }

    @RequestMapping(value = "/admin/get-zakaz-by-usluga-type/{tip}", method = RequestMethod.GET)
    public @ResponseBody
    List<Zakaz> getZakazByTypeUslugi(@PathVariable("tip") String tip) {
        return zakazRepository.findByUslugaType(tip);
    }

    @RequestMapping(value = "/admin/get-statistic-by-clients", method = RequestMethod.GET)
    public @ResponseBody
    List<ClientStatisticDto> getClientStatistic() {
        List<ClientStatisticDto> dtos = new ArrayList<>();

        List<Object[]> list = clientRepository.getClientStatistic();
        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String fio = (String) obj[1];
            int num = Integer.parseInt(obj[2].toString());
            Date lastZakazDate = (Date) obj[3];
            dtos.add(new ClientStatisticDto(id, fio, num, lastZakazDate));
        }
        return dtos;
    }

    @RequestMapping(value = "/admin/get-statistic-by-uslugi", method = RequestMethod.GET)
    public @ResponseBody
    List<UslugaPopularDto> getPopularUsluga() {
        List<UslugaPopularDto> dtos = new ArrayList<>();

        List<Object[]> list = uslugaRepository.getPopularUsluga();
        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String name = (String) obj[1];
            double price = Double.parseDouble(obj[2].toString());
            String file = (String) obj[3];
            String type = (String) obj[4];
            int num = Integer.parseInt(obj[5].toString());

            dtos.add(new UslugaPopularDto(id, name, price, file, type, num));
        }
        return dtos;
    }

    @RequestMapping(value = "/admin/get-consumption-between-dates/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<StatSkladDto> getRashodByDate(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        List<StatSkladDto> dtos = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = format.parse(d1);
            dateEnd = format.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Object[]> list = skladRepository.findRashodniksBeetwenDates(dateStart, dateEnd);

        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String name = (String) obj[1];
            String type = (String) obj[4];
            String units = (String) obj[5];
            int price = Integer.parseInt(obj[3].toString());
            int number = Integer.parseInt(obj[2].toString());
            dtos.add(new StatSkladDto(id, name, type, units, price, number));
        }

        return dtos;
    }

    @PostMapping("/admin/client-add-skidka")
    public @ResponseBody
    Client addSkidkaToClient(@RequestBody BasisSkidkaDto basisSkidkaDto) {
        Client client = clientRepository.findById(basisSkidkaDto.getId()).orElse(null);
        client.setSkidka(basisSkidkaDto.getSize());
        client.setBasisToSkidka(basisSkidkaDto.getBasis());
        return clientRepository.save(client);
    }

    @RequestMapping(value = "/admin/get-zarplata-table/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<ZarplataDTO> getZarplata(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        List<ZarplataDTO> zarplataDto = new ArrayList<>();
        List<Object[]> list = sotrudnikRepository.getZarplataStatistic(d1, d2);

        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String username = (String) obj[1];
            String fio = (String) obj[2];
            String post = (String) obj[3];
            String phone = (String) obj[4];
            double oklad = Double.parseDouble(obj[5].toString());
            double premiya = Double.parseDouble(obj[6].toString());
            String avatar = (String) obj[7];
            int hours = Integer.parseInt(obj[8].toString());
            double zarplata = ((oklad+premiya)/160.0)*hours;

            zarplataDto.add(new ZarplataDTO(id, username, fio, post, phone, oklad, premiya, avatar, hours, zarplata));
        }

        return zarplataDto;
    }
}


