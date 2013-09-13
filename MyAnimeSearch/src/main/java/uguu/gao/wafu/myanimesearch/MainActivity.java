package uguu.gao.wafu.myanimesearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import uguu.gao.wafu.javaMAL.AnimeSearch;
import uguu.gao.wafu.javaMAL.AnimeSearchResults;
import uguu.gao.wafu.javaMAL.CharacterSearch;
import uguu.gao.wafu.javaMAL.CharacterSearchResults;
import uguu.gao.wafu.javaMAL.PeopleSearch;
import uguu.gao.wafu.javaMAL.PeopleSearchResults;

public class MainActivity extends Activity {

    private ResultCountAdapter resultAdapter = null;

    AnimeSearchResults animeSearchResults;
    CharacterSearchResults charSearchResults;
    PeopleSearchResults peopleSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.searchHeading)).setText("Start searching!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ((TextView) findViewById(R.id.searchHeading)).setText("Searching for \"" + query + "\"...");

            ((ProgressBar) findViewById(R.id.searchProgress)).setVisibility(View.VISIBLE);
            new SearchTask().execute(query);
        }
    }

    protected class SearchTask extends AsyncTask<String, Void, Void> {


        Searcher searcher;
        @Override
        public Void doInBackground(String... params) {
            searcher = Searcher.instanceOf();
            searcher.setQuery(params[0]);
            searcher.performQuery();
            /*animeSearchResults = new AnimeSearch().searchByQuery(params[0]);
            charSearchResults = new CharacterSearch().searchByQuery(params[0]);
            peopleSearchResults = new PeopleSearch().searchByQuery(params[0]);*/

            return null;
        }

        public void onPostExecute(Void v) {
            ((ProgressBar) findViewById(R.id.searchProgress)).setVisibility(View.INVISIBLE);
            ListView resultsList = (ListView) findViewById(R.id.resultsList);

            ArrayList<Results> resultCounts = new ArrayList<Results>();

            if (searcher.animeSearchResults.getSearchResults() != null) {
                resultCounts.add(new Results("Anime", searcher.animeSearchResults.getSearchResults().length));
            }
            if (searcher.charSearchResults.getSearchResults() != null) {
                resultCounts.add(new Results("Characters", searcher.charSearchResults.getSearchResults().length));

            }
            if (searcher.peopleSearchResults.getSearchResults() != null) {
                resultCounts.add(new Results("People", searcher.peopleSearchResults.getSearchResults().length));

            }
            if (resultCounts.size() == 0) {
                resultCounts.add(new Results("No results found.", 0));
            }
            resultAdapter = new ResultCountAdapter(getApplicationContext(), R.layout.first_search_tableview, resultCounts);
            resultsList.setAdapter(resultAdapter);

        }

    }
    protected class Results {
        private String type;
        private int count;
        public Results(String type, int count) {
            this.type = type;
            this.count = count;
        }

        public String getType() {
            return type;
        }

        public String getResultCount() {
            return Integer.toString(count) + " results";
        }
    }

    protected class ResultCountAdapter extends ArrayAdapter<Results> {
        private ArrayList<Results> resultCounts;
        private Context c;

        public ResultCountAdapter(Context context, int textViewResourceId, ArrayList<Results> resultCounts) {
            super(context, textViewResourceId, resultCounts);
            this.resultCounts = resultCounts;
            this.c = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.first_search_tableview, null);
            }
            final Results rc = resultCounts.get(position);
            TextView resultType = (TextView) v.findViewById(R.id.resultType);
            TextView resultCount = (TextView) v.findViewById(R.id.resultCount);

            resultType.setText(rc.getType());
            resultCount.setText(rc.getResultCount());

            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Intent intent = new Intent(getContext(), ResultListActivity.class);
                    intent.putExtra("type", rc.getType());
                    startActivity(intent);
                    return false;
                }
            });
            return v;
        }
    }


}
