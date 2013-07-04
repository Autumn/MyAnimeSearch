package uguu.gao.wafu.javaMAL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 18/06/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterResult extends Characters {


    public CharacterResult(String siteText) {
        scrapeResult(siteText);
    }

    private void scrapeResult(String siteText) {
        try {
            Document doc = Jsoup.parse(siteText);
            Element detailsLink = doc.select("a:containsOwn(Details)").get(0);
            setId(Utility.idFromUrl(detailsLink.attr("href")));
            setName(doc.select("h1").text());
            setThumbUrl(doc.select("div#content tr td div img").get(0).attr("src")); // image
            setImageUrl(Utility.imageUrlFromThumbUrl(getThumbUrl(), 'v'));

            Elements leftColumnNodeset = doc.select("div#content table tr td.borderClass");
            Element animeTable = leftColumnNodeset.select("div:containsOwn(Animeography").get(0).nextElementSibling();
            Element mangaTable = leftColumnNodeset.select("div:containsOwn(Mangaography").get(0).nextElementSibling();

            ArrayList<AnimeEmbedded> animeList = new ArrayList<AnimeEmbedded>();
            for (Element animeNode : animeTable.select("tr")) {
                Elements infoNodes = animeNode.select("td");
                Element imageNode = infoNodes.get(0);
                Element titleNode = infoNodes.get(1);
                Integer id = Utility.idFromUrl(imageNode.select("a").get(0).attr("href"));
                String thumbUrl = imageNode.select("img").get(0).attr("src");
                String imageUrl = Utility.imageUrlFromThumbUrl(imageNode.select("img").get(0).attr("src"), 'v');
                String name = titleNode.select("a").get(0).text();
                String role = titleNode.select("small").get(0).text();
                AnimeEmbedded anime = new AnimeEmbedded(id, name, role, thumbUrl, imageUrl);
                animeList.add(anime);
            }

            Object[] objectArray = animeList.toArray();
            setAnime(Arrays.copyOf(objectArray, objectArray.length, AnimeEmbedded[].class));

            ArrayList<MangaEmbedded> mangaList = new ArrayList<MangaEmbedded>();
            for (Element mangaNode : mangaTable.select("tr")) {
                Elements infoNodes = mangaNode.select("td");
                Element imageNode = infoNodes.get(0);
                Element titleNode = infoNodes.get(1);
                Integer id = Utility.idFromUrl(imageNode.select("a").get(0).attr("href"));
                String thumbUrl = imageNode.select("img").get(0).attr("src");
                String imageUrl = Utility.imageUrlFromThumbUrl(imageNode.select("img").get(0).attr("src"), 'v');
                String name = titleNode.select("a").get(0).text();
                String role = titleNode.select("small").get(0).text();
                MangaEmbedded manga = new MangaEmbedded(id, name, role, thumbUrl, imageUrl);
                mangaList.add(manga);

            }

            objectArray = mangaList.toArray();
            setManga(Arrays.copyOf(objectArray, objectArray.length, MangaEmbedded[].class));

            Elements contentDivs = doc.select("div.normal_header");
            Element nameDiv = contentDivs.get(2);
            Element seiyuuDiv = contentDivs.get(3);

            setEngName(nameDiv.childNode(0).toString().trim()); // eng name
            setJpName((nameDiv.childNode(1).toString())); // jp name

            StringBuilder bio = new StringBuilder();
            Node bioNode = nameDiv.nextSibling();
            while (!bioNode.equals(seiyuuDiv)) {
                bio.append(bioNode.toString());
                bioNode = bioNode.nextSibling();
            }
            setBio(bio.toString());

            ArrayList<SeiyuuEmbedded> seiyuuList = new ArrayList<SeiyuuEmbedded>();
            seiyuuDiv = seiyuuDiv.nextElementSibling();

            while (!seiyuuDiv.nodeName().equals("br")) {
                Element imageNode = seiyuuDiv.select("td").get(0);
                Element titleNode = seiyuuDiv.select("td").get(1);
                Integer id = Utility.idFromUrl(imageNode.select("a").get(0).attr("href"));
                String thumbUrl = imageNode.select("img").get(0).attr("src");
                String imageUrl = Utility.imageUrlFromThumbUrl(imageNode.select("img").get(0).attr("src"), 'v');
                String name = titleNode.select("a").get(0).text();
                String nation = titleNode.select("small").get(0).text();
                SeiyuuEmbedded seiyuu = new SeiyuuEmbedded(id, name, nation, thumbUrl, imageUrl);
                seiyuuList.add(seiyuu);
                seiyuuDiv = seiyuuDiv.nextElementSibling();
            }

            objectArray = seiyuuList.toArray();
            setSeiyuu(Arrays.copyOf(objectArray, objectArray.length, SeiyuuEmbedded[].class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getJpName() {
        return jpName;
    }

    public void setJpName(String jpName) {
        this.jpName = jpName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Anime[] getAnime() {
        return anime;
    }

    public void setAnime(Anime[] anime) {
        this.anime = anime;
    }

    public Manga[] getManga() {
        return manga;
    }

    public void setManga(Manga[] manga) {
        this.manga = manga;
    }

    public People[] getSeiyuu() {
        return seiyuu;
    }

    public void setSeiyuu(People[] seiyuu) {
        this.seiyuu = seiyuu;
    }
}
