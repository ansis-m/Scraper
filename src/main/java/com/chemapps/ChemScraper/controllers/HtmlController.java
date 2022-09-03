package com.chemapps.ChemScraper.controllers;
import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinOutput;
import com.chemapps.ChemScraper.pojos.marvinOutput.MarvinService;
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

    private String cactus = "https://cactus.nci.nih.gov/chemical/structure/";
    private String chembook = "https://webbook.nist.gov/cgi/cbook.cgi?InChI=";
    private String units = "&Units=SI";



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


        String cas = getCas(record.getInchi());
        record.setCas(cas);
        marvinService.save(record);
        model.addAttribute("record", record);
        HttpClient client = HttpClient.newBuilder().build();


        return "record";
    }

    private String getCas(String inchi) {

        String[] numbers;
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                //.headers()
                .uri(URI.create(chembook + inchi.replace("=", "%3D").replaceAll("/", "%2F") + units))
                .GET()
                .build();


        try {
            HttpResponse response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("STATUS CODE: " + response.statusCode());
            if(response.statusCode() == 200 && response.body().toString().contains("CAS Registry Number:")){
                String result = response.body().toString().split("<li><strong>CAS Registry Number:</strong> ")[1].split("<", 2)[0];
                System.out.println(result);
                if(result.matches("[0-9]{2,7}-[0-9]{2}-[0-9]")) {
                    System.out.println("\n\nMatch!!!\n\n");
                    return result;
                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("something went wrong when sraping in Chemicalbook!!!");
        }

        client = HttpClient.newBuilder().build();
        request = HttpRequest.newBuilder()
                //.headers()
                .uri(URI.create(cactus + inchi + "/cas"))
                .GET()
                .build();
        try {
            HttpResponse response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("STATUS CODE: " + response.statusCode());
            if(response.statusCode() == 200) {
                numbers = response.body().toString().split("\n");
                for(String s : numbers)
                    System.out.println("entry: " + s);
                if(numbers.length == 1)
                    return numbers[0];
                else
                    return filter(numbers);
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong scraping the cactus!");
            e.printStackTrace();
        }

        return null;
    }

    private String filter(String[] numbers) {

        int minLen = 1000;

        for(String s : numbers){
            if (s.length() < minLen)
                    minLen = s.length();
        }

        for(String s : numbers)
            System.out.println(s);
        Arrays.sort(numbers);
        System.out.println("*******SORTING************");
        for(String s : numbers)
            System.out.println(s);


        for(String s : numbers){
            if (s.length() == minLen && s.matches("[0-9]{2,7}-[0-9]{2}-[0-9]"))
                return s;
        }

        return null;
    }
}