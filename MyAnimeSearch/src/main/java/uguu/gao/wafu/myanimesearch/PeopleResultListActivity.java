package uguu.gao.wafu.myanimesearch;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import uguu.gao.wafu.javaMAL.PeopleSearchResult;

public class PeopleResultListActivity extends ListActivity {

    // TODO action bar button to close all toggles

    PeopleResultsAdapter peopleAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);
        Searcher searcher = Searcher.instanceOf();
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        peopleAdapter = new PeopleResultsAdapter(getApplicationContext(),
                    R.layout.anime_result_row, searcher.getPeopleSearchResults());
        setListAdapter(peopleAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_list, menu);
        return true;
    }

    protected class PeopleResultsAdapter extends ArrayAdapter<PeopleSearchResult> {

        ArrayList<PeopleSearchResult> results;
        Context cxt;

        public PeopleResultsAdapter(Context cxt, int textViewResourceId, ArrayList<PeopleSearchResult> results) {
            super(cxt, textViewResourceId, results);
            this.results = results;
            this.cxt = cxt;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.people_result_row, null);
            }

            final PeopleSearchResult person = results.get(position);
            TextView name = (TextView) v.findViewById(R.id.personName);
            ImageView thumb = (ImageView) v.findViewById(R.id.personImage);
            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageUrl = person.getThumbUrl();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
            imageLoader.init(ImageLoaderConfiguration.createDefault(cxt));
            imageLoader.displayImage(imageUrl, thumb, options);
//            thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
            name.setText(person.getName());

            final ImageView button = (ImageView) v.findViewById(R.id.searchPersonButton);
            button.setHapticFeedbackEnabled(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    Intent intent = new Intent(cxt, PersonSingleResultActivity.class);
                    intent.putExtra("id", person.getId());
                    startActivity(intent);

                }
            });

            return v;
        }
    }
}
