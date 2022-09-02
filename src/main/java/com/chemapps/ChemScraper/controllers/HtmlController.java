package com.chemapps.ChemScraper.controllers;

import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinOutput;
import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
public class HtmlController {


    @Autowired
    private MarvinService marvinService;

    private String api = "https://cactus.nci.nih.gov/chemical/structure/";


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
    public void InChi(@RequestBody MarvinOutput marvinOutput, HttpServletResponse response) {
        System.out.println("******************");
        System.out.println(marvinOutput.getName());
        System.out.println(marvinOutput.getInchi());
        System.out.println(marvinOutput.getSmiles());
        System.out.println("******************");
        marvinOutput.cleanUpInput();
        marvinService.save(marvinOutput);
    }

    @GetMapping("/records")
    public String records(Model model) {
        List<MarvinOutput> records = marvinService.getAll();
        model.addAttribute("records", records);
        return "records";
    }

    @GetMapping("/wait")
    public String Foo(Model model) throws IOException, InterruptedException {
        //perhaps neet to sleep a bit to save the entry

        MarvinOutput record = marvinService.getLast();
        model.addAttribute("records", record); //???? proly dont need


        HttpClient client = HttpClient.newBuilder().build();
        String inchi = record.getInchi();

        HttpRequest request = HttpRequest.newBuilder()
                //.headers()
                .uri(URI.create(api + inchi + "/cas"))
                .GET()
                .build();
        HttpResponse response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("STATUS CODE: " + response.statusCode());

        for(String s : response.body().toString().split("\n"))
            System.out.println("entry: " + s);



        return "records";
    }

}