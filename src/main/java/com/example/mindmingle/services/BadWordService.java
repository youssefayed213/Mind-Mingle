package com.example.mindmingle.services;

import com.example.mindmingle.entities.BadWord;
import com.example.mindmingle.entities.Commentaire;
import com.example.mindmingle.repositories.BadWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadWordService {
    private final BadWordRepository badWordRepository;
    public List<BadWord> getAll() {
        return badWordRepository.findAll();
    }
    public void delete(long BadWordId) throws Exception {
        badWordRepository.findById(BadWordId).orElseThrow(() -> new Exception("BadWord not found"));
        badWordRepository.deleteById(BadWordId);
    }


    public BadWord addBadWord(BadWord b) {

        return badWordRepository.save(b);
    }
    public boolean containsBadWord(String content) {
        List<BadWord> badWords = badWordRepository.findAll();

        for (BadWord badWord : badWords) {
            if (content.contains(badWord.getContent())) {
                return true;
            }
        }

        return false;
    }
    public List<BadWord> retreiveAllBadWords() {

        return badWordRepository.findAll();
    }

}
