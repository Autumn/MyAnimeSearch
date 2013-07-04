package uguu.gao.wafu.myanimesearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import uguu.gao.wafu.javaMAL.AnimeSearch;
import uguu.gao.wafu.javaMAL.AnimeSearchResult;
import uguu.gao.wafu.javaMAL.AnimeSearchResults;
import uguu.gao.wafu.javaMAL.CharacterSearch;
import uguu.gao.wafu.javaMAL.CharacterSearchResults;
import uguu.gao.wafu.javaMAL.PeopleSearch;
import uguu.gao.wafu.javaMAL.PeopleSearchResults;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ((ProgressBar) findViewById(R.id.searchProgress)).setVisibility(View.VISIBLE);
            new SearchTask().execute(query);
        }
    }

    private class SearchTask extends AsyncTask<String, Void, Void> {
        AnimeSearchResults animeSearchResults;
        CharacterSearchResults charSearchResults;
        PeopleSearchResults peopleSearchResults;

        @Override
        public Void doInBackground(String... params) {
            animeSearchResults = new AnimeSearch().searchByQuery(params[0]);
            charSearchResults = new CharacterSearch().searchByQuery(params[0]);
            peopleSearchResults = new PeopleSearch().searchByQuery(params[0]);

            return null;
        }

        public void onPostExecute(Void v) {
            ((ProgressBar) findViewById(R.id.searchProgress)).setVisibility(View.INVISIBLE);
            LinearLayout resultLayout = (LinearLayout) findViewById(R.id.resultsLayout);
            if (animeSearchResults.getSearchResults() != null) {
                ResultCardView cv = new ResultCardView(getApplicationContext());
                cv.setTitle("Anime");
                cv.setResults(animeSearchResults.getSearchResults().length + 1);
                resultLayout.addView(cv);
            }
            if (charSearchResults.getSearchResults() != null) {
                ResultCardView cv = new ResultCardView(getApplicationContext());
                cv.setTitle("Characters");
                cv.setResults(charSearchResults.getSearchResults().length + 1);
                resultLayout.addView(cv);
            }
            if (peopleSearchResults.getSearchResults() != null) {
                ResultCardView cv = new ResultCardView(getApplicationContext());
                cv.setTitle("People");
                cv.setResults(peopleSearchResults.getSearchResults().length + 1);
                resultLayout.addView(cv);

            }
        }
    }

}
