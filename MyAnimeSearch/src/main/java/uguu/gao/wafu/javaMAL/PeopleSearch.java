package uguu.gao.wafu.javaMAL;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 20/06/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PeopleSearch {
    public PeopleResult searchById(int id) {
        String searchUrl = "people/" + Integer.toString(id);
        return new PeopleResult(new Network().connect(searchUrl));
    }

    public PeopleSearchResults searchByQuery(String query) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {}

        String searchUrl = "people.php?q=" + query;
        return new PeopleSearchResults(new Network().connect(searchUrl));
    }
}
