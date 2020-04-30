
package com.hiring.exercise.software.cyan.MultiRSSAnalyzer.model;

import java.util.List;

/**
 *
 * @author jamar
 */


public interface FeedRepository {

	public void insertRequest(String idReq);
        
        public void insertListNews(String idReq, List<News> listNews);
        
        public void insertNews(String idReq, News news);
        
        public void insertTagsNews(News news, List<Tag> listTags);
        
        public void insertTopTags(String idReq, List<Tag> listTags);
        
        public List<TopTag> getTopTag(String idReq,int cont);
        
        public List<NewsTop> getTopNews(String idReq, String tag);

}
