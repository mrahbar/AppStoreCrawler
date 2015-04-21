package model.playstore;

import java.util.Date;
import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 16.04.2015
 * Time: 21:59
 * Email: mrahbar.azad@gmail.com
 */
public class PlayStoreAppModel {
    public Date referenceDate;
    public String url;
    public String[] relatedUrls;
    public String name;
    public String developer;
    public boolean isTopDeveloper;
    public String developerURL;
    public String publicationDate;
    public String category;
    public boolean isFree;
    public double price;
    public double reviewers;
    public String coverImgUrl;
    public String[] screenshots;
    public String description;
    public String whatsNew;
    public Score score;
    public String lastUpdateDate;
    public String appSize;
    public String installations;
    public String currentVersion;
    public String minimumOSVersion;
    public String contentRating;
    public boolean haveInAppPurchases;
    public String developerEmail;
    public String developerWebsite;
    public String developerPrivacyPolicy;
    public String physicalAddress;
    public List<AppReview> appReviews;
}
