package uguu.gao.wafu.myanimesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.HashMap;

import uguu.gao.wafu.javaMAL.PeopleResult;
import uguu.gao.wafu.javaMAL.PeopleSearch;
import uguu.gao.wafu.javaMAL.VoiceActingRole;

/**
 * Created by aki on 14/09/13.
 */
public class PersonSingleResultActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_background);
        Intent i = getIntent();

        new SingleSearch().execute(Integer.valueOf(i.getIntExtra("id", 0)));
    }

    protected class SingleSearch extends AsyncTask<Integer, Void, Void> {

        PeopleResult result;

        @Override
        public Void doInBackground(Integer... params) {
            result = new PeopleSearch().searchById(params[0].intValue());
            return null;
        }

        public void onPostExecute(Void v) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.mainLayout);

            CardBackground background = new CardBackground(ll);

            CardBuilder imageHeadingBuilder = new CardBuilder(getApplicationContext(), ll);
            imageHeadingBuilder.addLargeImageHeading(result.getName(), result.getImageUrl());

            CardBuilder infoBuilder = new CardBuilder(getApplicationContext(), ll);

            CardBuilder infoDropdownBuilder = new CardBuilder(getApplicationContext(), ll);
            infoDropdownBuilder.addBasicLabel("Given Name", result.getGivenName());
            infoDropdownBuilder.addBasicLabel("Family Name", result.getFamilyName());
            infoDropdownBuilder.addBasicLabel("Website", result.getWebsiteUrl());

            infoBuilder.addSubheadingDropdown("Information", infoDropdownBuilder.getContainer());

            CardBuilder moreBuilder = new CardBuilder(getApplicationContext(), ll);
            CardBuilder moreDropdownBuilder = new CardBuilder(getApplicationContext(), ll);
            moreDropdownBuilder.addLabelWebView(result.getMore());
            moreBuilder.addSubheadingDropdown("More", moreDropdownBuilder.getContainer());

            CardBuilder seiyuuContainer = new CardBuilder(getApplicationContext(), ll);

            CardBuilder seiyuuDropdownContainer = new CardBuilder(getApplicationContext(), ll);

            if (result.getSeiyuuRoles().length > 0) {
                for (VoiceActingRole seiyuuRole : result.getSeiyuuRoles()) {
                    seiyuuDropdownContainer.addLabelImageLink(seiyuuRole.getAnimeTitle(), "");

                    CardBuilder characterDropdownBuilder = new CardBuilder(getApplicationContext(), ll);
                    characterDropdownBuilder.addLabelImageLink(seiyuuRole.getCharacterName(), seiyuuRole.getCharacterRole());

                    seiyuuDropdownContainer.addSubsubheadingDropdown("Character", characterDropdownBuilder.getContainer());

                }
                seiyuuContainer.addSubheadingDropdown("Anime Roles", seiyuuDropdownContainer.getContainer());
            }

            background.addContainer(imageHeadingBuilder.getContainer());
            background.addContainer(infoBuilder.getContainer());
            background.addContainer(moreBuilder.getContainer());
            background.addContainer(seiyuuContainer.getContainer());
            background.finalise();

        }
    }
}