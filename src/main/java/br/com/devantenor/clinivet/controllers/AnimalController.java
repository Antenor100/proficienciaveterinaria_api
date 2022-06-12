package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Animal;
import br.com.devantenor.clinivet.repositories.AnimalRepository;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Retorna uma lista com todos os animais")
    @GetMapping
    public ResponseEntity<List<Animal>> findAll() {
        List<Animal> animais = animalRepository.findAll();
        return ResponseEntity.ok(animais);
    }

    @ApiOperation(value = "Retorna uma lista de animais pelo ID do cliente")
    @GetMapping(value = "/cliente/{id}")
    public ResponseEntity<List<Animal>> findByClienteId(@PathVariable Long id) {
        List<Animal> animais = animalRepository.findAllByClienteId(id);
        return ResponseEntity.ok(animais);
    }
}