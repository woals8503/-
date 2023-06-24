package com.oneline.shimpyo.security.oAuth.test;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2Service {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public void socailLogin(String code, String registrationId) {
        log.info("======================================================");

        // code로 Access 토큰 발급
        String accessToken = getAccessToken(code, registrationId);
        
        // 발급한 Access 토큰으로 유저 정보 가져오기
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);

        UserResource userResource = new UserResource();
        log.info("userResource = {}", userResource);
        switch (registrationId) {
            case "google": {
                userResource.setId(userResourceNode.get("id").asText());
                userResource.setEmail(userResourceNode.get("email").asText());
                userResource.setNickname(userResourceNode.get("name").asText());
                break;
            } case "kakao": {
                userResource.setId(userResourceNode.get("id").asText());
                userResource.setEmail(userResourceNode.get("kakao_account").get("email").asText());
                userResource.setNickname(userResourceNode.get("kakao_account").get("profile").get("nickname").asText());
                break;
            } case "naver": {
                userResource.setId(userResourceNode.get("response").get("id").asText());
                userResource.setEmail(userResourceNode.get("response").get("email").asText());
                userResource.setNickname(userResourceNode.get("response").get("nickname").asText());
                break;
            } default: {
                throw new RuntimeException("UNSUPPORTED SOCIAL TYPE");
            }
        }

        log.info("id = {}", userResource.getId());
        log.info("email = {}", userResource.getEmail());
        log.info("nickname {}", userResource.getNickname());
        log.info("======================================================");
    }
    
    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        System.out.println(accessTokenNode);
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");

        HttpHeaders headers = new HttpHeaders();

        headers.set(AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity entity = new HttpEntity(headers);

        JsonNode body = restTemplate.exchange(resourceUri, GET, entity, JsonNode.class).getBody();
        return body;
    }
}
