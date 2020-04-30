/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.controller;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model.FeedRepository;
import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model.TopTag;
import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.read.CreateID;
import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.read.MultiRSSAnalyzer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jamar
 */
@RestController
@EnableAutoConfiguration
public class MainController {

    private FeedRepository feedRepository;
    private static final int TOP = 3;
    
    @RequestMapping(value = "/analyse/new", method = RequestMethod.POST)
    public List<String> getNewAnalyse(@RequestBody com.fasterxml.jackson.databind.JsonNode urls) {
        List<String> list = new ArrayList<String>();
        String idReq = CreateID.createID();
        try {

            String jsonText = urls.toPrettyString();
            MultiRSSAnalyzer mr = new MultiRSSAnalyzer(jsonText);
            
            feedRepository.insertRequest(idReq);
            feedRepository.insertListNews(idReq, mr.getTopNews());
            System.out.println("ID: ["+idReq+"]");
        } catch (ParseException | APIException | AlgorithmException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        list.add(idReq);
        return list;
    }

    @GetMapping(value = "/frequency/{id}")
    public List<TopTag> getRequestById(
            @PathVariable("id") String id) {
        
        List<TopTag> listTags = feedRepository.getTopTag(id, TOP);
        
        return listTags;
    }

    @Autowired
    @Qualifier("feedRepository")
    public void setFeedRepository(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    

}
