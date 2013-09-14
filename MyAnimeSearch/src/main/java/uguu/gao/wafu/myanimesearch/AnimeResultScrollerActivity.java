package uguu.gao.wafu.myanimesearch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import uguu.gao.wafu.javaMAL.AnimeSearchResult;

/**
 * Created by aki on 14/09/13.
 */
public class AnimeResultScrollerActivity extends Activity {

    AnimeResultsAdapter animeAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_horiz);

        Searcher searcher = Searcher.instanceOf();
        animeAdapter = new AnimeResultsAdapter(getApplicationContext(),
                R.layout.anime_result_row, searcher.getAnimeSearchResults());

        HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < animeAdapter.getCount(); i++) {
            linearLayout.addView(animeAdapter.getView(i, null, null));
        }

        scrollView.addView(linearLayout);
    }

    protected class AnimeResultsAdapter extends ArrayAdapter<AnimeSearchResult> {

        ArrayList<AnimeSearchResult> results;
        Context cxt;

        public AnimeResultsAdapter(Context cxt, int textViewResourceId, ArrayList<AnimeSearchResult> results) {
            super(cxt, textViewResourceId, results);
            this.results = results;
            this.cxt = cxt;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.anime_result_row, null);
            }

            AnimeSearchResult anime = results.get(position);
            TextView title = (TextView) v.findViewById(R.id.animeTitle);
            title.setText(anime.getTitle());

            ImageView thumb = (ImageView) v.findViewById(R.id.animeImage);
            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageUrl = anime.getImageUrl();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
            imageLoader.init(ImageLoaderConfiguration.createDefault(cxt));
            imageLoader.displayImage(imageUrl, thumb, options);
            thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);

            TextView type = (TextView) v.findViewById(R.id.animeType);
            type.setText(anime.getType());

            /*TextView airingDates = (TextView) v.findViewById(R.id.animeDate);
            airingDates.setText(anime.getStartDate() + " - " + anime.getEndDate());*/


            return v;
        }
    }

}