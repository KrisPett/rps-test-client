package com.example.rpstestclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class GameStatusDTO {
    String id;
    String name;
    String move;
    String game;
    String opponentName;
    String opponentMove;

    @JsonCreator
    public GameStatusDTO(
            @JsonProperty("id") String id,
            @JsonProperty("name")  String name,
            @JsonProperty("move")  String move,
            @JsonProperty("game")  String game,
            @JsonProperty("opponentName")  String opponentName,
            @JsonProperty("opponentMove")  String opponentMove
    ) {
        this.id = id;
        this.name = name;
        this.move = move;
        this.game = game;
        this.opponentName = opponentName;
        this.opponentMove = opponentMove;
    }
}
