package uguu.gao.wafu.javaMAL;

public abstract class Anime {
    protected Integer id;
    protected String title;
    protected OtherTitles[] otherTitles;
    protected String synopsis;
    protected String type;
    protected Integer rank;
    protected Integer popularityRank;
    protected String imageUrl;
    protected String thumbUrl;
    protected Integer episodes;
    protected String status;
    protected String startDate;
    protected String endDate;
    protected Genres[] genres;
    protected String[] tags;
    protected String classification;
    protected Float membersScore;
    protected Integer membersCount;
    protected Integer favouritedCount;
    protected RelatedWorks[] relatedWorks;
    protected String[] mangaAdaptations;
    protected String[] prequels;
    protected String[] sequels;
    protected String[] sideStories;
    protected String parentStory;
    protected String[] characterAnime;
    protected String[] spinOffs;
    protected String[] summaries;
    protected String[] alternativeVersions;

    protected String listedAnimeId;
    protected Float score;
    protected String watchedStatus;

    protected Producers[] producers;
    protected CharactersAnime[] characters;
    protected StaffEmbedded[] staff;

    class OtherTitles {
        public String language;
        public String title;
        public OtherTitles(String language, String title) {
            this.language = language;
            this.title = title;
        }

        public String toString() {
            return language + " " + title;
        }
    }

    class Genres {
        Integer id;
        String name;
        public Genres(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        public String toString() {
            return id + " " + name;
        }
    }

    class Producers {
        Integer id;
        String name;
        public Producers(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        public String toString() {
            return id + " " + name;
        }
    }

    public class RelatedWorks {
        public Integer id;
        public String title;
        public String type;

        public RelatedWorks(Integer id, String title, String type) {
            this.id = id;
            this.title = title;
            this.type = type;
        }

        public String toString() {
            return id.toString() + " " + title + " " + type;
        }
    }


    class RelatedAnime {
        public Integer id;
        public String title;
        public String type;

        public RelatedAnime(Integer id, String title, String type) {
            this.id = id;
            this.title = title;
            this.type = type;
        }

        public String toString() {
            return id.toString() + " " + title + " " + type;
        }
    }

    class RelatedManga {
        public Integer id;
        public String title;
        public String type;

        public RelatedManga(Integer id, String title, String type) {
            this.id = id;
            this.title = title;
            this.type = type;
        }

        public String toString() {
            return id.toString() + " " + title + " " + type;
        }
    }

}
