package startactivityforresult.javatpoint.packagecom.midtermproject;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by ShawnErl on 07/02/2017.
 */

public class QueryUtils {
    public QueryUtils() {
    }

    public static ArrayList<Article> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Article> newsArticles = extractFeatureFromJson(jsonResponse);
        return newsArticles;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Article> extractFeatureFromJson(String newsJson){
        if(TextUtils.isEmpty(newsJson)){
            return null;
        }

        ArrayList<Article> articles = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(newsJson);
            JSONArray newsArray = baseJsonResponse.getJSONArray("articles");
            for(int i = 0 ; i<newsArray.length(); i++){
                JSONObject currentArticle = newsArray.getJSONObject(i);

                String author = currentArticle.getString("author");
                String title = currentArticle.getString("title");
                String description = currentArticle.getString("description");
                String url = currentArticle.getString("url");
                String urlToImage = currentArticle.getString("urlToImage");
                String publishedAt = currentArticle.getString("publishedAt");

                Article article = new Article(author,title,description,url,urlToImage,publishedAt);
                articles.add(article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
