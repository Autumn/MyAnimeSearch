package uguu.gao.wafu.javaMAL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 17/06/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utility {
    public static Integer idFromUrl(String url) {
        Pattern idPattern = Pattern.compile("http://myanimelist.net/(\\w+)/(\\d+)/?.*");
        Matcher matcher = idPattern.matcher(url);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(2));
        }
        return 0;
    }

    public static String imageUrlFromThumbUrl(String thumbUrl, char thumbChar) {
        int char_pos = thumbUrl.lastIndexOf(".");
        if (thumbUrl.charAt(char_pos - 1) == thumbChar) {
            return thumbUrl.substring(0, char_pos - 1) + thumbUrl.substring(char_pos, thumbUrl.length());
        }
        return thumbUrl;
    }
}
