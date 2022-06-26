package br.com.devantenor.clinivet.controllers;

import br.com.devantenor.clinivet.JavaMailComponent;
import br.com.devantenor.clinivet.entities.Usuario;
import br.com.devantenor.clinivet.repositories.UsuarioRepository;
import br.com.devantenor.clinivet.util.NumberUtils;
import br.com.devantenor.clinivet.util.PasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping(value = "/login")
@Api(value = "login", description = "Endpoint's referentes a funcionalidade de login do sistema")
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JavaMailComponent javaMailComponent;

    @PostMapping
    @PreAuthorize("permitAll()")
    @ApiOperation(value = "Autentica o login do usuário")
    public ResponseEntity<String> authenticateUser(@RequestBody LinkedHashMap<String, String> authenticationObj) throws Exception {
        String email = authenticationObj.get("email");
        String password = authenticationObj.get("password");

        Usuario usuario =  usuarioRepository.findByEmail(email);

        if (usuario == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado com este email!");

        int quantidadeRestanteTentativas = 5 - NumberUtils.getIntegerOrZero(usuario.getQuantidadeAcessosIncorretos());

        if (quantidadeRestanteTentativas == 0)  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Quantidade de tentativas inválidas ultrapassadas!");

        if (PasswordUtils.verifyIfBCryptPasswordMatches(password, usuario.getPassword())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    userDetails, password == null ? userDetails.getPassword() : password, userDetails.getAuthorities()));

            usuario.setQuantidadeAcessos(NumberUtils.getIntegerOrZero(usuario.getQuantidadeAcessos()) + 1);

            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Autenticado com sucesso!");

        } else {
            usuario.setQuantidadeAcessosIncorretos(NumberUtils.getIntegerOrZero(usuario.getQuantidadeAcessosIncorretos()) + 1);
            usuarioRepository.save(usuario);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha inválida, você tem mais " + quantidadeRestanteTentativas + " tentativas!");
        }
    }

    @PostMapping("/forgotPassword")
    @PreAuthorize("permitAll()")
    @ExceptionHandler(Exception.class)
    @ApiOperation(value = "Envia uma nova senha ao email do usuário")
    public ResponseEntity<String> forgotPassword(@RequestBody LinkedHashMap<String, String> emailObj) throws Exception {
        String email = emailObj.get("email");

        Usuario usuario =  usuarioRepository.findByEmail(email);

        if (usuario == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado com este email!");

        if (NumberUtils.getIntegerOrZero(usuario.getQuantidadeSubstituicaoSenha()) > 0) {
            throw new Exception("Uma nova senha já foi enviada para o seu email!");

        } else {
            //Setta nova senha aleatória de 8 digitos para o usuário
            String senhaAleatoria = PasswordUtils.getEightDigitsRandomPassword();

            usuario.setPassword(PasswordUtils.encryptPassword(senhaAleatoria));
            usuario.setQuantidadeSubstituicaoSenha(NumberUtils.getIntegerOrZero(usuario.getQuantidadeSubstituicaoSenha()) + 1);
            usuarioRepository.save(usuario);

            javaMailComponent.sendEmailByGmail(email, "Troca de senha automática - clinivet", "Sua senha foi alterada para " + senhaAleatoria);

            return ResponseEntity.ok("Foi enviado um email para " + email.substring(0, 7) + "... com sua nova senha!");
        }
    }
}