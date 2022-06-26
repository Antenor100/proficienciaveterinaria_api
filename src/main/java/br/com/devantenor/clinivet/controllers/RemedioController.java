package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Remedio;
import br.com.devantenor.clinivet.repositories.RemedioRepository;
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
@RequestMapping(value = "/remedios")
@Api(value = "remedio", description = "Endpoint's referentes a entidade Remedio")
public class RemedioController {
    @Autowired
    private RemedioRepository remedioRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos os remédios")
    public ResponseEntity<List<Remedio>> findAll() {
        List<Remedio> remedio = remedioRepository.findAll();
        return ResponseEntity.ok(remedio);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna um remédio pelo seu ID")
    public ResponseEntity<Remedio> findById(@PathVariable Long id) {
        Remedio remedio = remedioRepository.findById(id).get();
        return ResponseEntity.ok(remedio);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere um novo remédio")
    public Remedio insert(@RequestBody Remedio remedio) {
        return remedioRepository.save(remedio);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita um remédio existente pelo seu ID")
    public ResponseEntity<Remedio> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> remedioMap) throws Exception {
        Remedio remedioById = remedioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(remedioById, remedioMap, Remedio.class);

        return ResponseEntity.ok(remedioRepository.save(remedioById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta um remédio pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Remedio remedioById = remedioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        remedioRepository.delete(remedioById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}