package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.ItemReceita;
import br.com.devantenor.clinivet.repositories.ItemReceitaRepository;
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
@RequestMapping(value = "/itensReceita")
@Api(value = "itemReceita", description = "Endpoint's referentes a entidade ItemReceita")
public class ItemReceitaController {
    @Autowired
    private ItemReceitaRepository itemReceitaRepository;

    @GetMapping(value = "/receita/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos os remedios de uma receita")
    public ResponseEntity<List<ItemReceita>> findAllByReceitaId(@PathVariable Long id) {
        List<ItemReceita> itemReceita = itemReceitaRepository.findAllByReceitaId(id);
        return ResponseEntity.ok(itemReceita);
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere um novo remédio a receita")
    public ItemReceita insert(@RequestBody ItemReceita itemReceita) {
        return itemReceitaRepository.save(itemReceita);
    }

    @PutMapping(value = "/edit/remedio/{remedioId}/receita/{receitaId}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita a  prescrição do remédio em uma receita")
    public ResponseEntity<ItemReceita> edit(@PathVariable Long remedioId, @PathVariable Long receitaId, @RequestBody LinkedHashMap<String, Object> itemReceitaMap) throws Exception {
        ItemReceita itemReceitaById = itemReceitaRepository.findByRemedioIdAndReceitaId(remedioId, receitaId);

        if (itemReceitaById == null) {
            throw new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO);
        }

        EntityUtils.editEntityClassByMap(itemReceitaById, itemReceitaMap, ItemReceita.class);

        return ResponseEntity.ok(itemReceitaRepository.save(itemReceitaById));
    }

    @DeleteMapping(value = "/delete/remedio/{remedioId}/receita/{receitaId}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Deleta a prescrição de um remédio de uma receita")
    public ResponseEntity<String> delete(@PathVariable Long remedioId, @PathVariable Long receitaId) {
        ItemReceita itemReceitaById = itemReceitaRepository.findByRemedioIdAndReceitaId(remedioId, receitaId);

        if (itemReceitaById == null) {
            throw new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO);
        }

        itemReceitaRepository.delete(itemReceitaById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}