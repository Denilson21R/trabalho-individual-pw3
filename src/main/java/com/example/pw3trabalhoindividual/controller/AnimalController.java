package com.example.pw3trabalhoindividual.controller;

import com.example.pw3trabalhoindividual.model.Animal;
import com.example.pw3trabalhoindividual.model.Especie;
import com.example.pw3trabalhoindividual.model.Tamanho;
import com.example.pw3trabalhoindividual.repository.AnimalRepository;
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
public class AnimalController {
    @Autowired
    AnimalRepository animalRepository;

    @GetMapping(value = "api/animals")
    public ResponseEntity<List<Animal>> getAllAnimals(){
        try{
            List<Animal> animals = animalRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
            return new ResponseEntity<>(animals, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "api/especie/{id}/animals")
    public ResponseEntity<List<Animal>> getAnimalsByEspecie(@PathVariable(value = "id") long id){
        try{
            List<Animal> animals = animalRepository.findAnimalsbyEspecie(id);
            return new ResponseEntity<>(animals, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "api/animal")
    public ResponseEntity<Animal> addAnimal(@RequestParam Map<String, String> newAnimal){
        try{
            Animal animal = new Animal();
            animal.setNome(newAnimal.get("nome"));
            animal.setTamanho(Tamanho.values()[Integer.parseInt(newAnimal.get("tamanho"))]);
            animal.setDescricao(newAnimal.get("descricao"));
            animal.setNome_dono(newAnimal.get("dono"));

            Especie especie = new Especie();
            especie.setId(Long.valueOf(newAnimal.get("especie")));

            animal.setEspecie(especie);
            if(newAnimal.get("observacao") != null){
                animal.setObservacao(newAnimal.get("observacao"));
            }
            Animal animalSavedanimal = animalRepository.save(animal);
            return new ResponseEntity<>(animalSavedanimal, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "api/animal/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable(value = "id") long id, @RequestParam Map<String, String> newAnimal){
        Optional<Animal> animal = animalRepository.findById(id);
        if(animal.isPresent()){
            Animal animalUpdate = animal.get();
            animalUpdate.setDescricao(newAnimal.get("descricao"));
            animalUpdate.setNome(newAnimal.get("nome"));
            animalUpdate.setTamanho(Tamanho.values()[Integer.parseInt(newAnimal.get("tamanho"))]);
            animalUpdate.setObservacao(newAnimal.get("observacao"));
            try{
                Animal animalUpdated = animalRepository.save(animalUpdate);
                return new ResponseEntity<>(animalUpdated, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping(value = "api/animal/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable(value = "id") long id){
        Optional<Animal> animal = animalRepository.findById(id);
        if(animal.isPresent()){
            try {
                animalRepository.delete(animal.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
