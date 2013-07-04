package uguu.gao.wafu.javaMAL;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 14/06/13
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Harness {
    public static void main(String[] args) {
        //System.out.println(new AnimeSearch().searchById(2167));
        PeopleSearchResults searchResults = new PeopleSearch().searchByQuery("asdfasdf");
        if (searchResults.searchResults == null) {
            System.out.println("none found");
        }
    }
}
