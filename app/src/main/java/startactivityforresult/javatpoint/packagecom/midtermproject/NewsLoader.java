package startactivityforresult.javatpoint.packagecom.midtermproject;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ShawnErl on 07/02/2017.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<Article>> {

    private String mUrl;

    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public ArrayList<Article> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        ArrayList<Article> articles = QueryUtils.fetchNewsData(mUrl);
        return articles;
    }
}

