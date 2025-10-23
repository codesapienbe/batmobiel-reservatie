package io.github.codesapienbe.user;

import io.github.codesapienbe.auth.JwtProvider;
import io.github.codesapienbe.user.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private JwtProvider jwtProvider;

    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserService(repo, jwtProvider);
    }

    @Test
    void register_createsUserAndReturnsToken() {
        var req = new RegisterRequest("alice", "password123");
        when(repo.existsByUsername("alice")).thenReturn(false);
        when(jwtProvider.generateToken("alice")).thenReturn("tok");
        var resp = service.register(req);
        assertThat(resp.token()).isEqualTo("tok");
        verify(repo).save(any());
    }

    @Test
    void verifyCredentials_falseWhenNotFound() {
        when(repo.findByUsername("bob")).thenReturn(Optional.empty());
        var ok = service.verifyCredentials(new io.github.codesapienbe.user.dto.AuthRequest("bob","pw"));
        assertThat(ok).isFalse();
    }
}


