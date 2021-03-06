package uguu.gao.wafu.myanimesearch;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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

import uguu.gao.wafu.javaMAL.CharacterSearchResult;

public class CharResultListActivity extends ListActivity {

    CharacterResultsAdapter charAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);
        Searcher searcher = Searcher.instanceOf();
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        charAdapter = new CharacterResultsAdapter(getApplicationContext(),
                    R.layout.anime_result_row, searcher.getCharacterSearchResults());
        setListAdapter(charAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_list, menu);
        return true;
    }

    protected class CharacterResultsAdapter extends ArrayAdapter<CharacterSearchResult> {

        ArrayList<CharacterSearchResult> results;
        Context cxt;

        public CharacterResultsAdapter(Context cxt, int textViewResourceId, ArrayList<CharacterSearchResult> results) {
            super(cxt, textViewResourceId, results);
            this.results = results;
            this.cxt = cxt;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.char_result_row, null);
            }

            CharacterSearchResult character = results.get(position);
            TextView name = (TextView) v.findViewById(R.id.animeTitle);
            TextView role = (TextView) v.findViewById(R.id.animeRole);
            name.setText(character.getName());
            role.setText(character.getRole());
            ImageView thumb = (ImageView) v.findViewById(R.id.animeImage);
            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageUrl = character.getImageUrl();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
            imageLoader.init(ImageLoaderConfiguration.createDefault(cxt));
            imageLoader.displayImage(imageUrl, thumb, options);
            thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);

            return v;
        }
    }
}
