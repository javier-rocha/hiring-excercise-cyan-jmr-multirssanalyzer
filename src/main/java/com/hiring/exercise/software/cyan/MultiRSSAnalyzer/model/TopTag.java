/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model;

import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.read.CreateID;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jamar
 */
public class TopTag implements Comparable<TopTag> {

    private int frequency;
    private String tag;
    private List<NewsTop> listNews = new ArrayList<NewsTop>();
    private String idTag;
    
    public TopTag (String tag){
        this.tag=tag;
    }
    public TopTag (String tag, int frequency){
        this.tag=tag;
        this.frequency=frequency;
        this.idTag=CreateID.createID();
    }
    
    public String getIdTag() {
        return idTag;
    }
    /**
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    

    @Override
    public int compareTo(TopTag o) {
        return o.getFrequency() - this.frequency;
    }

    @Override
    public String toString() {
        return "{"+this.tag + (this.frequency>0?" -> "+ this.frequency + "}":"}");
    }

    /**
     * @return the listNews
     */
    public List<NewsTop> getListNews() {
        return listNews;
    }

    /**
     * @param listNews the listNews to set
     */
    public void setListNews(List<NewsTop> listNews) {
        this.listNews = listNews;
    }
}
