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
public class News {

    private String title;
    private String link;
    private List<Tag> tags = new ArrayList<Tag>();
    private String newsId;

    public News(String title, String link) {
        this.link = link;
        this.title = title;
        this.newsId = CreateID.createID();
    }

    /**
     * @return the link
     */
    public String getNewsId() {
        return newsId;
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
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
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
        return "\nNews [title=" + getTitle() + "]\n Tags:"+getTags();
    }
}
