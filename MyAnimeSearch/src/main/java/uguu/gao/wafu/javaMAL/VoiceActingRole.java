package uguu.gao.wafu.javaMAL;

/**
 * Created with IntelliJ IDEA.
 * User: aki
 * Date: 21/06/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class VoiceActingRole {
    protected Integer animeId;
    protected String animeTitle;
    protected String animeThumbUrl;
    protected String animeImageUrl;

    protected Integer characterId;
    protected String characterName;
    protected String characterRole;
    protected String characterThumbUrl;
    protected String characterImageUrl;

    public VoiceActingRole(Integer animeId, String animeTitle, String animeThumbUrl, String animeImageUrl,
                           Integer characterId, String characterName, String characterRole, String characterThumbUrl, String characterImageUrl) {
        this.animeId = animeId;
        this.animeTitle = animeTitle;
        this.animeThumbUrl = animeThumbUrl;
        this.animeImageUrl = animeImageUrl;
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterRole = characterRole;
        this.characterThumbUrl = characterThumbUrl;
        this.characterImageUrl = characterImageUrl;
    }

    public Integer getAnimeId() {
        return animeId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    public String getAnimeThumbUrl() {
        return animeThumbUrl;
    }

    public void setAnimeThumbUrl(String animeThumbUrl) {
        this.animeThumbUrl = animeThumbUrl;
    }

    public String getAnimeImageUrl() {
        return animeImageUrl;
    }

    public void setAnimeImageUrl(String animeImageUrl) {
        this.animeImageUrl = animeImageUrl;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterRole() {
        return characterRole;
    }

    public void setCharacterRole(String characterRole) {
        this.characterRole = characterRole;
    }

    public String getCharacterThumbUrl() {
        return characterThumbUrl;
    }

    public void setCharacterThumbUrl(String characterThumbUrl) {
        this.characterThumbUrl = characterThumbUrl;
    }

    public String getCharacterImageUrl() {
        return characterImageUrl;
    }

    public void setCharacterImageUrl(String characterImageUrl) {
        this.characterImageUrl = characterImageUrl;
    }
}
