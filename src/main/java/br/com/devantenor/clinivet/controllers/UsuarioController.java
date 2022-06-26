package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.entities.Usuario;
import br.com.devantenor.clinivet.repositories.UsuarioRepository;
import br.com.devantenor.clinivet.services.UsuarioService;
import br.com.devantenor.clinivet.util.Constants;
import br.com.devantenor.clinivet.util.EntityUtils;
import br.com.devantenor.clinivet.util.enums.Estado;
import br.com.devantenor.clinivet.util.enums.UserType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/usuarios")
@Api(value = "usuarios", description = "Endpoint's referentes a entidade Usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService = new UsuarioService();

    @GetMapping
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Retorna uma lista com todos os usuários ativos")
    public ResponseEntity<List<Usuario>> findAllActives() {
        List<Usuario> usuarios = usuarioRepository.findAllAtivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Retorna um usuário pelo seu ID")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(value = "/email/{email}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Verifica se existe um usuário com o email especificado.")
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Não existe um usuário cadastrado com esse email!");
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping(value = "/email/verify/{email}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Verifica se existe um usuário com o email especificado.")
    public ResponseEntity<Map<String, Object>> verifyIfExistsByEmail(@PathVariable String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        JsonObject resposta = new JsonObject();
        resposta.addProperty("email", email);
        resposta.addProperty("emailEmUso", usuario != null);

        return ResponseEntity.ok(new Gson().fromJson(resposta, HashMap.class));
    }

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Insere um novo usuário")
    public Usuario insert(@RequestBody Usuario usuario) {
        if(usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new Error("Já existe um usuário cadastrado com este email!");
        }

        usuario.setDataCadastro(new Date());
        usuario.setEstado(Estado.ATIVO);

        usuarioService.encryptUserPassword(usuario);

        return usuarioRepository.save(usuario);
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

    @PatchMapping(value = "/inactive/{id}")
    @PreAuthorize("hasAnyRole('" + UserType.RoleNames.VETERINARIO + "', '" + UserType.RoleNames.ATENDENTE + "')")
    @ApiOperation(value = "Inativa um usuário pelo seu ID")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Usuario usuarioById = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.Messages.ID_NAO_ENCONTRADO));

        usuarioById.setEstado(Estado.INATIVO);

        usuarioRepository.save(usuarioById);

        return ResponseEntity.ok(Constants.Messages.DESATIVADO_COM_SUCESSO);
    }
}