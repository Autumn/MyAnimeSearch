package uguu.gao.wafu.myanimesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

            TextView type = (TextView) findViewById(R.id.animeType);
            type.setVisibility(View.VISIBLE);
            type.setText(result.getType());

            //TextView synopsis = (TextView) findViewById(R.id.animeSynopsis);
            // synopsis.setVisibility(View.VISIBLE);
            //synopsis.setText(result.getSynopsis());

            WebView synopsis = (WebView) findViewById(R.id.animeSynopsis);
            synopsis.setVisibility(View.VISIBLE);
            synopsis.loadData(result.getSynopsis(), "text/html", null);
        }

    }
}