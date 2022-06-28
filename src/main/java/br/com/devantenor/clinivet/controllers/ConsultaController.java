package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Consulta;
import br.com.devantenor.clinivet.repositories.ConsultaRepository;
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
@RequestMapping(value = "/consultas")
@Api(value = "consulta", description = "Endpoint's referentes a entidade Consulta")
public class ConsultaController {
    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos as consultas")
    public ResponseEntity<List<Consulta>> findAll() {
        List<Consulta> consulta = consultaRepository.findAll();
        return ResponseEntity.ok(consulta);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma consulta pelo seu ID")
    public ResponseEntity<Consulta> findById(@PathVariable Long id) {
        Consulta consulta = consultaRepository.findById(id).get();
        return ResponseEntity.ok(consulta);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere uma nova consulta")
    public Consulta insert(@RequestBody Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita uma consulta existente pelo seu ID")
    public ResponseEntity<Consulta> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> consultaMap) throws Exception {
        Consulta consultaById = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(consultaById, consultaMap, Consulta.class);

        return ResponseEntity.ok(consultaRepository.save(consultaById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta uma consulta pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Consulta consultaById = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        consultaRepository.delete(consultaById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}