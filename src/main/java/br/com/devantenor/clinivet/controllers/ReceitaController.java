package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Receita;
import br.com.devantenor.clinivet.repositories.ReceitaRepository;
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
@RequestMapping(value = "/receitas")
@Api(value = "receita", description = "Endpoint's referentes a entidade Receita")
public class ReceitaController {
    @Autowired
    private ReceitaRepository receitaRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos as receitas")
    public ResponseEntity<List<Receita>> findAll() {
        List<Receita> receita = receitaRepository.findAll();
        return ResponseEntity.ok(receita);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma receita pelo seu ID")
    public ResponseEntity<Receita> findById(@PathVariable Long id) {
        Receita receita = receitaRepository.findById(id).get();
        return ResponseEntity.ok(receita);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere uma nova receita")
    public Receita insert(@RequestBody Receita receita) {
        return receitaRepository.save(receita);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita uma receita existente pelo seu ID")
    public ResponseEntity<Receita> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> receitaMap) throws Exception {
        Receita receitaById = receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(receitaById, receitaMap, Receita.class);

        return ResponseEntity.ok(receitaRepository.save(receitaById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta uma receita pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Receita receitaById = receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        receitaRepository.delete(receitaById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}