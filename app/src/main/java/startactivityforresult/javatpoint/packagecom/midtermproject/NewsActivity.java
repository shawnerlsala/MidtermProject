package startactivityforresult.javatpoint.packagecom.midtermproject;


import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by ShawnErl on 07/02/2017.
 */

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Article>> {

    private static final String REQUEST_URL = "https://newsapi.org/v1/articles?source=national-geographic&sortBy=top&apiKey=ddbca87d01e1464d8de336a4dfd64677";
    private static final int NEWS_LOADER_ID = 1;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("Mitderm Project News Feed");

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E71919")));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

        }
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        return new NewsLoader(this,baseUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            newsAdapter = new NewsAdapter(this,data);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(newsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {

    }
}
