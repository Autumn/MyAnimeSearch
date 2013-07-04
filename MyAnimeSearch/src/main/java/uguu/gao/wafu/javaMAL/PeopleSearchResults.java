package uguu.gao.wafu.javaMAL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 20/06/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class PeopleSearchResults {

    PeopleSearchResult[] searchResults;
    public PeopleSearchResults(String siteText) {
        searchResults = null;
        scrapeSearchResults(siteText);
    }

    private void scrapeSearchResults(String siteText) {
        Document doc = Jsoup.parse(siteText);

        if (doc.select("td:containsOwn(No results returned)").size() == 1) {
            return;
        }

        Elements rows = doc.select("table").get(1).select("tr");
        rows.remove(0);
        ArrayList<PeopleSearchResult> peopleList = new ArrayList<PeopleSearchResult>();
        for (Element row : rows) {
            Elements cols = row.select("td");
            Element imageNode = cols.get(0);
            Element nameNode = cols.get(1);
            PeopleSearchResult person = new PeopleSearchResult();
            person.setThumbUrl(imageNode.select("img").attr("src"));
            person.setImageUrl(Utility.imageUrlFromThumbUrl(person.getThumbUrl(), 'v'));
            person.setId(Utility.idFromUrl(nameNode.select("a").attr("href")));
            person.setName(nameNode.select("a").text());
            peopleList.add(person);
        }
        Object[] objectArray = peopleList.toArray();
        searchResults = Arrays.copyOf(objectArray, objectArray.length, PeopleSearchResult[].class);
    }

    public PeopleSearchResult[] getSearchResults() {
        return searchResults;
    }
}
