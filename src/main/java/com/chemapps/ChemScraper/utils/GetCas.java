package com.chemapps.ChemScraper.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class GetCas {

    private static final String cactus = "https://cactus.nci.nih.gov/chemical/structure/";
    private static final String chembook = "https://webbook.nist.gov/cgi/cbook.cgi?InChI=";
    private static final String units = "&Units=SI";

    public static String getCas(String inchi) {

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

    public static String filter(String[] numbers) {

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
