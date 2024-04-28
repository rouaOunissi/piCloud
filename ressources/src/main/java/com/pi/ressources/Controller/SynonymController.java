package com.pi.ressources.Controller;

import com.pi.ressources.Services.SynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SynonymController {

    @Autowired
    private SynonymService synonymService;

    public SynonymController(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    @GetMapping("/api/synonyms")
    public String[] getSynonyms(@RequestParam String word) {
        return synonymService.getSynonyms(word);
    }

}
