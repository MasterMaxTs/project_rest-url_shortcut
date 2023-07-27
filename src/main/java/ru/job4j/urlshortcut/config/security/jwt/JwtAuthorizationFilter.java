package ru.job4j.urlshortcut.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static ru.job4j.urlshortcut.util.jwt.JwtConstants.*;

/**
 * Фильтр, выполняющий процедуру авторизации на основе анализа токена в
 * заголовке запроса в защищённый эндпойнт приложения
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * Конструктор
     * @param authenticationManager AuthenticationManager
     */
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * Аутентифицирует пользователя на основе анализа заголовка в запросе
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param chain FilterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
                                        throws IOException, ServletException {
        String header = request.getHeader(headerString);
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                                                        getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * Формирует UsernamePasswordAuthenticationToken на основе парсинга JWT
     * @param header имя заголовка в запросе на входе
     * @return объект UsernamePasswordAuthenticationToken при успешной проверке
     * JWT, иначе null
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        String user = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                         .build()
                         .verify(header.replace(tokenPrefix, ""))
                         .getSubject();
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(
                    user, null, new ArrayList<>()
            );
        }
        return null;
    }
}
