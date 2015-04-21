package utils;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 16.04.2015
 * Time: 19:58
 * Email: mrahbar.azad@gmail.com
 */
public class PlayStoreConfig {
    // Web Request Parameters and URLs
    public static final String CRAWL_URL          = "https://play.google.com/store/search?q=%s&c=apps&hl=en";
    public static final String REVIEWS_URL        = "https://play.google.com/store/getreviews";
    public static final String ORIGIN             = "https://play.google.com";
    public static final String INITIAL_POST_DATA  = "ipf=1&xhr=1";
    public static final String PLAY_STORE_PREFIX  = "https://play.google.com/store/apps/details?id=";

    // Old Post Data - To Allow Rolling Back if Needed
    // public static final String POST_DATA          = "start=%s&num=48&numChildren=0&ipf=1&xhr=1";

    public static final String POST_DATA          = "&start=0&num=0&numChildren=0&pagTok=%s&ipf=1&xhr=1";
    public static final String APP_URL_PREFIX     = "https://play.google.com";
    public static final String ACCEPT_LANGUAGE    = "Accept-Language: en-US,en;q=0.6,en;q=0.4,es;q=0.2";
    public static final String REVIEWS_POST_DATA  = "reviewType=0&pageNum=%s&id=%s&reviewSortOrder=2&xhr=1";
    public static final String USER_AGENT         = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    public static final String REFERER            = "http://www.google.com";

    // XPaths
    public static final String APP_LINKS          = "a[href*=details?id=]";
    public static final String APP_NAME           = "div[class=info-container] > div[class=document-title] > div";
    public static final String APP_CATEGORY       = "div > a[class=document-subtitle category]";
    public static final String APP_DEV            = "div[class=info-container] > div[itemprop=author] > a > span[itemprop=name]";
    public static final String APP_TOP_DEV        = "meta[itemprop=topDeveloperBadgeUrl]";
    public static final String DEV_URL            = "div[class=info-container] > div[itemprop=author] > meta[itemprop=url]";
    public static final String APP_PUBLISH_DATE   = "div[class=info-container] > div[itemprop=author] > div[class=document-subtitle]";
    public static final String APP_FREE_PAID      = "div[class=details-actions] span[itemtype=http://schema.org/Offer] > meta[itemprop=price]";
    public static final String APP_REVIEWERS      = "div[class=header-star-badge] > div[class=stars-count]";
    public static final String APP_DESCRIPTION    = "div[itemprop=description]";
    public static final String APP_SCORE_VALUE    = "div[class=rating-box] > div[class=score-container] > meta[itemprop=ratingValue]";
    public static final String APP_SCORE_COUNT    = "div[class=rating-box] > div[class=score-container] > meta[itemprop=ratingCount]";
    public static final String APP_FIVE_STARS     = "div[class=rating-histogram] > div[class=rating-bar-container five] > span[class=bar-number]";
    public static final String APP_FOUR_STARS     = "div[class=rating-histogram] > div[class=rating-bar-container four] > span[class=bar-number]";
    public static final String APP_THREE_STARS    = "div[class=rating-histogram] > div[class=rating-bar-container three] > span[class=bar-number]";
    public static final String APP_TWO_STARS      = "div[class=rating-histogram] > div[class=rating-bar-container two] > span[class=bar-number]";
    public static final String APP_ONE_STARS      = "div[class=rating-histogram] > div[class=rating-bar-container one] > span[class=bar-number]";
    public static final String APP_COVER_IMG      = "div[class=details-info] > div[class=cover-container] > img[class=cover-image]";
    public static final String APP_UPDATE_DATE    = "div[class=meta-info] > div[itemprop=datePublished]";
    public static final String APP_SIZE           = "div[class=meta-info] > div[itemprop=fileSize]";
    public static final String APP_VERSION        = "div[itemprop=softwareVersion]";
    public static final String APP_INSTALLS       = "div[itemprop=numDownloads]";
    public static final String APP_CONTENT_RATING = "div[itemprop=contentRating]";
    public static final String APP_OS_REQUIRED    = "div[itemprop=operatingSystems]";
    public static final String EXTRA_APPS         = "div[class=card-content id-track-click id-track-impression] > a[class=card-click-target]";
    public static final String IN_APP_PURCHASE    = "div[class=info-container] > div[class=inapp-msg]";
    public static final String DEVELOPER_URLS     = "div[class=content contains-text-link] > a[class=dev-link]";
    public static final String PHYSICAL_ADDRESS   = "div[class=content physical-address]";
    public static final String APP_SCREENSHOTS    = "div[class=thumbnails] > img[class*=screenshot]";
    public static final String WHATS_NEW          = "div[class=recent-change]";
    public static final String REVIEWS            = "div[class*=single-review]";
    public static final String REVIEW_AUTHOR_URL  = "a[href*=people]";
    public static final String REVIEW_AUTHOR_PIC  = "a[href*=people] > img";
    public static final String REVIEW_AUTHOR_NAME = "span[class*=author-name] > a";
    public static final String REVIEW_PERMALINK   = "a[class*=permalink]";
    public static final String REVIEW_REVIEW_DATE = "span[class*=review-date]";
    public static final String REVIEW_RATING      = "div[class*=current-rating]";
    public static final String REVIEW_TITLE       = "div[class*=review-body] > span";
    public static final String REVIEW_BODY        = "div[class*=review-body]";
}
