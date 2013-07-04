package uguu.gao.wafu.javaMAL;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 17/06/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class CharactersAnime extends Characters {
    protected String role;
    protected SeiyuuEmbedded[] seiyuus;

    public CharactersAnime(Integer id, String name, String role, String thumbUrl, String imageUrl, SeiyuuEmbedded[] seiyuus) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.thumbUrl = thumbUrl;
        this.imageUrl = imageUrl;
        this.seiyuus = seiyuus;
    }
    public String toString() {
        return id.toString() + " " + name + " " + role + " " + thumbUrl + " " + imageUrl + " " + Arrays.toString(seiyuus);
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

    public People[] getSeiyuu() {
        return seiyuu;
    }

    public void setSeiyuu(People[] seiyuu) {
        this.seiyuu = seiyuu;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SeiyuuEmbedded[] getSeiyuus() {
        return seiyuus;
    }

    public void setSeiyuus(SeiyuuEmbedded[] seiyuus) {
        this.seiyuus = seiyuus;
    }
}
