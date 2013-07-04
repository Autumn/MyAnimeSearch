package uguu.gao.wafu.javaMAL;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 17/06/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class SeiyuuEmbedded extends People {
    protected String nation;

    public SeiyuuEmbedded(Integer id, String name, String nation, String thumbUrl, String imageUrl) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.thumbUrl = thumbUrl;
        this.imageUrl = imageUrl;
    }
    public String toString() {
        return id.toString() + " " + name + " " + nation + " " + thumbUrl + " " + imageUrl;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
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
}
