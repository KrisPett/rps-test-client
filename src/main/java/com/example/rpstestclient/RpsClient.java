package com.example.rpstestclient;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Stream;

public class RpsClient {
    RestTemplate restTemplate = new RestTemplate();
    String baseUrl;
    String token;

    public RpsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Optional<String> getToken() {
        if (this.token != null) {
            return Optional.of(this.token);
        }
        return Optional.ofNullable(fetchNewTokenAndStore());
    }

    public Stream<GameStatusDTO> allGames() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", getToken().get());

        ResponseEntity<GameStatusDTO[]> exchange = restTemplate.exchange(
                url("games/"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GameStatusDTO[].class);

        GameStatusDTO[] games = exchange.getBody();
        if (games == null) {
            return Stream.empty();
        }
        return Stream.of(games);
    }

    public GameStatusDTO createUser(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", getToken().get());
        HttpEntity<String> request = new HttpEntity<>(name, headers);
        ResponseEntity<GameStatusDTO> exchange = restTemplate.exchange(
                url("user/name/"),
                HttpMethod.POST,
                request,
                GameStatusDTO.class);

        return exchange.getBody();
    }


    public GameStatusDTO joinGame(String gameId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", getToken().get());
        HttpEntity<GameStatusDTO> request = new HttpEntity<>(headers);
        ResponseEntity<GameStatusDTO> exchange = restTemplate.exchange(
                url("games/join/" + gameId),
                HttpMethod.GET,
                request,
                GameStatusDTO.class);

        return exchange.getBody();
    }

    public GameStatusDTO makeMove(Move move) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", getToken().get());
        HttpEntity<GameStatusDTO> request = new HttpEntity<>(headers);
        ResponseEntity<GameStatusDTO> exchange = restTemplate.exchange(
                url("games/move/" + move.name()),
                HttpMethod.GET,
                request,
                GameStatusDTO.class);

        return exchange.getBody();
    }

    public GameStatusDTO gameStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", getToken().get());
        HttpEntity<GameStatusDTO> request = new HttpEntity<>(headers);
        ResponseEntity<GameStatusDTO> exchange = restTemplate.exchange(
                url("games/status/"),
                HttpMethod.GET,
                request,
                GameStatusDTO.class);

        return exchange.getBody();
    }

    public GameStatusDTO startGame() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", getToken().get());
        HttpEntity<GameStatusDTO> request = new HttpEntity<>(headers);
        ResponseEntity<GameStatusDTO> exchange = restTemplate.exchange(
                url("games/start/"),
                HttpMethod.GET,
                request,
                GameStatusDTO.class);

        return exchange.getBody();
    }

    private String fetchNewTokenAndStore() {
        this.token = restTemplate.getForObject(url("auth/token"), String.class);
        return this.token;
    }

    private String url(String path) {
        //http//:localhost:8080/
        return baseUrl + path;
    }
}
