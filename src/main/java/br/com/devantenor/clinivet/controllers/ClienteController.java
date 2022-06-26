package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Cliente;
import br.com.devantenor.clinivet.repositories.ClienteRepository;
import br.com.devantenor.clinivet.services.AuthenticationService;
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
@RequestMapping(value = "/clientes")
@Api(value = "clientes", description = "Endpoint's referentes a entidade Cliente")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Retorna uma  lista com todos os clientes")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "', '" + UserType.RoleNames.CLIENTE + "')")
    @ApiOperation(value = "Retorna um cliente pelo seu ID")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        if (authenticationService.loggedUserHasRoleAccess(UserType.VETERINARIO.roleName)
                || authenticationService.loggedUserHasRoleAccess(UserType.ATENDENTE.roleName)
                || authenticationService.getLoggedUser().getId() == id) {

            Cliente cliente = clienteRepository.findById(id).get();
            return ResponseEntity.ok(cliente);

        } else {
            throw new IllegalArgumentException(Constants.Messages.USUARIO_LOGADO_SEM_PERMISSAO);
        }
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" +UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Insere um novo cliente")
    public Cliente insert(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" +UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Edita um cliente existente pelo seu ID")
    public ResponseEntity<Cliente> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> clienteMap) throws Exception {
        Cliente clienteById = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(clienteById, clienteMap, Cliente.class);

        return ResponseEntity.ok(clienteRepository.save(clienteById));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" +UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Deleta um cliente pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Cliente clienteById = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        clienteRepository.delete(clienteById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}