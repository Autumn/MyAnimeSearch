package uguu.gao.wafu.myanimesearch;

import java.util.ArrayList;
import java.util.Arrays;

import uguu.gao.wafu.javaMAL.AnimeSearch;
import uguu.gao.wafu.javaMAL.AnimeSearchResult;
import uguu.gao.wafu.javaMAL.AnimeSearchResults;
import uguu.gao.wafu.javaMAL.CharacterSearch;
import uguu.gao.wafu.javaMAL.CharacterSearchResult;
import uguu.gao.wafu.javaMAL.CharacterSearchResults;
import uguu.gao.wafu.javaMAL.PeopleSearch;
import uguu.gao.wafu.javaMAL.PeopleSearchResult;
import uguu.gao.wafu.javaMAL.PeopleSearchResults;

/**
 * Created by aki on 13/09/13.
 */
public class Searcher {

    private static Searcher me = null;

    public String query;
    public AnimeSearchResults animeSearchResults;
    public CharacterSearchResults charSearchResults;
    public PeopleSearchResults peopleSearchResults;

    public static Searcher instanceOf() {
        if (me == null) {
            me = new Searcher();
        }
        return me;
    }

    private Searcher() {

    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void performQuery() {
        animeSearchResults = new AnimeSearch().searchByQuery(query);
        charSearchResults = new CharacterSearch().searchByQuery(query);
        peopleSearchResults = new PeopleSearch().searchByQuery(query);
    }

    public ArrayList<AnimeSearchResult> getAnimeSearchResults() {
        return new ArrayList<AnimeSearchResult>(Arrays.asList(animeSearchResults.getSearchResults()));
    }

    public ArrayList<CharacterSearchResult> getCharacterSearchResults() {
        return new ArrayList<CharacterSearchResult>(Arrays.asList(charSearchResults.getSearchResults()));
    }

    public ArrayList<PeopleSearchResult> getPeopleSearchResults() {
        return new ArrayList<PeopleSearchResult>(Arrays.asList(peopleSearchResults.getSearchResults()));
    }

}
