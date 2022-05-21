package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.fotosalon.models.Sklad;
import ru.project.fotosalon.models.User;
import ru.project.fotosalon.repos.SkladRepository;

import java.util.List;

@CrossOrigin
@Controller
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

    @PostMapping("/sklad/edit-item/")
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


}
