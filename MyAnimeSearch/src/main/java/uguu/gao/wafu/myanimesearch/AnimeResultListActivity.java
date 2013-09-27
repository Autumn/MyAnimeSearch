package uguu.gao.wafu.myanimesearch;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import uguu.gao.wafu.javaMAL.AnimeSearchResult;
import uguu.gao.wafu.javaMAL.CharacterSearchResult;
import uguu.gao.wafu.javaMAL.PeopleSearchResult;

public class AnimeResultListActivity extends ListActivity {

    AnimeResultsAdapter animeAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);
        Searcher searcher = Searcher.instanceOf();
        animeAdapter = new AnimeResultsAdapter(getApplicationContext(), R.layout.anime_result_row, searcher.getAnimeSearchResults());
        setListAdapter(animeAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_list, menu);
        return true;
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

            final AnimeSearchResult anime = results.get(position);
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



            final ImageView searchSelect = (ImageView) v.findViewById(R.id.searchSelect);
            searchSelect.setHapticFeedbackEnabled(true);

            searchSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchSelect.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    Intent intent = new Intent(cxt, AnimeSingleResultActivity.class);
                    intent.putExtra("id", anime.getId());
                    startActivity(intent);
                }
            });

            return v;
        }
    }
}
