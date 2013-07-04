package uguu.gao.wafu.javaMAL;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 19/06/13
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterSearchResult extends Characters {

    public String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.name = role;
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

    public void setAnime(Anime[] anime) {
        this.anime = anime;
    }

    public Anime[] getAnime() {
        return anime;
    }

    public void setManga(Manga[] manga) {
        this.manga = manga;
    }

    public Manga[] getManga() {
        return manga;
    }


}
