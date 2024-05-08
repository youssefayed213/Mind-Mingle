package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.BadWord;
import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.services.BadWordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@AllArgsConstructor
public class BadWordController {
    BadWordService badWordService;


    @DeleteMapping("/badword/{badwordId}")
    public void delete(@PathVariable long badwordId) throws Exception {
        badWordService.delete(badwordId);
    }

    @PostMapping("badword/add")
    public ResponseEntity<BadWord> addingComment(@RequestBody BadWord b) {
        try {
            BadWord addedBadWord = badWordService.addBadWord(b);
            return new ResponseEntity<>(addedBadWord, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieve-all-badword")
    public List<BadWord> getBadWords(){
        List <BadWord> listBadWords = (List<BadWord>) badWordService.retreiveAllBadWords();
        return listBadWords;
    }
}
