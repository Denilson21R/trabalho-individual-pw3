package com.example.pw3trabalhoindividual.controller;

import com.example.pw3trabalhoindividual.model.Especie;
import com.example.pw3trabalhoindividual.model.Status;
import com.example.pw3trabalhoindividual.repository.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EspecieController {
    @Autowired
    EspecieRepository especieRepository;

    @GetMapping(value = "api/especies")
    public ResponseEntity<List<Especie>> getAllEspecies(){
        try{
            List<Especie> especies = especieRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
            return new ResponseEntity<>(especies, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "api/especie")
    public ResponseEntity<Especie> saveEspecie(@RequestBody Especie especie){
        try{
            especie.setStatus(Status.ATIVO);
            Especie newEspecie = especieRepository.save(especie);
            return new ResponseEntity<>(newEspecie, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "api/especie/{id}")
    public ResponseEntity<Especie> updateEspecie(@PathVariable(value = "id") long id, @RequestBody Especie newEspecie){
        Optional<Especie> especie = especieRepository.findById(id);
        if (especie.isPresent()) {
            try {
                Especie especieUpdate = especie.get();
                especieUpdate.setDescricao(newEspecie.getDescricao());
                especieUpdate.setNome(newEspecie.getNome());
                especieUpdate.setStatus(newEspecie.getStatus());
                Especie especieUpdated = especieRepository.save(especieUpdate);
                return new ResponseEntity<>(especieUpdated, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
