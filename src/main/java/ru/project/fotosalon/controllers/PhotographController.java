package ru.project.fotosalon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.project.fotosalon.dto.PortfolioDto;
import ru.project.fotosalon.models.*;
import ru.project.fotosalon.repos.PhotoFileRepository;
import ru.project.fotosalon.repos.PortfolioRepository;
import ru.project.fotosalon.repos.SotrudnikRepository;
import ru.project.fotosalon.repos.UslugaRepository;
import ru.project.fotosalon.utils.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class PhotographController {

    @Autowired
    SotrudnikRepository sotrudnikRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    UslugaRepository uslugaRepository;

    @Autowired
    PhotoFileRepository photoFileRepository;


    @RequestMapping(value = "/photograph/all-by-user-role/{role}", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Sotrudnik> getAllByRolePhotographs(@PathVariable("role") String role) {
        return sotrudnikRepository.findByUserRole(role);
    }

    @PostMapping("/photograph/portfolio/add")
    public @ResponseBody
    Portfolio saveNewPortfolio(@RequestBody PortfolioDto p) {
        System.out.println(p.toString());
        Sotrudnik sotrudnik = sotrudnikRepository.findById(p.getIdSotr()).orElse(null);
        return portfolioRepository.save(new Portfolio(p.getAbout(),sotrudnik));
    }

    @PostMapping("/photograph/portfolio/edit")
    public @ResponseBody
    Portfolio saveNewPortfolio(@RequestBody Portfolio p) {
        System.out.println(p.toString());
        return portfolioRepository.save(p);
    }


    @PostMapping("/photograph/portfolio/add-photo/{id}")
    public @ResponseBody Portfolio addAvatar(@PathVariable("id") Long id, @RequestParam("files") MultipartFile[] files){
        Portfolio portfolio = portfolioRepository.findBySotrudnikId(id);
        for (MultipartFile f: files) {
            String fileName = StringUtils.cleanPath(f.getOriginalFilename());
            String uploadDir = "photos/portfolio/" + portfolio.getId();
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, f);
                photoFileRepository.save(new PhotoFile(fileName,portfolio));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return portfolio;
    }

    @GetMapping("/photograph/portfolio/get-photos/{id}")
    @ResponseBody
    List<String> showImage(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        List<PhotoFile> listPhotos = photoFileRepository.findBySotrudnik(id);
        List<String> listUrl = new ArrayList<>();
        for (PhotoFile p: listPhotos) {
            listUrl.add(p.getPhotoImagePath());
        }
        return listUrl;
    }

}
