package com.chemapps.ChemScraper.pojos.marvinOutput;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarvinRepo extends JpaRepository<MarvinOutput, Long> {

    public List<MarvinOutput> findByName(String name);
    public List<MarvinOutput> findByInchi(String name);
    public List<MarvinOutput> findBySmiles(String name);
    MarvinOutput findFirstByOrderByIdDesc();
}
