package uguu.gao.wafu.javaMAL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

public class PeopleResult extends People {

    protected Integer id;
    protected String name;
    protected String imageUrl;

    protected String givenName;
    protected String familyName;

    protected String birthday;
    protected String websiteUrl;
    protected String more;

    protected VoiceActingRole[] seiyuuRoles;
    protected AnimeStaffRole[] animeStaffRoles;
    protected MangaStaffRole[] mangaStaffRoles;

    public PeopleResult(String siteText) {
        scrapePerson(siteText);
    }

    private void scrapePerson(String siteText) {
        Document doc = Jsoup.parse(siteText);
        Element detailsLink = doc.select("a:containsOwn(Details)").get(0);
        setId(Utility.idFromUrl(detailsLink.attr("href")));
        Elements allContent = doc.select("div#contentWrapper");
        setName(doc.select("h1").get(0).text()); // name
        Element content = allContent.select("div#content").get(0);
        setImageUrl(content.select("img").attr("src")); // image url

        Elements givenNameNode = content.select("span:containsOwn(Given name:)");
        if (givenNameNode.size() > 0) {
            setGivenName(givenNameNode.get(0).nextSibling().toString().trim());
        }

        Elements familyNameNode = content.select("span:containsOwn(Family name:)");
        if (familyNameNode.size() > 0) {
            setFamilyName(familyNameNode.get(0).nextSibling().toString().trim());
        }

        Elements birthdayNode = content.select("span:containsOwn(Birthday:)");
        if (birthdayNode.size() > 0) {
            setBirthday(birthdayNode.get(0).nextSibling().toString().trim());
        }

        Elements websiteNode = content.select("span:containsOwn(Website:)");
        if (websiteNode.size() > 0) {
            setWebsiteUrl(websiteNode.get(0).nextSibling().nextSibling().attr("href"));
        }

        Node moreNode = content.select("span:containsOwn(More:)").parents().get(0).nextSibling();
        StringBuilder moreText = new StringBuilder();
        while (moreNode != null) {
            if (moreNode.nodeName().equals("#text")) {
                moreText.append(moreNode.toString());
            } else {
                moreText.append("\n");
            }
            moreNode = moreNode.nextSibling();
        }
        setMore(moreText.toString());

        Elements seiyuuListNodes = content.select("div:containsOwn(Voice Acting Roles)");
        ArrayList<VoiceActingRole> seiyuuRoles = new ArrayList<VoiceActingRole>();
        if (!seiyuuListNodes.get(0).nextSibling().toString().matches(".*No voice acting roles have been added yet.*")) {
            Element table = seiyuuListNodes.get(0).nextElementSibling();
            for (Element rows : table.select("tr")) {
                Elements nodes = rows.select("td");
                Element imageNode = nodes.get(0);
                Element infoNode = nodes.get(1);
                Element charInfoNode = nodes.get(2);
                Element charImageNode = nodes.get(3);

                Integer animeId = Utility.idFromUrl(infoNode.select("a").get(0).attr("href"));
                String animeTitle = infoNode.select("a").text();
                String animeThumbUrl = imageNode.select("img").attr("src");
                String animeImageUrl = Utility.imageUrlFromThumbUrl(animeThumbUrl, 'v');

                Integer characterId = Utility.idFromUrl(charInfoNode.select("a").get(0).attr("href"));
                String characterName = charInfoNode.select("a").text();
                String characterRole = charInfoNode.select("div").text();
                String characterThumbUrl = charImageNode.select("img").attr("src");
                String characterImageUrl = Utility.imageUrlFromThumbUrl(characterThumbUrl, 't');
                seiyuuRoles.add(new VoiceActingRole(animeId, animeTitle, animeThumbUrl, animeImageUrl,
                        characterId, characterName, characterRole, characterThumbUrl, characterImageUrl));
            }
        }

        Object[] array = seiyuuRoles.toArray();
        setSeiyuuRoles(Arrays.copyOf(array, array.length, VoiceActingRole[].class));

        Elements animeStaffRolesNodes = content.select("div:containsOwn(Anime Staff Positions)");
        ArrayList<AnimeStaffRole> animeStaffRoleList = new ArrayList<AnimeStaffRole>();
        if (!animeStaffRolesNodes.get(0).nextSibling().toString().matches(".*This person has not worked on any anime.*")) {
            Element table = animeStaffRolesNodes.get(0).nextElementSibling();
            for (Element rows : table.select("tr")) {
                Elements nodes = rows.select("td");
                Element imageNode = nodes.get(0);
                Element infoNode = nodes.get(1);

                Integer animeId = Utility.idFromUrl(infoNode.select("a").attr("href"));
                String animeTitle = infoNode.select("a").text();
                String animeRole = infoNode.select("small").text() + infoNode.select("small").get(0).nextSibling().toString();
                String animeThumbUrl = imageNode.select("img").attr("src");
                String animeImageUrl = Utility.imageUrlFromThumbUrl(animeThumbUrl, 'v');
                animeStaffRoleList.add(new AnimeStaffRole(animeId, animeTitle, animeRole, animeThumbUrl, animeImageUrl));
            }
        }

        array = animeStaffRoleList.toArray();
        setAnimeStaffRoles(Arrays.copyOf(array, array.length, AnimeStaffRole[].class));


        Elements publishedMangaNodes = content.select("div:containsOwn(Published Manga)");
        ArrayList<MangaStaffRole> mangaStaffRoleList = new ArrayList<MangaStaffRole>();

        if (!publishedMangaNodes.get(0).nextSibling().toString().matches(".*This person has not published any manga.*")) {
            Element table = animeStaffRolesNodes.get(0).nextElementSibling();
            for (Element rows : table.select("tr")) {
                Elements nodes = rows.select("td");
                Element imageNode = nodes.get(0);
                Element infoNode = nodes.get(1);

                Integer mangaId = Utility.idFromUrl(infoNode.select("a").attr("href"));
                String mangaTitle = infoNode.select("a").text();
                String mangaRole = infoNode.select("small").text() + infoNode.select("small").get(0).nextSibling().toString();
                String mangaThumbUrl = imageNode.select("img").attr("src");
                String mangaImageUrl = Utility.imageUrlFromThumbUrl(mangaThumbUrl, 'v');
                mangaStaffRoleList.add(new MangaStaffRole(mangaId, mangaTitle, mangaRole, mangaThumbUrl, mangaImageUrl));
            }
        }

        array = mangaStaffRoleList.toArray();
        setMangaStaffRoles(Arrays.copyOf(array, array.length, MangaStaffRole[].class));

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

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public VoiceActingRole[] getSeiyuuRoles() {
        return seiyuuRoles;
    }

    public void setSeiyuuRoles(VoiceActingRole[] seiyuuRoles) {
        this.seiyuuRoles = seiyuuRoles;
    }

    public AnimeStaffRole[] getAnimeStaffRoles() {
        return animeStaffRoles;
    }

    public void setAnimeStaffRoles(AnimeStaffRole[] animeStaffRoles) {
        this.animeStaffRoles = animeStaffRoles;
    }

    public MangaStaffRole[] getMangaStaffRoles() {
        return mangaStaffRoles;
    }

    public void setMangaStaffRoles(MangaStaffRole[] mangaStaffRoles) {
        this.mangaStaffRoles = mangaStaffRoles;
    }
}
