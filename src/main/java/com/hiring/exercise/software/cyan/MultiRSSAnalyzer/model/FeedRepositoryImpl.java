package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Repository;

@Repository("feedRepository")
public class FeedRepositoryImpl implements FeedRepository {

    private final String INSERT_REQUEST = "INSERT INTO REQUEST VALUES (?)";
    private final String INSERT_NEWS = "INSERT INTO NEWS (NEWS_ID,TITLE,URL,REQ_ID) VALUES (?,?,?,?)";
    private final String INSERT_TAGS = "INSERT INTO TAGS (TAG, NEWS_ID, TAG_ID) VALUES (?,?,?)";
    private final String INSERT_TOP_TAGS = "INSERT INTO TOP_TAGS (TAG, FREQ, REQ_ID, TAG_ID) VALUES (?,?,?,?)";

    private final String SELECT_NEWS_BY_TAG = "SELECT T.TITLE, T.URL FROM NEWS T \n"
            + "INNER JOIN REQUEST T1 ON T.REQ_ID=T1.REQ_ID \n"
            + "INNER JOIN TAGS T2 ON T.NEWS_ID=T2.NEWS_ID \n"
            + "WHERE T1.REQ_ID=? AND T2.TAG=?";
    private final String SELECT_TOP_TAG = "SELECT TOP ? T1.TAG, COUNT(1) AS FREQ FROM TAGS T1 \n"
            + "INNER JOIN NEWS T2 ON T1.NEWS_ID=T2.NEWS_ID \n"
            + "INNER JOIN REQUEST T3 ON T2.REQ_ID=T3.REQ_ID \n"
            + "WHERE T3.REQ_ID=? \n"
            + "GROUP BY T1.TAG ORDER BY 2 DESC";

    private EmbeddedDatabase database;

    @PostConstruct
    private void init() {
        initiate();
    }

    private void initiate() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        database = builder.setType(EmbeddedDatabaseType.H2).addScript("h2/createdb.sql").build();
       
    }

    @Override
    public void insertRequest(String idReq) {
        try (Connection con = database.getConnection()) {
            PreparedStatement prep = con.prepareStatement(INSERT_REQUEST);
            prep.setString(1, idReq);
            prep.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertListNews(String idReq, List<News> listNews) {
        for (News news : listNews) {
            insertNews(idReq, news);
        }
    }

    @Override
    public void insertNews(String idReq, News news) {
        try (Connection con = database.getConnection()) {
            PreparedStatement prep = con.prepareStatement(INSERT_NEWS);
            prep.setString(1, news.getNewsId());
            prep.setString(2, news.getTitle());
            prep.setString(3, news.getLink());
            prep.setString(4, idReq);
            prep.execute();
            insertTagsNews(news, news.getTags());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertTagsNews(News news, List<Tag> listTags) {
        for (Tag tag : listTags) {
            try (Connection con = database.getConnection()) {
                PreparedStatement prep = con.prepareStatement(INSERT_TAGS);
                prep.setString(1, tag.getTag());
                prep.setString(2, news.getNewsId());
                prep.setString(3, tag.getIdTag());
                prep.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void insertTopTags(String idReq, List<Tag> listTags) {
        for (Tag tag : listTags) {
            try (Connection con = database.getConnection()) {
                PreparedStatement prep = con.prepareStatement(INSERT_TOP_TAGS);
                prep.setString(1, tag.getTag());
                prep.setInt(2, tag.getFrequency());
                prep.setString(3, idReq);
                prep.setString(4, tag.getIdTag());
                prep.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<TopTag> getTopTag(String idReq, int cont) {
        List<TopTag> list = new ArrayList<TopTag>();
        try (Connection con = database.getConnection()) {
            PreparedStatement prep = con.prepareStatement(SELECT_TOP_TAG);
            prep.setInt(1, cont);
            prep.setString(2, idReq);
            ResultSet result = prep.executeQuery();
            while (result.next()) {
                String tag = result.getString(1);
                int freq = result.getInt(2);
                TopTag ttag = new TopTag(tag, freq);
                ttag.setListNews(getTopNews(idReq, tag));
                list.add(ttag);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<NewsTop> getTopNews(String idReq, String tag) {
        List<NewsTop> list = new ArrayList<NewsTop>();
        try (Connection con = database.getConnection()) {
            PreparedStatement prep = con.prepareStatement(SELECT_NEWS_BY_TAG);
            prep.setString(1, idReq);
            prep.setString(2, tag);
            ResultSet result = prep.executeQuery();
            while (result.next()) {
                String title = result.getString(1);
                String url = result.getString(2);
                list.add(new NewsTop(title, url));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
