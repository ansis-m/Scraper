package com.chemapps.ChemScraper.controllers;

import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinOutput;
import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class HtmlController {


    @Autowired
    private MarvinService marvinService;


    @GetMapping("/test")
    public String codeForm() {
        System.out.println("HeiHei");
        return "test";
    }

    @GetMapping("/search")
    public String searchForm() {
        System.out.println("YoYo");
        return "search";
    }

    @GetMapping("/form")
    public String form() {
        System.out.println("form");
        return "form";
    }

    @PostMapping("/inchi")
    public ResponseEntity InChi(@RequestBody MarvinOutput marvinOutput) {
        System.out.println("***InChi***");
        System.out.println(marvinOutput.getName());
        System.out.println(marvinOutput.getInchi());
        System.out.println(marvinOutput.getSmiles());
        marvinOutput.cleanUpInput();
        System.out.println(marvinOutput.getInchi());
        //Need to validate and format the input!!!

        marvinService.save(marvinOutput);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/records")
    public String records(Model model) {
        List<MarvinOutput> records = marvinService.getAll();
        model.addAttribute("records", records);
        return "records";
    }

}