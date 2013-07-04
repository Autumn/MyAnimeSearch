package uguu.gao.wafu.javaMAL;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 14/06/13
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */

/*
anime.php?type=0&score=0&status=0&tag=&p=0&r=0&sm=0&sd=0&sy=0&em=0&ed=0&ey=0&c[0]=a&c[1]=b&c[2]=c&gx=0&o=6&w=1&o=3&w=2&q=clannad
anime.php?c[]=a&c[]=b&c[]=c&c[]=d&c[]=e&c[]=f&c[]=g&q=clannad
anime.php?type=0&score=0&status=0&tag=&p=0&r=0&sm=0&sd=0&sy=0&em=0&ed=0&ey=0&c[0]=a&c[1]=b&c[2]=c&c[]=d&c[]=e&c[]=f&c[]=g&gx=0&o=6&w=1&o=3&w=2&q=clannad

sort by tags
score descending o=3 w=1
score ascending o=3 w=2
start date descending o=2 w=1
start date ascending o=2 w=2
end date ascending o=5 w=2
end date descending o=5 w=1
type special movie tv o=6 w=1
type tv movie special o=6 w=2
types Music ONA Special Movie OVA

*/
public class AnimeSearch {
    
    String baseQuery = "anime.php?c[]=a&c[]=b&c[]=c&c[]=d&c[]=e&c[]=f&c[]=g&q=";
    
    public final int  SORT_NONE = 0;
    public final int  SORT_SCORE_DESCENDING = 1;
    public final int SORT_SCORE_ASCENDING = 2;
    public final int SORT_START_DATE_ASCENDING = 3;
    public final int SORT_START_DATE_DESCENDING = 4;
    public final int SORT_END_DATE_DESCENDING = 5;
    public final int SORT_END_DATE_ASCENDING = 6;
    public final int SORT_TYPE_DESCENDING = 7;
    public final int SORT_TYPE_ASCENDING = 8;
    
    String[] sortTypes = {"", "o=3&w=1", "o=3&w=2", "o=2&w=1","o=2&w=2","o=5&w=2","o=5&w=1","o=6&w=1","o=6&w=2"};;
    
    // TO DO - need to url encode query passed in

    public AnimeSearchResults searchByQuery(String query) {
        return searchByQuery(query, 1);
    }

    public AnimeSearchResults searchByQuery(String query, int page) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {}

        Integer pageQuery = page >= 1 ? (page - 1) * 20 : 0;
        String pageQueryString = "&show" + pageQuery.toString();
        String searchQueryString = baseQuery + query + pageQueryString;
        return new AnimeSearchResults(new Network().connect(searchQueryString));
    }

    /*
    public AnimeSearchResult[] searchByQuerySort(String query, int sortType) {
        return searchByQuerySort(query, 1, sortType);    
    }
    
    public AnimeSearchResult[] searchByQuerySort(String query, int page, int sortType) {
        Integer pageQuery = page >= 1 ? (page - 1) * 20 : 0;
        sortType = sortType < 0 || sortType > 8 ? 0 : sortType;
        String pageQueryString = "&show" + pageQuery.toString();
        String sortQueryString = "&" + sortTypes[sortType];
        String searchQueryString = baseQuery + query + pageQueryString + sortQueryString;
        return new AnimeSearchResults(new Network().connect(searchQueryString)).getSearchResults();    
    }
    */
    
    public AnimeResult searchById(int id) {
        String searchUrl = "anime/" + Integer.toString(id);
        return new AnimeResult(new Network().connect(searchUrl));
    }
}
