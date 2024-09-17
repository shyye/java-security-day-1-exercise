package com.booleanuk.api.controllers;

import com.booleanuk.api.models.VideoGame;
import com.booleanuk.api.repositories.VideoGameRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.VideoGameListResponse;
import com.booleanuk.api.responses.VideoGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
public class VideoGameController {
    @Autowired
    private VideoGameRepository repository;

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAll() {
        VideoGameListResponse response = new VideoGameListResponse();
        response.set(this.repository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getSpecific(@PathVariable int id) {
        VideoGame game = this.repository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("A game with this id was not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse response = new VideoGameResponse();
        response.set(game);
        return ResponseEntity.ok(response);
    }
}
