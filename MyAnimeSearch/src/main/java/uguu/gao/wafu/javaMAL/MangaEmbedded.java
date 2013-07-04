package uguu.gao.wafu.javaMAL;

public class MangaEmbedded extends Manga {

    protected String role;

    public MangaEmbedded(Integer id, String title, String role, String thumbUrl, String imageUrl) {
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
