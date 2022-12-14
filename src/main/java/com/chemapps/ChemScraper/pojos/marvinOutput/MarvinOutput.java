package com.chemapps.ChemScraper.pojos.marvinOutput;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class MarvinOutput {


    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 1000)
    private String name;

    @Column(length = 1000)
    private String inchi;

    @Column(length = 1000)
    private String smiles;

    @Column
    private String cas;

    @Column
    private String timestamp;


    public MarvinOutput() {
        this.timestamp = LocalDateTime.now().toString();
    }

    public MarvinOutput(String name, String inchi, String smiles) {
        this.timestamp = LocalDateTime.now().toString();
        this.name = name;
        this.inchi = inchi;
        this.smiles = smiles;
    }

    public void cleanUpInput() {
        this.inchi = inchi.split("\n")[0];
    }
}