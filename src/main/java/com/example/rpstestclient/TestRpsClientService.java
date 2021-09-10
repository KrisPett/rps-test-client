package com.example.rpstestclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class TestRpsClientService {

    Random random = new Random();

    public void startTest(){
        IntStream.range(0,50)
                .boxed()
                .map(integer -> {
            new Thread(this::startGame).start();
            //new Thread(this::joinGame).start();
            return null;
        }).collect(Collectors.toList());
    }

    public void joinGame() {
        RpsClient rpsClient = new RpsClient("http://localhost:8080");

        String token = rpsClient.getToken().get();
        System.out.println("TestRpsClientService.startTest " + token);

        rpsClient.createUser("createdByRPS");

        List<GameStatusDTO> games = Collections.emptyList();

        while(games.isEmpty()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            games = rpsClient.allGames()
                    .collect(Collectors.toList());
        }

        GameStatusDTO selectedGame = games.get(random.nextInt(games.size()));
        GameStatusDTO gameJoinedStatus = rpsClient.joinGame(selectedGame.getId());
        GameStatusDTO gameEndStatus = rpsClient.makeMove(randomMove());

        try {
            while(gameEndStatus.getGame().equals("ACTIVE")){
                Thread.sleep(100);
                gameEndStatus = rpsClient.gameStatus();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("TestRpsClientService.startTest "+gameEndStatus.getGame());

    }

    public void startGame() {
        RpsClient rpsClient = new RpsClient("http://localhost:8080");

        String token = rpsClient.getToken().get();
        System.out.println("TestRpsClientService.startTest " + token);

        rpsClient.createUser("createdByRPS");

        GameStatusDTO gameJoinedStatus = rpsClient.startGame();
        GameStatusDTO gameEndStatus = rpsClient.makeMove(randomMove());

        try {
            while(gameEndStatus.getGame().equals("ACTIVE")){
                Thread.sleep(100);
                gameEndStatus = rpsClient.gameStatus();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("TestRpsClientService.startTest "+gameEndStatus.getGame());

    }

    private Move randomMove(){
        switch(random.nextInt(3)){
            case 0 : return Move.PAPER;
            case 1 : return Move.ROCK;
            case 2 : return Move.SCISSORS;
            default: throw new RuntimeException("FEEEEL");
        }
    }
}
