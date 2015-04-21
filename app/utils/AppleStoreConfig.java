package utils;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 17.04.2015
 * Time: 21:44
 * Email: mrahbar.azad@gmail.com
 */
public class AppleStoreConfig {
    // Urls
    public final static String ROOT_STORE_URL = "https://itunes.apple.com/de/app/";

    // XPaths - App Page
    public final static String XPATH_TITLE            = "div[id=title] > div[class=left] > h1";
    public final static String XPATH_DEVELOPER_NAME   = "div[id=title] > div[class=left] > h2";
    public final static String XPATH_DEVELOPER_URL    = "a[class=view-more]";
    public final static String XPATH_APP_PRICE        = "div[itemprop=price]";
    public final static String XPATH_CATEGORY         = "ul[class=list] > li[class=genre] > a";
    public final static String XPATH_UPDATE_DATE      = "ul[class=list] > li[class=release-date] > span[itemprop=datePublished]";
    public final static String XPATH_DESCRIPTION      = "p[itemprop=description]";
    public final static String XPATH_WHATSNEW         = "div[metrics-loc*=Titledbox_What] p";
    public final static String XPATH_VERSION          = "span[itemprop=softwareVersion]";
    public final static String XPATH_APP_SIZE         = "span:contains(Size)";
    public final static String XPATH_THUMBNAIL        = "div[id=left-stack] > div > a >  div[class=artwork] > img";
    public final static String XPATH_LANGUAGES        = "ul[class=list] > li[class=language] > span[class=label]";
    public final static String XPATH_COMPATIBILITY    = "span[itemprop=operatingSystem]";
    public final static String XPATH_MINIMUM_AGE      = "div[class=app-rating] > a";
    public final static String XPATH_RATING_REASONS   = "ul[class=list app-rating-reasons] > li";
    public final static String XPATH_RATINGS          = "div[class=extra-list customer-ratings] > div[class=rating]";
    public final static String XPATH_IN_APP_PURCHASES = "div[class=extra-list in-app-purchases] > ol > li";
    public final static String XPATH_SCREENSHOTS      = "div[class*=screen-shots] img";
    public final static String XPATH_REVIEWS          = "div[class=customer-review]";
    public final static String XPATH_RELATED_APPS     = "div[class=content] a[class=name]";
    public final static String XPATH_MOREBYDEV_APPS   = "div[metrics-loc*=Titledbox_More] a[class=name]";
}
