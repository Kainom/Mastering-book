package kainom.com.testing.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class KeycloakRoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    @Nullable
    public AbstractAuthenticationToken convert(Jwt jwt) {
        JwtGrantedAuthoritiesConverter defaultAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        Collection<GrantedAuthority> defaultAuthorities = defaultAuthoritiesConverter.convert(jwt);
        Collection<GrantedAuthority> realmAccessRoles = extractRealmAccessRoles(jwt);

        Set<GrantedAuthority> combinedAuthorities = new HashSet<>();
        combinedAuthorities.addAll(defaultAuthorities);
        combinedAuthorities.addAll(realmAccessRoles);
        
        return new AbstractAuthenticationToken(combinedAuthorities) {
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return jwt.getSubject();
            }
        };

    }

    public static List<GrantedAuthority> extractRealmAccessRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess == null) {
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        if (roles == null) {
            return Collections.emptyList();
        }

        System.out.println(roles);

        return roles.stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()))
                .collect(Collectors.toList());
    }

}
