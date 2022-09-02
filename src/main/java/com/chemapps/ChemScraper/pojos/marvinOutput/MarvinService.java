package com.chemapps.ChemScraper.pojos.marvinOutput;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MarvinService {
    @Transactional
    List<MarvinOutput> getAll();

    @Transactional
    MarvinOutput findById(Long id);

    @Transactional
    List<MarvinOutput> findByName(String name);

    @Transactional
    List<MarvinOutput> findBySmiles(String smiles);

    @Transactional
    List<MarvinOutput> findByInchi(String inchi);

    @Transactional
    void deleteById(Long id);

    @Transactional
    void save(MarvinOutput marvinOutput);
}
