package ru.job4j.urlshortcut.service.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.service.authentication.AuthenticationService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static ru.job4j.urlshortcut.util.jwt.JwtConstants.headerString;
import static ru.job4j.urlshortcut.util.jwt.JwtConstants.tokenPrefix;

@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class JwtServiceTest {

    private JwtService jwtService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AuthenticationManager authManager;

    @Autowired
    private HttpServletResponse response;

    private Authentication authResult;

    private Credential credential;

    @BeforeEach
    void whenSetUp() {
        jwtService = new JwtServiceImpl();
        credential = new Credential("login", "pass");
        authResult = new UsernamePasswordAuthenticationToken(
                             new User(
                                     credential.getLogin(),
                                     credential.getPassword(),
                                     List.of()
                             ), null
        );
    }

    @Test
    void whenAuthIsSuccessThenCreateTokenAndVerifyResponseHeader() {
        doReturn(authResult)
                .when(authenticationService).authenticate(authManager, credential);
        jwtService.createToken(authResult, response);
        assertTrue(response.containsHeader(headerString));
        assertTrue(response.getHeader(headerString).startsWith(tokenPrefix));
    }
}