package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.fotosalon.dto.ZakazDto;
import ru.project.fotosalon.models.*;
import ru.project.fotosalon.repos.*;
import ru.project.fotosalon.utils.ZakazStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ZakazRepository zakazRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UslugaRepository uslugaRepository;

    @Autowired
    private SkidkaRepository skidkaRepository;

    @Autowired
    private SotrudnikRepository sotrudnikRepository;


    @PostMapping("/client/add-new-zakaz")
    public @ResponseBody
    Zakaz saveNewZakaz(@RequestBody ZakazDto z) {
        System.out.println(z.toString());
        Client client = clientRepository.findById(z.getId_client()).orElse(null);
        Sotrudnik sotrudnik = sotrudnikRepository.findById(z.getId_sotr()).orElse(null);
        Usluga usluga = uslugaRepository.findById(z.getId_usligi()).orElse(null);
        //Skidka skidka = skidkaRepository.findAllBySotrudnikIdAndUslugaIdAndClientId(sotrudnik.getId(),usluga.getId(),client.getId()).get(0);
        Zakaz zakaz = new Zakaz(
                new Date(),
                null,
                null,
                ZakazStatus.CREATED,
                usluga.getPrice()-(usluga.getPrice()/100.0)*usluga.getSkidka(),
                client,
                sotrudnik,
                usluga,
                z.getNumber()
        );
        return zakazRepository.save(zakaz);
    }

    @RequestMapping(value = "/client/get-zakaz-by-id/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Zakaz getZakaz(@PathVariable("id") Long id) {
        return zakazRepository.findById(id).orElse(null);
    }

    @RequestMapping(value = "/client/get-zakazy-by-client-id/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Zakaz> getZakazyByClient(@PathVariable("id") Long id) {
        return zakazRepository.findAllByClientId(id);
    }

    @RequestMapping(value = "/client/get-all-zakazy", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Zakaz> getAllZakazy() {
        return zakazRepository.findAll();
    }

    @RequestMapping(value = "/client/get-zakazy-by-status/{stat}", method = RequestMethod.GET)
    public @ResponseBody
    List<Zakaz> getZakazyByStatus(@PathVariable("stat") ZakazStatus status) {
        return zakazRepository.findAllByStatus(status);
    }

    @RequestMapping(value = "/client/get-zakazy-by-date/{date1}/{date2}", method = RequestMethod.GET)
    public @ResponseBody
    List<Zakaz> getZakazyByDate(@PathVariable("date1") String data1,@PathVariable("date2") String data2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(data1);
            d2 = format.parse(data2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return zakazRepository.findBetweenTwoDates(d1,d2);
    }

    @RequestMapping(value = "/client/get-zakazy-by-date-and-status/{date1}/{date2}/{stat}", method = RequestMethod.GET)
    public @ResponseBody
    List<Zakaz> getZakazyByDate(@PathVariable("date1") String data1,@PathVariable("date2") String data2,@PathVariable("stat") String status) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(data1);
            d2 = format.parse(data2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return zakazRepository.findBetweenTwoDatesAndStatus(d1,d2,status);
    }

    @RequestMapping(value = "/client/update-zakaz-status/{id}/{stat}", method = RequestMethod.GET)
    public @ResponseBody
    Zakaz updateStatusZakaz(@PathVariable("id") Long id,@PathVariable("stat") ZakazStatus status) {
        Zakaz zakaz = zakazRepository.findById(id).orElse(null);
        if (status == ZakazStatus.COMPLETE){
            zakaz.setCompleteDate(new Date());
            zakaz.setStatus(status);
        }
        if (status == ZakazStatus.ISSUED){
            zakaz.setIssueDate(new Date());
            zakaz.setStatus(status);
        }
        return zakazRepository.save(zakaz);
    }



}

