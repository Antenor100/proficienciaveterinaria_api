package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Anamnese;
import br.com.devantenor.clinivet.repositories.AnamneseRepository;
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
@RequestMapping(value = "/anamneses")
@Api(value = "anamnese", description = "Endpoint's referentes a entidade Anamnese")
public class AnamneseController {
    @Autowired
    private AnamneseRepository anamneseRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos as anamneses")
    public ResponseEntity<List<Anamnese>> findAll() {
        List<Anamnese> anamnese = anamneseRepository.findAll();
        return ResponseEntity.ok(anamnese);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma anamnese pelo seu ID")
    public ResponseEntity<Anamnese> findById(@PathVariable Long id) {
        Anamnese anamnese = anamneseRepository.findById(id).get();
        return ResponseEntity.ok(anamnese);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere uma nova anamnese")
    public Anamnese insert(@RequestBody Anamnese anamnese) {
        return anamneseRepository.save(anamnese);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita uma anamnese existente pelo seu ID")
    public ResponseEntity<Anamnese> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> anamneseMap) throws Exception {
        Anamnese anamneseById = anamneseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(anamneseById, anamneseMap, Anamnese.class);

        return ResponseEntity.ok(anamneseRepository.save(anamneseById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta uma anamnese pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Anamnese anamneseById = anamneseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        anamneseRepository.delete(anamneseById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}