package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Animal;
import br.com.devantenor.clinivet.repositories.AnimalRepository;
import br.com.devantenor.clinivet.services.AuthenticationService;
import br.com.devantenor.clinivet.util.Constants;
import br.com.devantenor.clinivet.util.EntityUtils;
import br.com.devantenor.clinivet.util.enums.Estado;
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
@RequestMapping(value = "/animais")
@Api(value = "animais", description = "Endpoint's referentes a entidade Animal")
public class AnimalController {
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista com todos os animais")
    public ResponseEntity<List<Animal>> findAll() {
        List<Animal> animais = animalRepository.findAll();
        return ResponseEntity.ok(animais);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna um animal pelo seu ID")
    public ResponseEntity<Animal> findById(@PathVariable Long id) {
        Animal animal = animalRepository.findById(id).get();
        return ResponseEntity.ok(animal);
    }

    @GetMapping(value = "/filtro/nomeclienteanimal/{name}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Retorna uma lista de animais ao informar uma String e compara-lá ao nome dos animais ou clientes")
    public ResponseEntity<List<Animal>> findAllByClienteOrAnimalName(@PathVariable String name) {
        List<Animal> animais = animalRepository.findAllByClienteOrAnimalName(name);
        return ResponseEntity.ok(animais);
    }

    @GetMapping(value = "/cliente/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.CLIENTE + "')")
    @ApiOperation(value = "Retorna uma lista de animais pelo ID do cliente")
    public ResponseEntity<List<Animal>> findAllByClienteId(@PathVariable Long id) {
        if (authenticationService.loggedUserHasRoleAccess(UserType.VETERINARIO.roleName) || authenticationService.getLoggedUser().getId() == id) {
            List<Animal> animais = animalRepository.findAllByClienteId(id);
            return ResponseEntity.ok(animais);

        } else {
            throw new IllegalArgumentException(Constants.Messages.USUARIO_LOGADO_SEM_PERMISSAO);
        }
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Insere um novo animal")
    public Animal insert(@RequestBody Animal animal) {
        return animalRepository.save(animal);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Edita um animal existente pelo seu ID")
    public ResponseEntity<Animal> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> animalMap) throws Exception {
        Animal animalById = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(animalById, animalMap, Animal.class);

        animalById.setEstado(Estado.ATIVO);

        return ResponseEntity.ok(animalRepository.save(animalById));
    }

    @PatchMapping(value = "/inactive/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "')")
    @ApiOperation(value = "Inativa um animal pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Animal animalById = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        animalById.setEstado(Estado.INATIVO);

        animalRepository.save(animalById);

        return ResponseEntity.ok(Constants.Messages.DESATIVADO_COM_SUCESSO);
    }
}