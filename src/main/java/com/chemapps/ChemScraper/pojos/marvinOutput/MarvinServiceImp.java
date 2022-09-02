package com.chemapps.ChemScraper.pojos.marvinOutput;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarvinServiceImp implements MarvinService{

    @Autowired
    private MarvinRepo marvinRepo;

    @Override
    @Transactional
    public List<MarvinOutput> getAll(){
        return marvinRepo.findAll();
    }

    @Override
    @Transactional
    public MarvinOutput findById(Long id){
        return marvinRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<MarvinOutput> findByName(String name){
        return marvinRepo.findByName(name);
    }

    @Override
    @Transactional
    public List<MarvinOutput> findBySmiles(String smiles){
        return marvinRepo.findBySmiles(smiles);
    }

    @Override
    @Transactional
    public List<MarvinOutput> findByInchi(String inchi){
        return marvinRepo.findByInchi(inchi);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        marvinRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void save(MarvinOutput marvinOutput){
        marvinRepo.save(marvinOutput);
    }
}
