package uguu.gao.wafu.myanimesearch;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by aki on 14/09/13.
 */
public class ImageLoaderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
    }
}
