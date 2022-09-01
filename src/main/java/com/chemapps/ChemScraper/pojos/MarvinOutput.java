package com.chemapps.ChemScraper.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarvinOutput {

    private String name;
    private String inchi;
    private String smiles;

    public MarvinOutput(String name, String inchi, String smiles) {
        this.name = name;
        this.inchi = inchi;
        this.smiles = smiles;
    }
}