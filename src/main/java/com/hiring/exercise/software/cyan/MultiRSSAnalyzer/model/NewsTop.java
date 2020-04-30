/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jamar
 */
public class NewsTop {

    private String title;
    private String link;
    
    public NewsTop(String title, String link) {
        this.link = link;
        this.title = title;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param description the description to set
     */
    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "\nNews [title=" + getTitle() + "]";
    }
}
