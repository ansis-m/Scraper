package com.chemapps.ChemScraper;


import lombok.Getter;
import lombok.Setter;

// Utility class to get input from the HTML form. Instance of this utility class is then sent to Code constructor.
// The data from the HTML form could be sent directly to the Code constructor.
// However, this utility class allows simple addition of input checking. (not implemented)
@Getter
@Setter
public class CodeInput {

    private String code;
    private String time;
    private String views;


    private  CodeInput(){

    }

    public CodeInput(String code, String time, String views) {
        this.code = code;
        this.time = time;
        this.views = views;
    }
}