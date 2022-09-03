package com.chemapps.ChemScraper.controllers;
import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinOutput;
import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinService;
import com.chemapps.ChemScraper.utils.GetCas;
import com.chemapps.ChemScraper.utils.ScrapeFluorochem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HtmlController {


    @Autowired
    private MarvinService marvinService;





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


        MarvinOutput record = marvinService.getLast();
        String cas = GetCas.getCas(record.getInchi());
        String fluorochemN = ScrapeFluorochem.getNumber(cas);
        System.out.println("Fluorochem number!!!" + fluorochemN);
        record.setCas(cas);
        marvinService.save(record);
        model.addAttribute("record", record);
        HttpClient client = HttpClient.newBuilder().build();

        return "record";
    }

}