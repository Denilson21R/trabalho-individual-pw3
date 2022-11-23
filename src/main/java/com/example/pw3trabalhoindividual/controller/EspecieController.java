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
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
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
    public ResponseEntity<Especie> saveEspecie(@RequestParam Map<String, String> newEspecie){
        try{
            Especie especie = new Especie();
            especie.setNome(newEspecie.get("nome"));
            especie.setDescricao(newEspecie.get("descricao"));
            especie.setStatus(Status.ATIVO);
            Especie especieSaved = especieRepository.save(especie);
            return new ResponseEntity<>(especieSaved, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "api/especie/{id}")
    public ResponseEntity<Especie> updateEspecie(@PathVariable(value = "id") long id, @RequestParam Map<String, String> newEspecie){
        Optional<Especie> especie = especieRepository.findById(id);
        if (especie.isPresent()) {
            try {
                Especie especieUpdate = especie.get();
                especieUpdate.setDescricao(newEspecie.get("descricao"));
                especieUpdate.setNome(newEspecie.get("nome"));
                especieUpdate.setStatus(Status.values()[Integer.parseInt(newEspecie.get("status"))]);
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
