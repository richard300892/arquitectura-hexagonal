package com.alediesme.joyeria.security.adapter.out.jwt;

import com.alediesme.joyeria.security.exception.InvalidTokenException;
import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.port.out.TokenProvider;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.Username;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements TokenProvider {

  private static final String ROLES_CLAIM = "roles";

  private final JwtProperties jwtProperties;
  private final SecretKey secretKey;

  public JwtTokenProvider(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String generate(User user) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + jwtProperties.getExpirationMs());

    List<String> roles = user.getRoles().stream().map(Role::value).collect(Collectors.toList());

    return Jwts.builder()
        .setSubject(user.getUsername().value())
        .claim(ROLES_CLAIM, roles)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  @SuppressWarnings("unchecked")
  public AuthenticatedUser parse(String token) {
    try {
      Claims claims =
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

      Username username = Username.of(claims.getSubject());
      List<String> roleNames = claims.get(ROLES_CLAIM, List.class);

      Set<Role> roles = new HashSet<>();
      if (roleNames != null) {
        for (String roleName : roleNames) {
          roles.add(Role.of(roleName));
        }
      }

      return new AuthenticatedUser(username, roles);
    } catch (Exception exception) {
      throw new InvalidTokenException();
    }
  }
}
