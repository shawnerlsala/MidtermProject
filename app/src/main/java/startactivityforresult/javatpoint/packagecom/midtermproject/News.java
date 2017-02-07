package startactivityforresult.javatpoint.packagecom.midtermproject;

import java.util.ArrayList;

/**
 * Created by ShawnErl on 07/02/2017.
 */

public class News {
    private String status;
    private String source;
    private String sortBy;
    private ArrayList<Article> articles;

    public News() {
    }

    public News(String status, String source, String sortBy, ArrayList<Article> articles) {
        this.status = status;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}

