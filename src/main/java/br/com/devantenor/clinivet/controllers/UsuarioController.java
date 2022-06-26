package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Usuario;
import br.com.devantenor.clinivet.repositories.UsuarioRepository;
import br.com.devantenor.clinivet.services.UsuarioService;
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
@RequestMapping(value = "/usuarios")
@Api(value = "usuarios", description = "Endpoint's referentes a entidade Usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService = new UsuarioService();

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Retorna uma lista com todos os usuários")
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Retorna um usuário pelo seu ID")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        return ResponseEntity.ok(usuario);
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Edita um usuário existente pelo seu ID")
    public ResponseEntity<Usuario> edit(@PathVariable Long id, @RequestBody LinkedHashMap<String, Object> usuarioMap) throws Exception {
        Usuario usuarioById = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        EntityUtils.editEntityClassByMap(usuarioById, usuarioMap, Usuario.class);

        usuarioById = usuarioService.encryptUserPassword(usuarioById);

        return ResponseEntity.ok(usuarioRepository.save(usuarioById));
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Insere um novo usuário")
    public Usuario insert(@RequestBody Usuario usuario) {
        if(usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new Error("Já existe um usuário cadastrado com este email!");
        }

        usuarioService.encryptUserPassword(usuario);

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Deleta um usuário pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Usuario usuarioById = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        usuarioRepository.delete(usuarioById);
        return ResponseEntity.ok(Constants.Messages.DELETADO_COM_SUCESSO);
    }
}