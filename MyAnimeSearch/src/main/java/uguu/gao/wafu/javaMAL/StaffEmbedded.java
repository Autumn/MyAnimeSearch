package uguu.gao.wafu.javaMAL;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 17/06/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaffEmbedded extends People {
    protected String role;

    public StaffEmbedded(Integer id, String name, String role, String thumbUrl, String imageUrl) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.thumbUrl = thumbUrl;
        this.imageUrl = imageUrl;
    }
    public String toString() {
        return id.toString() + " " + name + " " + role + " " + thumbUrl + " " + imageUrl;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
