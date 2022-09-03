package com.chemapps.ChemScraper.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScrapeFluorochem {


    public static String getNumber(String cas) {

        try{
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .headers("Content-Type", "application/json")
                    .uri(URI.create("http://www.fluorochem.co.uk/Products/ExportSearchCSV"))
                    .POST(HttpRequest.BodyPublishers.ofString("{\"lstSearchType\": \"C\", \"txtSearchText\": \"" + cas + "\", \"groupFilters\": []}"))
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response status 1: " + response.statusCode());
            String filename = response.body().toString();
            if(!filename.matches(".+\\.csv")){
                return "not in fluorochem catalogue!";
            }


            client = HttpClient.newBuilder().build();
            request = HttpRequest.newBuilder()
                    .headers("Content-Type", "application/json")
                    .uri(URI.create("http://www.fluorochem.co.uk/Products/DownloadExportFile?fileName=" + filename))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("response status2 : " + response.statusCode() + "\n\n" + response.body());
            String result = response.body().toString().split("\n")[1].split(",")[0];
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("something went wrong. Could not get Flurochem number!");
            return null;
        }
    }
}
