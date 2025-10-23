package io.github.codesapienbe.auth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {

    @Test
    void generateAndValidateToken() {
        var props = new JwtProperties();
        props.setSecret("dev-secret-key-dev-secret-key-dev");
        props.setExpiration(3600);
        var provider = new JwtProvider(props);
        var token = provider.generateToken("alice");
        assertThat(provider.validate(token)).isTrue();
        assertThat(provider.subject(token)).isEqualTo("alice");
    }
}


