package uguu.gao.wafu.javaMAL;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 19/06/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnimeEmbedded extends Anime {

    protected String role;

    public AnimeEmbedded(Integer id, String title, String role, String thumbUrl, String imageUrl) {
        this.id = id;
        this.title = title;
        this.role = role;
        this.thumbUrl = thumbUrl;
        this.imageUrl = imageUrl;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
