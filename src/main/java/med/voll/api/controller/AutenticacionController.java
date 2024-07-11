package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacionUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DatosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity realizarLogin(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        var authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),datosAutenticacionUsuario.clave());
        var authentication = authenticationManager.authenticate(authToken);
        System.out.println("A punto de generar token");
        var tokenJWT = tokenService.generarToken((Usuario) authentication.getPrincipal());
        System.out.println("Generado el token " + tokenJWT);
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
