package uguu.gao.wafu.javaMAL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 15/06/13
 * Time: 9:22 AM
 * To change this template use File | Settings | File Templates.
 */

/*
TO DO

- parse dates
- implement iterator and find methods
- extend to implement infinite-search features?
 */

public class AnimeSearchResults {

    protected AnimeSearchResult[] searchResults;

    public AnimeSearchResults(String siteText) {
        searchResults = null;
        scrapeSearchResults(siteText);
    }

    private void scrapeSearchResults(String site) {
        ArrayList<AnimeSearchResult> animeList = new ArrayList<AnimeSearchResult>();
        try {
            Document doc = Jsoup.parse(site);

            if (doc.select("div:containsOwn(No titles that matched your query were found.)").size() == 1) {
                return;
            }

            Elements resultsTable = doc.select("div#content div:eq(1) table");
            Elements resultsRows = resultsTable.select("tr");
            for (Element e: resultsRows) {
                Elements animeTitleNode = e.select("td a strong");
                if (animeTitleNode.size() == 0) {
                    continue;
                }

                AnimeSearchResult anime = new AnimeSearchResult();
                anime.setTitle(animeTitleNode.text());

                Element urlNode = animeTitleNode.parents().first();
                String url = urlNode.attr("href");
                String pattern = "http://myanimelist.net/(\\w+)/(\\d+)/?.*";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(url);
                if (m.find()) {
                    anime.setId(Integer.valueOf(m.group(2)));
                }

                Element imageNode = e.select("td a img").first();
                anime.setThumbUrl(imageNode.attr("src"));
                anime.setImageUrl(Utility.imageUrlFromThumbUrl(anime.getThumbUrl(), 't'));

                Elements tableCellNodes = e.select("td");

                String episodeCount = tableCellNodes.get(3).text();
                if (!episodeCount.matches(".*-.*")) {
                   anime.setEpisodes(Integer.valueOf(episodeCount));
                }

                String membersScore = tableCellNodes.get(4).text();
                if (!membersScore.matches(".*-.*")) {
                    anime.setMembersScore(Float.valueOf(membersScore));
                }
                anime.setType(tableCellNodes.get(2).text());

                // TO DO - parse dates into date type
                // dates may be hyphened
                String startDate = tableCellNodes.get(5).text();
                String endDate = tableCellNodes.get(6).text();
                if (tableCellNodes.size() > 8) {
                    anime.setClassification(tableCellNodes.get(8).text());
                }
                animeList.add(anime);
            }
            Object[] array = animeList.toArray();
            searchResults = Arrays.copyOf(array, array.length, AnimeSearchResult[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AnimeSearchResult[] getSearchResults() {
        return searchResults;
    }
}
