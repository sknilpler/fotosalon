package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.fotosalon.dto.StatSkladDto;
import ru.project.fotosalon.models.Sklad;
import ru.project.fotosalon.models.User;
import ru.project.fotosalon.repos.SkladRepository;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@CrossOrigin(origins = "*")
public class SkladController {

    @Autowired
    private SkladRepository skladRepository;

    @RequestMapping(value = "/sklad/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Sklad> getAllSkladItems() {
        return skladRepository.findAll();
    }

    @RequestMapping(value = "/sklad/get-item/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Sklad getSkladItem(@PathVariable("id") Long id) {
        return skladRepository.findById(id).orElse(new Sklad(null,null,null,0,0));
    }

    @PostMapping("/sklad/add-new-item")
    public @ResponseBody
    Sklad saveNewSkladItem(@RequestBody Sklad sklad) {
        System.out.println(sklad.toString());
        if (skladRepository.findByNameTypeUnits(sklad.getName(), sklad.getType(), sklad.getUnits()).size() > 0) {
            Sklad sklad1 = skladRepository.findByNameTypeUnits(sklad.getName(), sklad.getType(), sklad.getUnits()).get(0);
            sklad1.setNumber(sklad1.getNumber() + sklad.getNumber());
            return skladRepository.save(sklad1);
        } else {
            return skladRepository.save(sklad);
        }
    }

    @PostMapping("/sklad/edit-item")
    public @ResponseBody
    Sklad saveEditSkladItem(@RequestBody Sklad sklad) {
        System.out.println(sklad.toString());
        return skladRepository.save(sklad);
    }

    @RequestMapping(value = "/sklad/search-item/{keyword}", method = RequestMethod.GET)
    public @ResponseBody
    List<Sklad> searchSkladItem(@PathVariable("keyword") String keyword) {
        return skladRepository.findAllByKeyword(keyword);
    }

    @RequestMapping(value = "/sklad/delete-item/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> deleteSkladItem(@PathVariable("id") Long id) {
        skladRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/statistik/get-statik", method = RequestMethod.GET)
    public @ResponseBody
    List<StatSkladDto> getBuyingTable(){
        List<StatSkladDto> dtos = new ArrayList<>();
        Date dateBefore = convertToDateViaInstant(LocalDate.now().minusYears(2));
        List<Object[]> list = skladRepository.findRashodniksBeetwenDates(dateBefore,new Date());

        for (Object[] obj : list) {
            long id = Long.parseLong(obj[0].toString());
            String name = (String) obj[1];
            String type = (String) obj[4];
            String units = (String) obj[5];
            int price = Integer.parseInt(obj[3].toString());
            int number = Integer.parseInt(obj[2].toString());
            dtos.add(new StatSkladDto(id, name, type, units, price, number/24));
        }

        return dtos;
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

}
