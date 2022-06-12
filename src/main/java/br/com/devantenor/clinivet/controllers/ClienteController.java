package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Cliente;
import br.com.devantenor.clinivet.repositories.ClienteRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    @ApiOperation(value = "Retorna uma  lista com todos os clientes")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    @ApiOperation(value = "Retorna um cliente pelo seu ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id).get();
        return ResponseEntity.ok(cliente);
    }
}