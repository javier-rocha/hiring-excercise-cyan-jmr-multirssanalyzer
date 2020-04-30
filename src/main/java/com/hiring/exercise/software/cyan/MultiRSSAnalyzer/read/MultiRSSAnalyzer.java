/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.read;

import com.algorithmia.*;
import com.algorithmia.algo.*;
import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model.News;
import com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model.Tag;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author jamar
 */
public class MultiRSSAnalyzer {

    private List<News> allNews = new ArrayList<News>();
    private List<Tag> topTags = new ArrayList<Tag>();
    private String concatNews = "";

    private AlgorithmiaClient client = Algorithmia.client("simWdrEOk+5Ci1oN6TM7lyNZUCZ1");

    public MultiRSSAnalyzer(String jsonUrls) throws ParseException, APIException, AlgorithmException {

        Object obj = new JSONParser().parse(jsonUrls);
        JSONArray ja = (JSONArray) obj;

        Iterator itr1 = ja.iterator();

        while (itr1.hasNext()) {
            String url = (String) itr1.next();
            allNews.addAll(getRSSNews(url));
        }
        topTags.addAll(getGlobalTags(concatNews));
        setAllTags(allNews, topTags);
        
    }

    private void setConcatNews(String stringNews) {
        this.concatNews += stringNews + "\n";
    }

   

    private List<News> getRSSNews(String rssUrl) throws APIException, AlgorithmException, ParseException {
        List<News> listNews = new ArrayList<News>();

        Algorithm algo = client.algo("tags/ScrapeRSS/0.1.6");
        //algo.setTimeout(300L, java.util.concurrent.TimeUnit.SECONDS); //optional
        AlgoResponse result;

        try {
            result = algo.pipe(rssUrl);

            Object obj = new JSONParser().parse(result.asJsonString());
            JSONArray ja = (JSONArray) obj;

            Iterator itr1 = ja.iterator();

            int cont = 1;
            while (itr1.hasNext()) {

                JSONObject jo = (JSONObject) itr1.next();
                String title = (String) jo.get("title");
                String url = (String) jo.get("url");

                News news = new News(title, url);
                listNews.add(news);
                setConcatNews(title);

                cont++;
            }
        } catch (Exception ex) {
            System.err.println("Invalid RSS Format from URL: " + rssUrl);
        }
        return listNews;
    }

    public List<News> getTopNews() {

        return allNews;
    }

    private List<Tag> getGlobalTags(String totalWords) throws APIException, AlgorithmException, ParseException {

        Algorithm algo = client.algo("nlp/AutoTag/1.0.1");
        AlgoResponse result = algo.pipe(totalWords);
        String jsonResult = result.asJsonString();
        Object objTop = new JSONParser().parse(jsonResult);
        JSONArray ja = (JSONArray) objTop;

        Iterator itr1 = ja.iterator();

        List<Tag> listTags = new ArrayList<Tag>();

        while (itr1.hasNext()) {
            StringTokenizer tok = new StringTokenizer(totalWords.toLowerCase());
            int cont = 0;
            String topWord = (String) itr1.next();
            while (tok.hasMoreTokens()) {
                if (tok.nextElement().toString().contains(topWord)) {
                    cont++;
                }
            }
            listTags.add(new Tag(topWord, cont));
        }

        Collections.sort(listTags);
        return listTags;
    }

    private void setAllTags(List<News> news, List<Tag> tags) {
        Iterator itr1 = news.iterator();

        while (itr1.hasNext()) {
            setTags((News) itr1.next(), tags);

        }
    }

    private static News setTags(News news, List<Tag> tags) {
        Iterator it = tags.iterator();
        while (it.hasNext()) {
            Tag tag = (Tag) it.next();
            StringTokenizer tok = new StringTokenizer(news.getTitle().toLowerCase());
            while (tok.hasMoreTokens()) {
                if (tok.nextElement().toString().contains(tag.getTag())) {
                    news.getTags().add(new Tag(tag.getTag()));
                    break;
                }
            }
        }
        return news;
    }

}
