package com.example.pw3trabalhoindividual.repository;

import com.example.pw3trabalhoindividual.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @Query(value = "SELECT a FROM Animal a WHERE a.especie.id = ?1 ORDER BY a.nome")
    List<Animal> findAnimalsbyEspecie(long id);
}
