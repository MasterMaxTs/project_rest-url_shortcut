package ru.job4j.urlshortcut.service.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.util.jwt.JwtToken;

import javax.servlet.http.HttpServletResponse;

/**
 * Реализация сервиса JSON Web Token
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public void createToken(Authentication authResult,
                            HttpServletResponse response) {
        JwtToken.generateToken(authResult, response);
    }
}
