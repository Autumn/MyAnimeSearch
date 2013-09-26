package uguu.gao.wafu.myanimesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import uguu.gao.wafu.javaMAL.AnimeResult;
import uguu.gao.wafu.javaMAL.AnimeSearch;

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

//            TextView type = (TextView) findViewById(R.id.animeType);
//            type.setVisibility(View.VISIBLE);
//            type.setText(result.getType());

            //TextView synopsis = (TextView) findViewById(R.id.animeSynopsis);
            // synopsis.setVisibility(View.VISIBLE);
            //synopsis.setText(result.getSynopsis());

            //WebView synopsis = (WebView) findViewById(R.id.animeSynopsis);
            //synopsis.setVisibility(View.VISIBLE);
            //synopsis.loadData(result.getSynopsis(), "text/html", null);

            LinearLayout optionLabels = (LinearLayout) findViewById(R.id.animeHeaders);
            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int i = 0; i < groupNames.length; i++) {
                View view = setGroupHeader(groupNames[i]);
                final ImageView toggle = (ImageView) view.findViewById(R.id.groupToggleIcon);
                toggle.setHapticFeedbackEnabled(true);

                switch(i) {
                    case 0: // Information
                        view.setId(R.id.anime_info_header);
                        toggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                toggle.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

                                LinearLayout optionLabels = (LinearLayout) findViewById(R.id.animeHeaders);
                                int indexOfParent = optionLabels.indexOfChild(optionLabels.findViewById(R.id.anime_info_header));

                                if (informationToggleSet == false) {
                                    toggle.setImageResource(R.drawable.ic_find_previous_holo_light);
                                    informationToggleSet = true;

                                    // add info content

                                    View infoView = vi.inflate(R.layout.anime_single_info, null);
                                    TextView type = (TextView) infoView.findViewById(R.id.animeType);
                                    TextView episodes = (TextView) infoView.findViewById(R.id.animeEpisodes);
                                    TextView status = (TextView) infoView.findViewById(R.id.animeStatus);
                                    TextView startDate = (TextView) infoView.findViewById(R.id.animeStartDate);
                                    TextView endDate = (TextView) infoView.findViewById(R.id.animeEndDate);
                                    TextView classification = (TextView) infoView.findViewById(R.id.animeClassification);
                                    type.setText(result.getType());
                                    //episodes.setText(result.getEpisodes());
                                    status.setText(result.getStatus());
                                    startDate.setText(result.getStartDate());
                                    endDate.setText(result.getEndDate());
                                    classification.setText(result.getClassification());

                                    optionLabels.addView(infoView, indexOfParent + 1);

                                } else {
                                    toggle.setImageResource(R.drawable.ic_find_next_holo_light);
                                    informationToggleSet = false;

                                    optionLabels.removeViewAt(indexOfParent + 1);
                                }
                            }
                        });
                        break;
                    case 1: // Synopsis
                        view.setId(R.id.anime_synopsis_header);
                        toggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                toggle.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

                                LinearLayout optionLabels = (LinearLayout) findViewById(R.id.animeHeaders);
                                int indexOfParent = optionLabels.indexOfChild(optionLabels.findViewById(R.id.anime_synopsis_header));

                                if (synopsisToggleSet == false) {
                                    toggle.setImageResource(R.drawable.ic_find_previous_holo_light);
                                    synopsisToggleSet = true;

                                    // add info content

                                    View infoView = vi.inflate(R.layout.anime_single_synopsis, null);
                                    WebView synopsis = (WebView) infoView.findViewById(R.id.animeSynopsis);
                                    synopsis.loadData(result.getSynopsis(), "text/html", null);

                                    optionLabels.addView(infoView, indexOfParent + 1);

                                } else {
                                    toggle.setImageResource(R.drawable.ic_find_next_holo_light);
                                    synopsisToggleSet = false;

                                    optionLabels.removeViewAt(indexOfParent + 1);
                                }
                            }
                        });
                        break;

                    case 2: // Related Works
                    case 3: // Characters
                    case 4: // Staff
                    default:
                }
                optionLabels.addView(view);

            }
        }

        private View setGroupHeader(String header) {
            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = vi.inflate(R.layout.anime_single_group, null);
            TextView heading = (TextView) view.findViewById(R.id.animeGroupNames);
            heading.setText(header);
            return view;
        }

    }
}