package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.fotosalon.dto.NewSkidkaDto;
import ru.project.fotosalon.dto.SkidkaDto;
import ru.project.fotosalon.models.*;
import ru.project.fotosalon.repos.ClientRepository;
import ru.project.fotosalon.repos.SkidkaRepository;
import ru.project.fotosalon.repos.SotrudnikRepository;
import ru.project.fotosalon.repos.UslugaRepository;

import java.util.List;
import java.util.Objects;


@Controller
@CrossOrigin(origins = "*")
public class SotrudnikController {
    @Autowired
    private SotrudnikRepository sotrudnikRepository;

    @Autowired
    private SkidkaRepository skidkaRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UslugaRepository uslugaRepository;
    private Skidka save;

    @PostMapping("/sotrudnik/skidka/add")
    public @ResponseBody
    Skidka saveNewSkidka(@RequestBody SkidkaDto skidkaDto) {
        System.out.println(skidkaDto.toString());
        Sotrudnik s = sotrudnikRepository.findById(skidkaDto.getId_sotr()).orElse(null);
        Client c = clientRepository.findById(skidkaDto.getId_clienta()).orElse(null);
        Usluga u = uslugaRepository.findById(skidkaDto.getId_uslugi()).orElse(null);
        Skidka skidka = new Skidka(skidkaDto.getBasis(), skidkaDto.getSize(), u, s, c);
        return skidkaRepository.save(skidka);
    }

    @PostMapping("/sotrudnik/skidka/edit")
    public @ResponseBody
    Skidka editSkidka(@RequestBody SkidkaDto skidkaDto) {
        System.out.println(skidkaDto.toString());
        Sotrudnik s = sotrudnikRepository.findById(skidkaDto.getId_sotr()).orElse(null);
        Client c = clientRepository.findById(skidkaDto.getId_clienta()).orElse(null);
        Usluga u = uslugaRepository.findById(skidkaDto.getId_uslugi()).orElse(null);
        Skidka skidka = skidkaRepository.findById(skidkaDto.getId()).orElse(null);
        skidka.setBasis(skidkaDto.getBasis());
        skidka.setSize(skidkaDto.getSize());
        skidka.setUsluga(u);
        skidka.setSotrudnik(s);
        skidka.setClient(c);
        return save;
    }

    @RequestMapping(value = "/sotrudnik/skidka/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Skidka> getAllSkidka() {
        return skidkaRepository.findAll();
    }

    @RequestMapping(value = "/sotrudnik/skidka-by-sotrudnik/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Skidka> getSkidkaBySotrudnik(@PathVariable("id") Long id) {
        return skidkaRepository.findAllBySotrudnikId(id);
    }

    @RequestMapping(value = "/sotrudnik/skidka-by-client/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Skidka> getSkidkaByClient(@PathVariable("id") Long id) {
        return skidkaRepository.findAllByClientId(id);
    }

    @RequestMapping(value = "/sotrudnik/skidka/{id}/delete", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> delSkidka(@PathVariable("id") Long id) {
        skidkaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/sotrudnik/skidka-to-usluga/add")
    public @ResponseBody
    Usluga saveNewSkidkaToUsluga(@RequestBody NewSkidkaDto skidkaDto) {
        System.out.println(skidkaDto.toString());
        Usluga u = uslugaRepository.findById(skidkaDto.getIdUslugi()).orElse(null);
        u.setSkidka(skidkaDto.getSkidka());
        u.setBasisToSkidka(skidkaDto.getBasis());
        return uslugaRepository.save(u);
    }

}
