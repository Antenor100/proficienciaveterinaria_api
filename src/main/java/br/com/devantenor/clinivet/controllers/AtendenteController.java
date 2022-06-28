package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Atendente;
import br.com.devantenor.clinivet.repositories.AtendenteRepository;
import br.com.devantenor.clinivet.util.Constants;
import br.com.devantenor.clinivet.util.EntityUtils;
import br.com.devantenor.clinivet.util.enums.UserType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/atendentes")
@Api(value = "atendente", description = "Endpoint's referentes a entidade Atendente")
public class AtendenteController {
    @Autowired
    private AtendenteRepository atendenteRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos as atendentes")
    public ResponseEntity<List<Atendente>> findAll() {
        List<Atendente> atendente = atendenteRepository.findAll();
        return ResponseEntity.ok(atendente);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma atendente pelo seu ID")
    public ResponseEntity<Atendente> findById(@PathVariable Long id) {
        Atendente atendente = atendenteRepository.findById(id).get();
        return ResponseEntity.ok(atendente);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere uma nova atendente")
    public Atendente insert(@RequestBody Atendente atendente) {
        return atendenteRepository.save(atendente);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita uma atendente existente pelo seu ID")
    public ResponseEntity<Atendente> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> atendenteMap) throws Exception {
        Atendente atendenteById = atendenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(atendenteById, atendenteMap, Atendente.class);

        return ResponseEntity.ok(atendenteRepository.save(atendenteById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta uma atendente pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Atendente atendenteById = atendenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        atendenteRepository.delete(atendenteById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}