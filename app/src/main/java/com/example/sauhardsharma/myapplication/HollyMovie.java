package com.example.sauhardsharma.myapplication;

import java.util.Date;

/**
 * Created by nairit on 8/3/15.
 */
public class HollyMovie {
    private long id;
    private String title;
    private Date releaseDateTheater;
    private int audienceScore;
    private String synopsis;
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;

    public HollyMovie(){

    }
    public HollyMovie(long id,
                 String title,
                 Date releaseDateTheater,
                 int audienceScore,
                 String synopsis,
                 String urlThumbnail,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar) {
       this.id = id;
        this.title = title;
        this.releaseDateTheater = releaseDateTheater;
        this.audienceScore = audienceScore;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
        this.urlSimilar = urlSimilar;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }
    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }
    public int getAudienceScore() {
        return audienceScore;
    }
    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public String getUrlThumbnail() {
        return urlThumbnail;
    }
    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }
    public String getUrlSelf() {
        return urlSelf;
    }
    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }
    public String getUrlCast() {
        return urlCast;
    }
    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }
    public String getUrlReviews() {
        return urlReviews;
    }
    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }
    public String getUrlSimilar() {
        return urlSimilar;
    }
    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }
    @Override
    public String toString() {
        return "\nID: " + id +
                "\nTitle " + title +
                "\nDate " + releaseDateTheater +
                "\nSynopsis " + synopsis +
                "\nScore " + audienceScore +
                "\nurlThumbnail " + urlThumbnail +
                "\nurlSelf " + urlSelf +
                "\nurlCast " + urlCast +
                "\nurlReviews " + urlReviews +
                "\nurlSimilar " + urlSimilar +
                "\n";
    }

}
