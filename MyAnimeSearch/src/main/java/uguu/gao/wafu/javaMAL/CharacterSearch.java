package uguu.gao.wafu.javaMAL;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 18/06/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterSearch {
    public CharacterResult searchById(int id) {
        String searchUrl = "character/" + Integer.toString(id);
        return new CharacterResult(new Network().connect(searchUrl));
    }

    public CharacterSearchResults searchByQuery(String query) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {}

        String searchUrl = "character.php?q=" + query;
        return new CharacterSearchResults(new Network().connect(searchUrl));
    }

}
