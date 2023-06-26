package com.example.demo.controller;

import com.example.demo.dto.RatingDto;
import com.example.demo.entity.Rating;
import com.example.demo.service.RatingService;

//import com.example.demo.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/ratebook")
    public ResponseEntity<Rating> rateBook(@RequestBody RatingDto ratingDto) {
        Rating savedRating = ratingService.saveRating(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable long id) {
        Optional<Rating> rating = ratingService.findRatingById(id);
        return rating.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/average/{bookId}")
    public ResponseEntity<Double> getAverageRatingByIsbn(@PathVariable long bookId) {
        double averageRating = ratingService.calculateAverageRatingByIsbn(bookId);
        return ResponseEntity.ok(averageRating);
    }
    
}


