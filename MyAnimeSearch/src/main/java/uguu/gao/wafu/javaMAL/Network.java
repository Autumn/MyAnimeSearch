package uguu.gao.wafu.javaMAL; /**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 14/06/13
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Network {

    String rootUrl = "http://www.myanimelist.net/";

    public String connect(String query) {
        String siteUrl = rootUrl + query;

        return connect_url(siteUrl);
    }
/*
    public String connect_test1(String query) {
        String result = "";
        try {
            String siteUrl = rootUrl + URLEncoder.encode(query, "UTF-8");

            URL url = new URL(siteUrl);
            //File f = new File("Clannad");
            URLConnection urlc = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            //BufferedReader buffer = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = buffer.readLine()) != null) {
                sb.append(str);
            }
            result = sb.toString();

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return result;
    }
*/

    public String connect_url(String uri) {
        String result = "";
        try {
            URL url = new URL(uri);
            //File f = new File("Kana_Hanazawa");
            URLConnection urlc = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            //BufferedReader buffer = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = buffer.readLine()) != null) {
                sb.append(str);
            }
            result = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
