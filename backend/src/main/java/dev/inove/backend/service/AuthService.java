package dev.inove.backend.service;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.inove.backend.DTO.LoginResponse;
import dev.inove.backend.model.AuthUser;
import dev.inove.backend.repository.AuthUserRepository;
import dev.inove.backend.util.JwtUtil;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthUserRepository authUserRepo;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Realiza o login de um usuário.
     * 
     * @param email email do usuário
     * @param senha senha do usuário
     * @return um token JWT caso o login seja bem-sucedido, ou null
     *         caso contrário
     */
    public LoginResponse login(String email, String senha) {
        LOGGER.info("Realizando login do usuário com email: {}", email);
        Optional<AuthUser> userOpt = authUserRepo.findByEmail(email);

        if (userOpt.isPresent() && userOpt.get().getSenha().equals(senha)) {
            AuthUser user = userOpt.get();
            String token = jwtUtil.generateToken(user);
            return new LoginResponse(user.getNome(), token);
        }

        LOGGER.info("Erro ao realizar login do usuário com email: {}", email);
        return null;
    }

    /**
     * Registra um novo usuário.
     * 
     * @param user dados do usuário a ser registrado
     * @return o usuário registrado
     * @throws RuntimeException se o email do usuário já estiver cadastrado
     */
    public AuthUser register(AuthUser user) {
        LOGGER.info("Realizando registro do usuário com email: {}", user.getEmail());
        if (authUserRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        return authUserRepo.save(user);
    }

    public Optional<AuthUser> findByEmail(String email) {
        return authUserRepo.findByEmail(email);
    }

}
