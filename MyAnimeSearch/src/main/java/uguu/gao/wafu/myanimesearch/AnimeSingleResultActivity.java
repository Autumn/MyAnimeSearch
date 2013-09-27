package uguu.gao.wafu.myanimesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import uguu.gao.wafu.javaMAL.AnimeResult;
import uguu.gao.wafu.javaMAL.AnimeSearch;
import uguu.gao.wafu.javaMAL.CharactersAnime;
import uguu.gao.wafu.javaMAL.StaffEmbedded;

/**
 * Created by aki on 14/09/13.
 */
public class AnimeSingleResultActivity extends Activity {

    String[] groupNames = {"Information", "Synopsis", "Related Works", "Characters", "Staff"};
    boolean informationToggleSet = false;
    boolean synopsisToggleSet = false;
    boolean relatedToggleSet = false;
    boolean charactersToggleSet = false;
    boolean staffToggleSet = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_single_result);
        Intent i = getIntent();

        new SingleSearch().execute(Integer.valueOf(i.getIntExtra("id", 0)));
    }

    protected class SingleSearch extends AsyncTask<Integer, Void, Void> {

        AnimeResult result;

        @Override
        public Void doInBackground(Integer... params) {
            result = new AnimeSearch().searchById(params[0].intValue());
            return null;
        }

        public void onPostExecute(Void v) {
            ((ProgressBar) findViewById(R.id.searchProgress)).setVisibility(View.INVISIBLE);

            ImageView thumb = (ImageView) findViewById(R.id.animeResultImage);
            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageUrl = result.getImageUrl();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
            imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
            imageLoader.displayImage(imageUrl, thumb, options);
            thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);


            TextView title = (TextView) findViewById(R.id.animeTitle);
            title.setVisibility(View.VISIBLE);
            title.setText(result.getTitle());

            //LinearLayout layout = (LinearLayout) findViewById(R.id.animeHeaders);


            ToggleListView tlv = new ToggleListView(getApplicationContext(), null, (LinearLayout) findViewById(R.id.animeHeaders));

            tlv.addItem(new ToggleItem(getApplicationContext(), tlv, R.layout.anime_single_info, "Information") {
                void createContainerView() {
                    TextView type = (TextView) containerView.findViewById(R.id.animeType);
                    TextView episodes = (TextView) containerView.findViewById(R.id.animeEpisodes);
                    TextView status = (TextView) containerView.findViewById(R.id.animeStatus);
                    TextView startDate = (TextView) containerView.findViewById(R.id.animeStartDate);
                    TextView endDate = (TextView) containerView.findViewById(R.id.animeEndDate);
                    TextView classification = (TextView) containerView.findViewById(R.id.animeClassification);
                    type.setText(result.getType());
                    //episodes.setText(result.getEpisodes());
                    status.setText(result.getStatus());
                    startDate.setText(result.getStartDate());
                    endDate.setText(result.getEndDate());
                    classification.setText(result.getClassification());
                }
            });

            tlv.addItem(new ToggleItem(getApplicationContext(), tlv, R.layout.anime_single_synopsis, "Synopsis") {
                void createContainerView() {
                    WebView synopsis = (WebView) containerView.findViewById(R.id.animeSynopsis);
                    synopsis.loadData(result.getSynopsis(), "text/html", null);
                }
            });

            tlv.addItem(new ToggleItem(getApplicationContext(), tlv, R.layout.anime_single_related, "Related Works") {
                void createContainerView() {

                    for (AnimeResult.RelatedWorks r : result.getRelatedStories()) {
                        RelativeLayout relatedAnimeRow = (RelativeLayout) vi.inflate(R.layout.anime_single_related_row, null);
                        TextView type = (TextView) relatedAnimeRow.findViewById(R.id.relatedAnimeType);
                        TextView title = (TextView) relatedAnimeRow.findViewById(R.id.relatedAnimeTitle);
                        type.setText(r.type);
                        title.setText(r.title);
                        containerView.addView(relatedAnimeRow);
                    }
                }
            });

            tlv.addItem(new ToggleItem(getApplicationContext(), tlv, R.layout.anime_single_characters, "Characters") {
                void createContainerView() {

                    for (CharactersAnime c : result.getCharacters()) {
                        RelativeLayout row = (RelativeLayout) vi.inflate(R.layout.anime_single_characters_row, null);
                        TextView name = (TextView) row.findViewById(R.id.charName);
                        TextView role = (TextView) row.findViewById(R.id.charRole);
                        ImageView image = (ImageView) row.findViewById(R.id.charImage);

                        name.setText(c.getName());
                        role.setText(c.getRole());

                        ImageLoader imageLoader = ImageLoader.getInstance();
                        String imageUrl = c.getThumbUrl();
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
                        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
                        imageLoader.displayImage(imageUrl, image, options);
                        image.setScaleType(ImageView.ScaleType.FIT_START);

                        containerView.addView(row);
                    }
                }
            });

            tlv.addItem(new ToggleItem(getApplicationContext(), tlv, R.layout.anime_single_staff, "Staff") {
                void createContainerView() {

                    for (StaffEmbedded p : result.getStaff()) {
                        RelativeLayout row = (RelativeLayout) vi.inflate(R.layout.anime_single_staff_row, null);
                        TextView name = (TextView) row.findViewById(R.id.staffName);
                        TextView role = (TextView) row.findViewById(R.id.staffRole);
                        ImageView image = (ImageView) row.findViewById(R.id.staffImage);

                        name.setText(p.getName());
                        role.setText(p.getRole());

                        ImageLoader imageLoader = ImageLoader.getInstance();
                        String imageUrl = p.getThumbUrl();
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
                        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
                        imageLoader.displayImage(imageUrl, image, options);
                        image.setScaleType(ImageView.ScaleType.FIT_START);

                        containerView.addView(row);
                    }
                }
            });
            //((LinearLayout) findViewById(R.id.animeHeaders)).addView(tlv.getContainerView());
            tlv.addViews();

        }
    }
}