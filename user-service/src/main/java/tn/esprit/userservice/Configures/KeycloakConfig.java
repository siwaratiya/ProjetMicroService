package tn.esprit.userservice.Configures;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//    {
//        "realm": "spring-boot-microservices-biochar-realm",
//            "auth-server-url": "http://localhost:8181/",
//            "ssl-required": "external",
//            "resource": "spring-cloud-client",
//            "verify-token-audience": true,
//            "credentials": {
//        "secret": "ggwhksssLSM4srnjvGacJlnQj97O3KSI"
//    },
//        "use-resource-role-mappings": true,
//            "confidential-port": 0
//    }

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.serverUrl}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.clientId}")
    public String clientId;
    @Value("${keycloak.clientSecret}")
    public String clientSecret;
    @Value("${keycloak.userName}")
    private String userName;
    @Value("${keycloak.password}")
    private String password;
    @Value("${keycloak.url_login}")
    public  String urlLogin;
    @Value("${keycloak.url_logout}")
    public  String urlLogout;
    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.authorization-grant-type}")
    public String grantType;
    //@Bean
    public Keycloak getKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .username(userName)
                .password(password)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilder()
                        .connectionPoolSize(10)
                        .build())
                .build();
    }
}


