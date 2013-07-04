package uguu.gao.wafu.javaMAL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimeResult extends Anime {

    public AnimeResult(String siteText) {
        scrapeAnime(siteText);
    }

    private void scrapeAnime(String site) {
        try {
            Object[] objectArray;

            Document doc = Jsoup.parse(site);
            Element animeIdInput = doc.select("input[name=aid]").get(0);
            setId(Integer.valueOf(animeIdInput.attr("value")));
            setTitle(doc.select("h1").get(0).ownText());
            String rankText = doc.select("h1 > div").text();
            setRank(Integer.valueOf(rankText.replaceAll("\\D", "")));
            setThumbUrl(doc.select("div#content tr td div img").attr("src"));
            setImageUrl(Utility.imageUrlFromThumbUrl(getThumbUrl(), 't'));

            Elements otherEnglishTitles = doc.select("span:containsOwn(English:)");
            Elements otherJapaneseTitles = doc.select("span:containsOwn(Japanese:)");
            Elements otherSynonymTitles = doc.select("span:containsOwn(Synonyms:)");
            ArrayList<OtherTitles> otherTitles = new ArrayList<Anime.OtherTitles>();
            otherTitles.add(new OtherTitles("english", textIfExists(otherEnglishTitles)));
            otherTitles.add(new OtherTitles("japanese", textIfExists(otherJapaneseTitles)));
            otherTitles.add(new OtherTitles("synonyms", textIfExists(otherSynonymTitles)));

            objectArray = otherTitles.toArray();
            setOtherTitles(Arrays.copyOf(objectArray, objectArray.length, OtherTitles[].class));

            Elements typeNodes = doc.select("span:containsOwn(Type:)");
            setType(textIfExists(typeNodes));

            Elements episodesNodes = doc.select("span:containsOwn(Episodes:");
            setEpisodes(Integer.valueOf(textIfExists(episodesNodes)));

            Elements statusNodes = doc.select("span:containsOwn(Status:)");
            setStatus(textIfExists(statusNodes));

            // need to parse the date
            Elements airedNodes = doc.select("span:containsOwn(Aired:)");
            System.out.println(textIfExists(airedNodes));

            Elements producersLinks = doc.select("span:containsOwn(Producers:").parents().get(0).select("a");
            ArrayList<Producers> producers = new ArrayList<Producers>();
            for (Element link : producersLinks) {
                String url = link.attr("href");
                Integer id = Integer.valueOf(url.split("=")[1]);
                String name = link.text();
                producers.add(new Producers(id, name));
            }
            objectArray = producers.toArray();
            setProducers(Arrays.copyOf(objectArray, objectArray.length, Producers[].class));


            Elements genresLinks = doc.select("span:containsOwn(Genres:)").parents().get(0).select("a");
            ArrayList<Genres> genres = new ArrayList<Genres>();
            for (Element link : genresLinks) {
                String url = link.attr("href");
                Integer id = Integer.valueOf(url.split("=")[1]);
                String name = link.text();
                genres.add(new Genres(id, name));
            }

            objectArray = genres.toArray();
            setGenres(Arrays.copyOf(objectArray, objectArray.length, Genres[].class));

            Elements classificationNodes = doc.select("span:containsOwn(Rating:)");
            setClassification(textIfExists(classificationNodes));

            Elements scoreNodes = doc.select("span:containsOwn(Score:)");
            setScore(Float.valueOf(textIfExists(scoreNodes)));

            Elements popularityNodes = doc.select("span:containsOwn(Popularity:)");
            setPopularityRank(Integer.valueOf(textIfExists(popularityNodes).replaceAll("\\W", "")));

            Elements membersNodes = doc.select("span:containsOwn(Members:)");
            setMembersCount(Integer.valueOf(textIfExists(membersNodes).replaceAll(",", "")));

            Elements favouritesNodes = doc.select("span:containsOwn(Favorites:)");
            setFavouritedCount(Integer.valueOf(textIfExists(favouritesNodes).replaceAll(",", "")));

            Elements popularTagsNodes = doc.select("h2:containsOwn(Popular Tags) + span").get(0).select("a");
            ArrayList<String> popularTags = new ArrayList<String>();
            for (Element link : popularTagsNodes) {
                popularTags.add(link.text());
            }
            objectArray = popularTags.toArray();
            setTags(Arrays.copyOf(objectArray, objectArray.length, String[].class));

            Element synopsisNode = doc.select("h2:containsOwn(Synopsis)").get(0);
            StringBuilder sb = new StringBuilder();
            Node synopsis = synopsisNode.nextSibling();

            do {
                sb.append(synopsis.toString());
            } while ((synopsis = synopsis.nextSibling()) != null);
            setSynopsis(sb.toString());

            Elements relatedAnimeNodes = doc.select("h2:containsOwn(Related Anime)");
            Node relatedAnimeNode = relatedAnimeNodes.get(0).nextSibling();

            // type - Adaptation/Prequel/Sequel/Character/Spin-off/Summary/Alternative versions
            // link and name
            // ", " if more, <br /> if finished
            // h2 when completely done

            String type = "";
            ArrayList<RelatedWorks> relatedWorks = new ArrayList<RelatedWorks>();
            while (!relatedAnimeNode.nodeName().equals("h2")) {
                String text = relatedAnimeNode.toString();

                String typePattern = "(Adaptation)|(Prequel)|(Sequel)|(Character)|(Spin-off)|(Summary)|(Alternative version)|(Alternative setting)";
                Pattern typePatternMatcher = Pattern.compile(typePattern);
                Matcher matcher = typePatternMatcher.matcher(text);
                if (matcher.find()) {
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        if (matcher.group(i) != null) {
                            type = matcher.group(i);
                            break;
                        }
                    }
                } else if (relatedAnimeNode.nodeName().equals("a")) {
                    String title = relatedAnimeNode.childNode(0).toString();
                    Integer id = 0;
                    Pattern idPattern = Pattern.compile("http://myanimelist.net/(\\w+)/(\\d+)/?.*");
                    matcher = idPattern.matcher(relatedAnimeNode.attr("href").toString());
                    if (matcher.find()) {
                        id = Integer.valueOf(matcher.group(2));
                    }
                    relatedWorks.add(new RelatedWorks(id, title, type));

                }
                relatedAnimeNode = relatedAnimeNode.nextSibling();
            }
            // TO DO - separate each type of related story into own variable
            objectArray = relatedWorks.toArray();
            setRelatedStories(Arrays.copyOf(objectArray, objectArray.length, RelatedWorks[].class));

            String charactersLink = doc.select("a:containsOwn(More characters)").get(0).attr("href");
            String charactersPage = new Network().connect_url(charactersLink);
            scrapeCharactersStaffPage(charactersPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrapeCharactersStaffPage(String body) {
        Document doc = Jsoup.parse(body);
        Elements nodes = doc.select("h2:containsOwn(Characters & Voice Actors) + table").parents().get(0).select("table");
        Element parentDiv = doc.select("h2:containsOwn(Characters & Voice Actors)").parents().get(0);
        Element staffNode = nodes.get(nodes.size() - 1);
        ArrayList<CharactersAnime> characterList = new ArrayList<CharactersAnime>();
        ArrayList<StaffEmbedded> staffList = new ArrayList<StaffEmbedded>();
        for (Element node : nodes) {
            if (!node.equals(staffNode)) {
                if (!node.parent().equals(parentDiv)) continue; // required so the nested tables are skipped

                Elements characterNodes = node.select("tr").get(0).select("td");
                Element characterInfoNode = characterNodes.get(1);
                Element characterImageNode = characterNodes.get(0);

                String charUrl = characterInfoNode.select("a").get(0).attr("href");
                Integer charId = Utility.idFromUrl(charUrl);
                String charRole = characterInfoNode.select("small").text();
                String charName = characterInfoNode.select("a").get(0).ownText();
                String charThumbUrl = characterImageNode.select("a img").attr("src");
                String charImageUrl = Utility.imageUrlFromThumbUrl(charThumbUrl, 't');

                ArrayList<SeiyuuEmbedded> seiyuuList = new ArrayList<SeiyuuEmbedded>();
                for (Element seiyuuNode : node.select("table tr")) {
                    Elements infoNodes = seiyuuNode.select("td");
                    if (infoNodes.size() > 0) {
                        Element seiyuuInfoNode = infoNodes.get(0);
                        Element seiyuuImageNode = infoNodes.get(1);
                        String seiyuuUrl = seiyuuInfoNode.select("a").attr("href");
                        Integer seiyuuId = Utility.idFromUrl(seiyuuUrl);
                        String seiyuuNation = seiyuuInfoNode.select("small").text();
                        String seiyuuName = seiyuuInfoNode.select("a").text();
                        String seiyuuThumbUrl = seiyuuImageNode.select("a img").attr("src");
                        String seiyuuImageUrl = Utility.imageUrlFromThumbUrl(seiyuuThumbUrl, 'v');
                        seiyuuList.add(new SeiyuuEmbedded(seiyuuId, seiyuuName, seiyuuNation, seiyuuThumbUrl, seiyuuImageUrl));
                    }
                }
                Object[] array = seiyuuList.toArray();
                SeiyuuEmbedded[] seiyuuArray = Arrays.copyOf(array, array.length, SeiyuuEmbedded[].class);
                characterList.add(new CharactersAnime(charId, charName, charRole, charThumbUrl, charImageUrl, seiyuuArray));
            } else {
                for (Element row : node.select("tr")) {
                    Elements staffNodes = row.select("td");
                    String staffUrl = staffNodes.get(0).select("a").attr("href");
                    Integer staffId = Utility.idFromUrl(staffUrl);
                    String staffThumbUrl = staffNodes.get(0).select("img").attr("src");
                    String staffImageUrl = Utility.imageUrlFromThumbUrl(staffThumbUrl, 'v');
                    String staffName = staffNodes.get(1).select("a").text();
                    String staffRole = staffNodes.get(1).select("small").text();
                    staffList.add(new StaffEmbedded(staffId, staffName, staffRole, staffThumbUrl, staffImageUrl));
                }
            }
        }
        Object[] array = characterList.toArray();
        CharactersAnime[] characterArray = Arrays.copyOf(array, array.length, CharactersAnime[].class);
        array = staffList.toArray();
        StaffEmbedded[] staffArray = Arrays.copyOf(array, array.length, StaffEmbedded[].class);
        setCharacters(characterArray);
        setStaff(staffArray);
    }


    private String textIfExists(Elements nodes) {
        if (nodes.size() > 0) {
            return nodes.parents().get(0).ownText();
        }
        return null;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OtherTitles[] getOtherTitles() {
        return otherTitles;
    }

    public void setOtherTitles(OtherTitles[] otherTitles) {
        this.otherTitles = otherTitles;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getPopularityRank() {
        return popularityRank;
    }

    public void setPopularityRank(Integer popularityRank) {
        this.popularityRank = popularityRank;
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

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public void setGenres(Genres[] genres) {
        this.genres = genres;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Float getMembersScore() {
        return membersScore;
    }

    public void setMembersScore(Float membersScore) {
        this.membersScore = membersScore;
    }

    public Integer getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    public Integer getFavouritedCount() {
        return favouritedCount;
    }

    public void setFavouritedCount(Integer favouritedCount) {
        this.favouritedCount = favouritedCount;
    }

    public RelatedWorks[] getRelatedStories() {
        return relatedWorks;
    }

    public void setRelatedStories(RelatedWorks[] relatedStories) {
        this.relatedWorks = relatedStories;
    }


    public String[] getMangaAdaptations() {
        return mangaAdaptations;
    }

    public void setMangaAdaptations(String[] mangaAdaptations) {
        this.mangaAdaptations = mangaAdaptations;
    }

    public String[] getPrequels() {
        return prequels;
    }

    public void setPrequels(String[] prequels) {
        this.prequels = prequels;
    }

    public String[] getSequels() {
        return sequels;
    }

    public void setSequels(String[] sequels) {
        this.sequels = sequels;
    }

    public String[] getSideStories() {
        return sideStories;
    }

    public void setSideStories(String[] sideStories) {
        this.sideStories = sideStories;
    }

    public String getParentStory() {
        return parentStory;
    }

    public void setParentStory(String parentStory) {
        this.parentStory = parentStory;
    }

    public String[] getCharacterAnime() {
        return characterAnime;
    }

    public void setCharacterAnime(String[] characterAnime) {
        this.characterAnime = characterAnime;
    }

    public String[] getSpinOffs() {
        return spinOffs;
    }

    public void setSpinOffs(String[] spinOffs) {
        this.spinOffs = spinOffs;
    }

    public String[] getSummaries() {
        return summaries;
    }

    public void setSummaries(String[] summaries) {
        this.summaries = summaries;
    }

    public String[] getAlternativeVersions() {
        return alternativeVersions;
    }

    public void setAlternativeVersions(String[] alternativeVersions) {
        this.alternativeVersions = alternativeVersions;
    }

    public String getListedAnimeId() {
        return listedAnimeId;
    }

    public void setListedAnimeId(String listedAnimeId) {
        this.listedAnimeId = listedAnimeId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getWatchedStatus() {
        return watchedStatus;
    }

    public void setWatchedStatus(String watchedStatus) {
        this.watchedStatus = watchedStatus;
    }

    public Producers[] getProducers() {
        return producers;
    }

    public void setProducers(Producers[] producers) {
        this.producers = producers;
    }

    public CharactersAnime[] getCharacters() {
        return characters;
    }

    public void setCharacters(CharactersAnime[] characters) {
        this.characters = characters;
    }

    public People[] getStaff() {
        return staff;
    }

    public void setStaff(StaffEmbedded[] staff) {
        this.staff = staff;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId().toString() + "\n");
        sb.append(getTitle() + "\n");
        sb.append(getSynopsis() + "\n");
        sb.append(Arrays.toString(getCharacters()) + "\n");
        return sb.toString();
    }
}
