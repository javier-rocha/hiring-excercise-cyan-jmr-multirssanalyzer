/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model;

import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.read.CreateID;

/**
 *
 * @author jamar
 */
public class Tag implements Comparable<Tag> {

    private int frequency;
    private String tag;
    private String idTag;
    
    
    public Tag (String tag){
        this.tag=tag;
        this.idTag=CreateID.createID();
    }
    public Tag (String tag, int frequency){
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
    public int compareTo(Tag o) {
        return o.getFrequency() - this.frequency;
    }

    @Override
    public String toString() {
        return "{"+this.tag + (this.frequency>0?" -> "+ this.frequency + "}":"}");
    }
}
