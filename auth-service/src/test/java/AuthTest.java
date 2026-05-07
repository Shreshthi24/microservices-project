import com.authService.dto.LoginRequest;
import com.authService.dto.RegisterRequest;
import com.authService.entity.AuthUser;
import com.authService.kafka.AuthKafkaProducer;
import com.authService.repositories.AuthRepository;
import com.authService.security.JwtUtil;
import com.authService.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @Mock
    private AuthRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthKafkaProducer kafkaProducer;

    @InjectMocks
    private AuthService service;

    // =========================
    // REGISTER TEST
    // =========================
    @Test
    void testRegisterUser() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("12345");

        AuthUser savedUser = new AuthUser();
        savedUser.setEmail("test@gmail.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole("USER");

        when(encoder.encode("12345")).thenReturn("encodedPassword");
        when(repo.save(any(AuthUser.class))).thenReturn(savedUser);

        AuthUser result = service.register(request);

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("USER", result.getRole());

        verify(repo, times(1)).save(any(AuthUser.class));
    }

    // =========================
    // LOGIN SUCCESS TEST
    // =========================
    @Test
    void testLoginSuccess() {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("12345");

        AuthUser user = new AuthUser();
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        when(repo.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(encoder.matches("12345", "encodedPassword"))
                .thenReturn(true);

        when(jwtUtil.generateToken("test@gmail.com"))
                .thenReturn("jwt-token");

        String token = service.login(request);

        assertNotNull(token);
        assertEquals("jwt-token", token);

        verify(kafkaProducer, times(1))
                .sendAuthEvent("test@gmail.com|SUCCESS|LOGIN");
    }

    // =========================
    // LOGIN FAILED TEST
    // =========================
    @Test
    void testLoginFailure() {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("wrongPassword");

        AuthUser user = new AuthUser();
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        when(repo.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(encoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.login(request)
        );

        assertEquals("Invalid credentials", exception.getMessage());

        verify(kafkaProducer, times(1))
                .sendAuthEvent("test@gmail.com|FAILED|LOGIN");
    }
}