package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Exame;
import br.com.devantenor.clinivet.repositories.ExameRepository;
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
@RequestMapping(value = "/exames")
@Api(value = "exame", description = "Endpoint's referentes a entidade Exame")
public class ExameController {
    @Autowired
    private ExameRepository exameRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos as exames")
    public ResponseEntity<List<Exame>> findAll() {
        List<Exame> exame = exameRepository.findAll();
        return ResponseEntity.ok(exame);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma exame pelo seu ID")
    public ResponseEntity<Exame> findById(@PathVariable Long id) {
        Exame exame = exameRepository.findById(id).get();
        return ResponseEntity.ok(exame);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere uma nova exame")
    public Exame insert(@RequestBody Exame exame) {
        return exameRepository.save(exame);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita uma exame existente pelo seu ID")
    public ResponseEntity<Exame> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> exameMap) throws Exception {
        Exame exameById = exameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(exameById, exameMap, Exame.class);

        return ResponseEntity.ok(exameRepository.save(exameById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta uma exame pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Exame exameById = exameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        exameRepository.delete(exameById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}