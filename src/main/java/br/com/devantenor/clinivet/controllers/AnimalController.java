package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Animal;
import br.com.devantenor.clinivet.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/animais")
public class AnimalController {
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public ResponseEntity<List<Animal>> findAll() {
        List<Animal> animais = animalRepository.findAll();
        return ResponseEntity.ok(animais);
    }

    @GetMapping(value = "/cliente/{id}")
    public ResponseEntity<List<Animal>> findByClienteId(@PathVariable Long id) {
        List<Animal> animais = animalRepository.findAllByClienteId(id);
        return ResponseEntity.ok(animais);
    }
}