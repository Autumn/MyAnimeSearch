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
 * Date: 18/06/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterSearchResults {

    protected CharacterSearchResult[] searchResults;

    public CharacterSearchResults(String siteText) {
        searchResults = null;
        scrapeSearchResults(siteText);
    }

    public void scrapeSearchResults(String siteText) {
        Document doc = Jsoup.parse(siteText);

        if (doc.select("td:containsOwn(No results found)").size() == 1) {
            return;
        }

        Elements rows = doc.select("table").get(1).select("tr");
        rows.remove(0);
        ArrayList<CharacterSearchResult> characterList = new ArrayList<CharacterSearchResult>();
        for (Element row : rows) {
            Elements cols = row.select("td");

            Element imageNode = cols.get(0);
            CharacterSearchResult character = new CharacterSearchResult();
            character.setId(Utility.idFromUrl(imageNode.select("a").attr("href")));
            character.setThumbUrl(imageNode.select("img").attr("src"));
            character.setImageUrl(Utility.imageUrlFromThumbUrl(character.getThumbUrl(), 't'));

            Element nameNode = cols.get(1);
            character.setName(nameNode.select("a").text());
            character.setRole(nameNode.select("small").text());

            Elements titlesNodes = cols.get(2).select("a");
            ArrayList<AnimeEmbedded> animeList = new ArrayList<AnimeEmbedded>();
            ArrayList<MangaEmbedded> mangaList = new ArrayList<MangaEmbedded>();
            for (Element link : titlesNodes) {
                Pattern animePattern = Pattern.compile("http://myanimelist.net/anime/(\\d+)/.*");
                Pattern mangaPattern = Pattern.compile("http://myanimelist.net/manga/(\\d+)/.*");
                Matcher animeMatch = animePattern.matcher(link.attr("href"));
                Matcher mangaMatch = mangaPattern.matcher(link.attr("href"));
                if (animeMatch.find()) {
                    animeList.add(new AnimeEmbedded(Utility.idFromUrl(link.attr("href")), link.text(), null, null, null));
                } else if (mangaMatch.find()) {
                    mangaList.add(new MangaEmbedded(Utility.idFromUrl(link.attr("href")), link.text(), null, null, null));
                }
            }
            Object[] objectArray = animeList.toArray();
            character.setAnime(Arrays.copyOf(objectArray, objectArray.length, AnimeEmbedded[].class));

            objectArray = mangaList.toArray();
            character.setManga(Arrays.copyOf(objectArray, objectArray.length, MangaEmbedded[].class));
            characterList.add(character);
        }
        Object[] objectArray = characterList.toArray();
        searchResults = Arrays.copyOf(objectArray, objectArray.length, CharacterSearchResult[].class);
    }

    public CharacterSearchResult[] getSearchResults() {
        return searchResults;
    }
}
