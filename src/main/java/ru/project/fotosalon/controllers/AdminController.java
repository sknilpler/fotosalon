package ru.project.fotosalon.controllers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
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
import ru.project.fotosalon.utils.ZakazStatus;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    @RequestMapping(value = "/admin/usluga/print-statistic-by-date/{d1}/{d2}", method = RequestMethod.POST)
    public void printServicesStatisticByDate(@PathVariable("d1") String d1, @PathVariable("d2") String d2, HttpServletResponse response) throws IOException {
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

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String filename = "Отчет по оказанным услугам за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Услуги");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        sheet.createRow(3);
        for (int i = 0; i < 4; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                3  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Оказанные услуги с " + formatter2.format(LocalDate.parse(d1)) + " по " + formatter2.format(LocalDate.parse(d2)));

        for (int i = 0; i < 4; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Наименование услуги");
        sheet.getRow(2).getCell(1).setCellValue("Стоимость, руб");
        sheet.getRow(2).getCell(2).setCellValue("Кол-во");
        sheet.getRow(2).getCell(3).setCellValue("Общая стоимосоть, руб");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 4; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 4; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
                if ((j == 1) || (j == 2) || (j == 3)) {
                    sheet.getRow(i).getCell(j).setCellType(CellType.NUMERIC);
                }
            }
        }
        int row = 3;
        int sum1 = 0;
        double sum2 = 0;
        for (UslugaRenderedDto dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getName());
            sheet.getRow(row).getCell(1).setCellValue(dto.getPrice());
            sheet.getRow(row).getCell(2).setCellValue(dto.getNum());
            sheet.getRow(row).getCell(3).setCellValue(dto.getTotal());
            sum1 = sum1 + dto.getNum();
            sum2 = sum2 + dto.getTotal();
            row++;
        }
        sheet.getRow(row).getCell(0).setCellValue("Итого");
        sheet.getRow(row).getCell(2).setCellValue(sum1);
        sheet.getRow(row).getCell(3).setCellValue(sum2);

        for (int i = 0; i < 4; i++) {
            sheet.getRow(row).getCell(i).setCellStyle(cellStyle);
        }

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

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
        dtos.add(new UslugaTotalByYearDto("Февраль", Integer.parseInt(((Object[]) obj2[0])[0].toString()), Double.parseDouble(((Object[]) obj2[0])[1].toString())));
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

    @RequestMapping(value = "/admin/usluga/print-statistic-by-year/{year}", method = RequestMethod.POST)
    public void printServicesStatisticByDate(@PathVariable("year") String d1, HttpServletResponse response) throws IOException {
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
        dtos.add(new UslugaTotalByYearDto("Февраль", Integer.parseInt(((Object[]) obj2[0])[0].toString()), Double.parseDouble(((Object[]) obj2[0])[1].toString())));
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

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String filename = "Отчет по доходам за " + d1 + " год.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Доход");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                3  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Отчет по доходам за " + d1 + " год");

        for (int i = 0; i < 3; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Месяц");
        sheet.getRow(2).getCell(1).setCellValue("Кол-во услуг");
        sheet.getRow(2).getCell(2).setCellValue("Сумма, руб");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 4; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 3; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
                if ((j == 1) || (j == 2)) {
                    sheet.getRow(i).getCell(j).setCellType(CellType.NUMERIC);
                }
            }
        }
        int row = 3;
        int sum1 = 0;
        double sum2 = 0;
        for (UslugaTotalByYearDto dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getMonth());
            sheet.getRow(row).getCell(1).setCellValue(dto.getNum());
            sheet.getRow(row).getCell(2).setCellValue(dto.getTotal());
            sum1 = sum1 + dto.getNum();
            sum2 = sum2 + dto.getTotal();
            row++;
        }
        sheet.getRow(row).getCell(0).setCellValue("Итого");
        sheet.getRow(row).getCell(1).setCellValue(sum1);
        sheet.getRow(row).getCell(2).setCellValue(sum2);

        for (int i = 0; i < 3; i++) {
            sheet.getRow(row).getCell(i).setCellStyle(cellStyle);
        }

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    @RequestMapping(value = "/admin/get-zakaz-by-usluga-type/{tip}", method = RequestMethod.GET)
    public @ResponseBody
    List<Zakaz> getZakazByTypeUslugi(@PathVariable("tip") String tip) {
        return zakazRepository.findByUslugaType(tip);
    }

    @RequestMapping(value = "/admin/get-statistic-by-clients/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<ClientStatisticDto> getClientStatistic(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        List<ClientStatisticDto> dtos = new ArrayList<>();

        List<Object[]> list = clientRepository.getClientStatistic(d1,d2);
        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String fio = (String) obj[1];
            int num = Integer.parseInt(obj[2].toString());
            Date lastZakazDate = (Date) obj[3];
            dtos.add(new ClientStatisticDto(id, fio, num, lastZakazDate));
        }
        return dtos;
    }

    @RequestMapping(value = "/admin/print-get-statistic-by-clients/{d1}/{d2}", method = RequestMethod.POST)
    public void printClientStatistic(@PathVariable("d1") String d1, @PathVariable("d2") String d2,HttpServletResponse response) throws IOException {
        List<ClientStatisticDto> dtos = new ArrayList<>();

        List<Object[]> list = clientRepository.getClientStatistic(d1,d2);
        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String fio = (String) obj[1];
            int num = Integer.parseInt(obj[2].toString());
            Date lastZakazDate = (Date) obj[3];
            dtos.add(new ClientStatisticDto(id, fio, num, lastZakazDate));
        }

        DateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy");
        String filename = "Отчет по клиентам за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Клиенты");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                3  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Отчет по клиентам за " + d1 + " - " + d2 + " период");

        for (int i = 0; i < 3; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Клиент (ФИО)");
        sheet.getRow(2).getCell(1).setCellValue("Частота обращения");
        sheet.getRow(2).getCell(2).setCellValue("Дата последнего заказа");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 3; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 3; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
                if ((j == 1)) {
                    sheet.getRow(i).getCell(j).setCellType(CellType.NUMERIC);
                }
            }
        }
        int row = 3;

        for (ClientStatisticDto dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getFio());
            sheet.getRow(row).getCell(1).setCellValue(dto.getNum());
            sheet.getRow(row).getCell(2).setCellValue(formatter2.format(dto.getLastZakazDate()));
            row++;
        }


        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();


    }

    @RequestMapping(value = "/admin/get-statistic-by-uslugi/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<UslugaPopularDto> getPopularUsluga(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        List<UslugaPopularDto> dtos = new ArrayList<>();

        List<Object[]> list = uslugaRepository.getPopularUsluga(d1,d2);
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

    @RequestMapping(value = "/admin/print-statistic-by-uslugi/{d1}/{d2}", method = RequestMethod.POST)
    public void printPopularUsluga(@PathVariable("d1") String d1, @PathVariable("d2") String d2, HttpServletResponse response) throws IOException {
        List<UslugaPopularDto> dtos = new ArrayList<>();

        List<Object[]> list = uslugaRepository.getPopularUsluga(d1,d2);
        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String name = (String) obj[1];
            double price = Double.parseDouble(obj[2].toString());
            String file = (String) obj[3];
            String type = (String) obj[4];
            int num = Integer.parseInt(obj[5].toString());

            dtos.add(new UslugaPopularDto(id, name, price, file, type, num));
        }

        DateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy");
        String filename = "Отчет по востребованным услугам за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Услуги");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                3  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Отчет по востребованным услугам за " + d1 + " - " + d2 + " период");

        for (int i = 0; i < 4; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Наименование услуги");
        sheet.getRow(2).getCell(1).setCellValue("Стоимость");
        sheet.getRow(2).getCell(2).setCellValue("Тип");
        sheet.getRow(2).getCell(3).setCellValue("Кол-во");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 3; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 4; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
                if ((j == 1)) {
                    sheet.getRow(i).getCell(j).setCellType(CellType.NUMERIC);
                }
            }
        }
        int row = 3;

        for (UslugaPopularDto dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getName());
            sheet.getRow(row).getCell(1).setCellValue(dto.getPrice());
            sheet.getRow(row).getCell(2).setCellValue(dto.getType());
            sheet.getRow(row).getCell(3).setCellValue(dto.getNum());
            row++;
        }


        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
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

    @RequestMapping(value = "/admin/print-consumption-between-dates/{d1}/{d2}", method = RequestMethod.POST)
    public void printRashodByDate(@PathVariable("d1") String d1, @PathVariable("d2") String d2, HttpServletResponse response) throws IOException {
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

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String filename = "Отчет по расходам материалов за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Расходники");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                3  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Отчет по расходам материалов за " + d1 + " - " + d2 + " период");

        for (int i = 0; i < 4; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Наименование");
        sheet.getRow(2).getCell(1).setCellValue("Тип");
        sheet.getRow(2).getCell(2).setCellValue("Ед. изм.");
        sheet.getRow(2).getCell(3).setCellValue("Кол-во");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 3; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 4; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
                if ((j == 1)) {
                    sheet.getRow(i).getCell(j).setCellType(CellType.NUMERIC);
                }
            }
        }
        int row = 3;

        for (StatSkladDto dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getName());
            sheet.getRow(row).getCell(1).setCellValue(dto.getType());
            sheet.getRow(row).getCell(2).setCellValue(dto.getUnits());
            sheet.getRow(row).getCell(3).setCellValue(dto.getNumber());
            row++;
        }


        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

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
            double zarplata = ((oklad + premiya) / 160.0) * hours;

            zarplataDto.add(new ZarplataDTO(id, username, fio, post, phone, oklad, premiya, avatar, hours, zarplata));
        }

        return zarplataDto;
    }

    @RequestMapping(value = "/admin/print-zarplata-table/{d1}/{d2}", method = RequestMethod.POST)
    public void printZarplata(@PathVariable("d1") String d1, @PathVariable("d2") String d2, HttpServletResponse response) throws IOException {
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
            double zarplata = ((oklad + premiya) / 160.0) * hours;

            zarplataDto.add(new ZarplataDTO(id, username, fio, post, phone, oklad, premiya, avatar, hours, zarplata));
        }


        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String filename = "Отчет по заработной плате за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Сотрудники");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                6  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Отчет по ЗП за " + d1 + " - " + d2 + " период");

        for (int i = 0; i < 6; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("ФИО");
        sheet.getRow(2).getCell(1).setCellValue("Должность");
        sheet.getRow(2).getCell(2).setCellValue("Оклад");
        sheet.getRow(2).getCell(3).setCellValue("Премия");
        sheet.getRow(2).getCell(4).setCellValue("Отработано часов");
        sheet.getRow(2).getCell(5).setCellValue("Зарплата");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < zarplataDto.size() + 3; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 6; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
            }
        }
        int row = 3;

        for (ZarplataDTO dto : zarplataDto) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getFio());
            sheet.getRow(row).getCell(1).setCellValue(dto.getPost());
            sheet.getRow(row).getCell(2).setCellValue(dto.getOklad());
            sheet.getRow(row).getCell(3).setCellValue(dto.getPremiya());
            sheet.getRow(row).getCell(4).setCellValue(dto.getHours());
            sheet.getRow(row).getCell(5).setCellValue(dto.getZarplata());
            row++;
        }


        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    @RequestMapping(value = "/admin/get-all-clients", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/admin/get-income-expense-by-date/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<IncomeExpenseDTO> getIncomeExpense(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        List<IncomeExpenseDTO> dtos = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(d1);
        LocalDate endDate = LocalDate.parse(d2);

        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());

        System.out.println(listOfDates.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        listOfDates.forEach(d -> {
            String dd1 = formatter.format(d) + " 00:00";
            String dd2 = formatter.format(d) + " 23:59";
            List<Object[]> list = zakazRepository.getIncomeConsumptionByDate(dd1, dd2);
            for (Object[] obj : list) {
                double income = Double.parseDouble(obj[0].toString());
                double expense = Double.parseDouble(obj[1].toString());
                dtos.add(new IncomeExpenseDTO(formatter2.format(d), income, expense));
            }
        });

        return dtos;
    }


    /**
     * Печать приход расход
     */
    @RequestMapping(value = "/admin/print-income-expense-by-date/{d1}/{d2}", method = RequestMethod.POST)
    public void printIncomeExpense(@PathVariable("d1") String d1, @PathVariable("d2") String d2, HttpServletResponse response) throws IOException {

        List<IncomeExpenseDTO> dtos = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(d1);
        LocalDate endDate = LocalDate.parse(d2);

        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());

        System.out.println(listOfDates.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        listOfDates.forEach(d -> {
            String dd1 = formatter.format(d) + " 00:00";
            String dd2 = formatter.format(d) + " 23:59";
            List<Object[]> list = zakazRepository.getIncomeConsumptionByDate(dd1, dd2);
            for (Object[] obj : list) {
                double income = Double.parseDouble(obj[0].toString());
                double expense = Double.parseDouble(obj[1].toString());
                dtos.add(new IncomeExpenseDTO(formatter2.format(d), income, expense));
            }
        });
        String filename = "Доходы и расходы за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Доходы и расходы");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                2  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Данные о расходах и доходах с " + formatter2.format(LocalDate.parse(d1)) + " по " + formatter2.format(LocalDate.parse(d2)));

        for (int i = 0; i < 3; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Дата");
        sheet.getRow(2).getCell(1).setCellValue("Доход, руб");
        sheet.getRow(2).getCell(2).setCellValue("Расход, руб");

        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 4; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 3; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
                if ((j == 1) || (j == 2)) {
                    sheet.getRow(i).getCell(j).setCellType(CellType.NUMERIC);
                }
            }
        }
        int row = 3;
        double sumIncome = 0;
        double sumExpense = 0;
        for (IncomeExpenseDTO dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getDate());
            sheet.getRow(row).getCell(1).setCellValue(dto.getIncome());
            sheet.getRow(row).getCell(2).setCellValue(dto.getExpense());
            sumIncome = sumIncome + dto.getIncome();
            sumExpense = sumExpense + dto.getExpense();
            row++;
        }
        sheet.getRow(row).getCell(0).setCellValue("Итого");
        sheet.getRow(row).getCell(1).setCellValue(sumIncome);
        sheet.getRow(row).getCell(2).setCellValue(sumExpense);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(row).getCell(i).setCellStyle(cellStyle);
        }
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 25 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @RequestMapping(value = "/admin/set-cancel-status-zakaz/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Zakaz setCancel(@PathVariable("id") Long id) {
        Zakaz zakaz = zakazRepository.findById(id).orElse(null);
        zakaz.setStatus(ZakazStatus.CANCELED);
        return zakazRepository.save(zakaz);
    }


    @RequestMapping(value = "/admin/get-consumption-between-dates-for-one/{d1}/{d2}/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<StatSklad2Dto> getOneRashodByDate(@PathVariable("d1") String d1, @PathVariable("d2") String d2, @PathVariable("id") Long id) {
        List<StatSklad2Dto> dtos = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(d1);
        LocalDate endDate = LocalDate.parse(d2);
        Sklad sklad = skladRepository.findById(id).orElse(null);

        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());

        System.out.println(listOfDates.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        listOfDates.forEach(d -> {
            String dd1 = formatter.format(d) + " 00:00";
            String dd2 = formatter.format(d) + " 23:59";
            List<Object[]> obj1 = skladRepository.findOneRashodnikBeetwenDates(dd1, dd2, id);
            if (obj1.size() > 0) {
                Object[] obj = obj1.get(0);
                dtos.add(new StatSklad2Dto(
                        (String) obj[0],
                        (String) obj[1],
                        (String) obj[2],
                        Double.parseDouble(obj[3].toString()),
                        formatter2.format(d)
                ));
            } else {
                dtos.add(new StatSklad2Dto(
                        sklad.getName(),
                        sklad.getType(),
                        sklad.getUnits(),
                        0,
                        formatter2.format(d)
                ));
            }

        });

        return dtos;
    }

    @RequestMapping(value = "/admin/print-consumption-between-dates-for-one/{d1}/{d2}/{id}", method = RequestMethod.POST)
    public void printOneRashodByDate(@PathVariable("d1") String d1, @PathVariable("d2") String d2, @PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        List<StatSklad2Dto> dtos = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(d1);
        LocalDate endDate = LocalDate.parse(d2);
        Sklad sklad = skladRepository.findById(id).orElse(null);

        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());

        System.out.println(listOfDates.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        listOfDates.forEach(d -> {
            String dd1 = formatter.format(d) + " 00:00";
            String dd2 = formatter.format(d) + " 23:59";
            List<Object[]> obj1 = skladRepository.findOneRashodnikBeetwenDates(dd1, dd2, id);
            if (obj1.size() > 0) {
                Object[] obj = obj1.get(0);
                dtos.add(new StatSklad2Dto(
                        (String) obj[0],
                        (String) obj[1],
                        (String) obj[2],
                        Double.parseDouble(obj[3].toString()),
                        formatter2.format(d)
                ));
            } else {
                dtos.add(new StatSklad2Dto(
                        sklad.getName(),
                        sklad.getType(),
                        sklad.getUnits(),
                        0,
                        formatter2.format(d)
                ));
            }

        });

        String filename = "Данные по расходнику " + sklad.getName() + " за " + d1 + " - " + d2 + " период.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Расходник");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                5  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Данные по расходнику " + sklad.getName() + " с " + formatter2.format(LocalDate.parse(d1)) + " по " + formatter2.format(LocalDate.parse(d2)));

        for (int i = 0; i < 5; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("Дата");
        sheet.getRow(2).getCell(1).setCellValue("Наименование");
        sheet.getRow(2).getCell(2).setCellValue("Тип");
        sheet.getRow(2).getCell(3).setCellValue("Ед. изм.");
        sheet.getRow(2).getCell(4).setCellValue("Израсходавано");


        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 4; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 5; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
            }
        }
        int row = 3;

        for (StatSklad2Dto dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getDate());
            sheet.getRow(row).getCell(1).setCellValue(dto.getName());
            sheet.getRow(row).getCell(2).setCellValue(dto.getType());
            sheet.getRow(row).getCell(3).setCellValue(dto.getUnits());
            sheet.getRow(row).getCell(4).setCellValue(dto.getTotal());

            row++;
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 25 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    @RequestMapping(value = "/admin/get-client-skidka-usluga-sotrudnik/{d1}/{d2}", method = RequestMethod.GET)
    public @ResponseBody
    List<ClientSkidkaDTO> getClientSkidka(@PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        List<ClientSkidkaDTO> dtos = new ArrayList<>();
        List<Object[]> list = clientRepository.getAllSkidka(d1,d2);
        for (Object[] obj : list) {
            dtos.add(new ClientSkidkaDTO((String) obj[0], (String) obj[1], format.format((Date) obj[2]), (int) Double.parseDouble(obj[3].toString()), Double.parseDouble(obj[4].toString()), (String) obj[5]));
        }
        return dtos;
    }

    @RequestMapping(value = "/admin/print-client-skidka-usluga-sotrudnik/{d1}/{d2}", method = RequestMethod.GET)
    public void printClientSkidka(@PathVariable("d1") String d1, @PathVariable("d2") String d2, HttpServletResponse response) throws IOException {
        List<ClientSkidkaDTO> dtos = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        List<Object[]> list = clientRepository.getAllSkidka(d1,d2);
        for (Object[] obj : list) {
            dtos.add(new ClientSkidkaDTO((String) obj[0], (String) obj[1], format.format((Date) obj[2]), (int) Double.parseDouble(obj[3].toString()), Double.parseDouble(obj[4].toString()), (String) obj[5]));
        }

        String filename = "Данные по скидкам у клиентов.xlsx";
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = contentDisposition.toString();

        response.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();


        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//Set borders
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Скидки");
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        for (int i = 0; i < 3; i++) {
            sheet.getRow(i).createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                5  //last column  (0-based)
        ));
        CellStyle cellStyleTopic = workbook.createCellStyle();
        Font fontTopic = workbook.createFont();
        fontTopic.setFontName("Times New Roman");
        fontTopic.setFontHeightInPoints((short) 14);
        fontTopic.setBold(true);
        cellStyleTopic.setFont(fontTopic);

        cellStyleTopic.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTopic.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyleTopic);
        sheet.getRow(0).getCell(0).setCellValue("Данные по клиентам за " + d1 + " - " + d2 + " период");

        for (int i = 0; i < 6; i++) {
            sheet.getRow(2).createCell(i).setCellStyle(cellStyle);
        }
        sheet.getRow(2).getCell(0).setCellValue("ФИО клиента");
        sheet.getRow(2).getCell(1).setCellValue("Услуга");
        sheet.getRow(2).getCell(2).setCellValue("Дата заказа");
        sheet.getRow(2).getCell(3).setCellValue("Скидка, %");
        sheet.getRow(2).getCell(4).setCellValue("Стоимость со скидкой, руб");
        sheet.getRow(2).getCell(5).setCellValue("Сотрудник");


        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setFontHeightInPoints((short) 14);
        font2.setBold(false);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        for (int i = 3; i < dtos.size() + 3; i++) {
            sheet.createRow(i);
            for (int j = 0; j < 6; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle2);
            }
        }
        int row = 3;

        for (ClientSkidkaDTO dto : dtos) {
            sheet.getRow(row).getCell(0).setCellValue(dto.getFio());
            sheet.getRow(row).getCell(1).setCellValue(dto.getUsluga());
            sheet.getRow(row).getCell(2).setCellValue(dto.getData());
            sheet.getRow(row).getCell(3).setCellValue(dto.getSkidka());
            sheet.getRow(row).getCell(4).setCellValue(dto.getTotal());
            sheet.getRow(row).getCell(5).setCellValue(dto.getSotrudnik());

            row++;
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}


