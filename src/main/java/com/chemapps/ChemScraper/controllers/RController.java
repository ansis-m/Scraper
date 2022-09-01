package com.chemapps.ChemScraper.controllers;

import com.chemapps.ChemScraper.CodeInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RController {

    @PostMapping("/inchi")
    public ResponseEntity InChi(@RequestBody CodeInput codeInput) {
        System.out.println("***InChi***");
        System.out.println(codeInput.getCode());
        System.out.println(codeInput.getViews());
        return new ResponseEntity(HttpStatus.OK);

    }

}
