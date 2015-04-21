package parser;

import model.playstore.AppReview;
import model.playstore.PlayStoreAppModel;
import model.playstore.Score;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.PlayStoreConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 16.04.2015
 * Time: 20:35
 * Email: mrahbar.azad@gmail.com
 */
public class PlayStoreParser extends Parser {

    private static Pattern currencyPattern = Pattern.compile("\\D");

    public static PlayStoreAppModel parseAppPage(Document doc) {
        Elements select;
        PlayStoreAppModel parsedApp = new PlayStoreAppModel();

        // Updating App Url
        parsedApp.url = doc.location();

        // Updating Reference Date
        parsedApp.referenceDate = new Date();

        // Parsing App Name
        select = doc.select(PlayStoreConfig.APP_NAME);
        parsedApp.name = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing Cover Img Url
        select = doc.select(PlayStoreConfig.APP_COVER_IMG);
        parsedApp.coverImgUrl = hasNoSelection(select) ? EMPTY_STRING : select.get(0).attr("src");

        // Parsing App Screenshots
        select = doc.select(PlayStoreConfig.APP_SCREENSHOTS);

        // Sanity Check
        if (hasSelection(select))
        {
            // Dumping "Src" attribute of each node to an array
            parsedApp.screenshots = select.stream()
                    .map(new Function<Element, String>() {
                        @Override
                        public String apply(Element element) {
                            return element.attr("src");
                        }
                    })
                    .distinct()
                    .collect(Collectors.toList())
                    .toArray(new String[select.size()]);
        }

        // Parsing App Category
        select = doc.select(PlayStoreConfig.APP_CATEGORY);

        if (hasSelection(select))
        {
            String catLink = select.get(0).attr("href");

            if (catLink.indexOf('/') >= 0) {
                String[] catLinkSplit = catLink.split("/");
                parsedApp.category = catLinkSplit[catLinkSplit.length-1];
            }
        }
        else
        {
            parsedApp.category = "NO_CATEGORY_FOUND";
        }

        // Parsing App Developer/Author
        select = doc.select(PlayStoreConfig.APP_DEV);
        parsedApp.developer = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing If the Developer is a Top Developer
        select = doc.select(PlayStoreConfig.APP_TOP_DEV);
        parsedApp.isTopDeveloper = hasSelection(select);

        // Parsing App Developer Url
        select = doc.select(PlayStoreConfig.DEV_URL);

        if (hasSelection(select) && StringUtils.isNotEmpty(select.get(0).attr("content")))
        {
            parsedApp.developerURL = PlayStoreConfig.ORIGIN + select.get(0).attr("content");
        }
        else
        {
            parsedApp.developerURL = EMPTY_STRING;
        }

        // Parsing Publishing Date
        select = doc.select(PlayStoreConfig.APP_PUBLISH_DATE);

        if (hasSelection(select))
        {
            parsedApp.publicationDate = select.get(0).text().replace("-", "").trim();
        }


        // Parsing Free x Paid App
        select = doc.select(PlayStoreConfig.APP_FREE_PAID);

        if (hasSelection(select) && StringUtils.isNotEmpty(select.get(0).attr("content")))
        {
            String contentValue = select.get(0).attr("content");
            parsedApp.isFree = StringUtils.equals(contentValue, "0");
        }
        else
        {
            parsedApp.isFree = true;
        }

        // Parsing App Price
        if (parsedApp.isFree)
        {
            parsedApp.price = 0.0;
        }
        else
        {
            String normalizedPrice = select.get(0).attr("content");
            Matcher matcher = currencyPattern.matcher(normalizedPrice);
            if (matcher.find()) {
                parsedApp.price = parseDouble(normalizedPrice.replace(matcher.group(), EMPTY_STRING));
            } else {
                parsedApp.price = 0.0;
            }
        }

        // Parsing number of app reviewers
        select = doc.select(PlayStoreConfig.APP_REVIEWERS);

        if (hasSelection(select))
        {
            String normalizedReviews = select.get(0).text().trim().replace("(", EMPTY_STRING).replace(")", EMPTY_STRING);
            parsedApp.reviewers = parseNumber(normalizedReviews);
        }
        else
        {
            parsedApp.reviewers = -1;
        }

        // Parsing App Description
        select = doc.select(PlayStoreConfig.APP_DESCRIPTION);
        parsedApp.description = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing App "What's new" section
        select = doc.select(PlayStoreConfig.WHATS_NEW);

        // Sanity Check
        if (hasSelection(select))
        {
            parsedApp.whatsNew = select.stream()
                    .map(new Function<Element, String>() {
                        @Override
                        public String apply(Element element) {
                            return element.text();
                        }
                    })
                    .reduce((t, u) -> t + "\n" + u)
                    .get();
        }

        // Checking for In app Purchases
        select = doc.select(PlayStoreConfig.IN_APP_PURCHASE);
        parsedApp.haveInAppPurchases = hasSelection(select);

        // Parsing App's score
        Score score = new Score();

        // Total score
        select = doc.select(PlayStoreConfig.APP_SCORE_VALUE);
        score.total = hasSelection(select) ? parseDouble(select.get(0).attr("content")) : -1;

        // Rating Count
        select = doc.select(PlayStoreConfig.APP_SCORE_COUNT);
        score.count = hasSelection(select) ? parseDouble(select.get(0).attr("content")) : -1;

        // Parsing Five  Stars Count
        select = doc.select(PlayStoreConfig.APP_FIVE_STARS);
        score.fiveStars = parseNumber(select.get(0).text());

        // Parsing Four Stars Count
        select = doc.select(PlayStoreConfig.APP_FOUR_STARS);
        score.fourStars = parseNumber(select.get(0).text());

        // Parsing Three Stars Count
        select = doc.select(PlayStoreConfig.APP_THREE_STARS);
        score.threeStars = parseNumber(select.get(0).text());

        // Parsing Two Stars Count
        select = doc.select(PlayStoreConfig.APP_TWO_STARS);
        score.twoStars = parseNumber(select.get(0).text());

        // Parsing One Stars Count
        select = doc.select(PlayStoreConfig.APP_ONE_STARS);
        score.oneStars = parseNumber(select.get(0).text());

        // Updating Parsed App's score
        parsedApp.score = score;

        // Parsing Last Update Date
        select = doc.select(PlayStoreConfig.APP_UPDATE_DATE);

        if (hasSelection(select))
        {
            parsedApp.lastUpdateDate = select.get(0).text().replace("-", "").trim();
        }

        // Parsing App Size
        select = doc.select(PlayStoreConfig.APP_SIZE);

        if (hasSelection(select))
        {
            parsedApp.appSize = select.get(0).text().trim();
        }

        // Parsing App's Current Version
        select = doc.select(PlayStoreConfig.APP_VERSION);
        parsedApp.currentVersion = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing App's Instalation Count
        select = doc.select(PlayStoreConfig.APP_INSTALLS);
        parsedApp.installations = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing App's Content Rating
        select = doc.select(PlayStoreConfig.APP_CONTENT_RATING);
        parsedApp.contentRating = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing App's OS Version Required
        select = doc.select(PlayStoreConfig.APP_OS_REQUIRED);
        parsedApp.minimumOSVersion = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        // Parsing Developer Links (e-mail / website)
        select = doc.select(PlayStoreConfig.DEVELOPER_URLS);

        for (Element element : select) {
            // Parsing Inner Text
            String tagHref = element.attr("href").trim();
            String tagText = element.text().trim();

            // Checking for Email
            if (tagHref.contains("mailto"))
            {
                parsedApp.developerEmail = tagHref.replace("mailto:", EMPTY_STRING).trim();
            }
            else {
                if (tagText.toLowerCase().contains("website") || select.indexOf(element) == 0) // Developer Website
                {
                    parsedApp.developerWebsite = tagHref.replace("https://www.google.com/url?q=", EMPTY_STRING).trim();
                }
                else // Privacy Policy
                {
                    parsedApp.developerPrivacyPolicy = tagHref.replace("https://www.google.com/url?q=", EMPTY_STRING).trim();
                }
            }
        }

        // Parsing Physical Address (if available)
        select = doc.select(PlayStoreConfig.PHYSICAL_ADDRESS);
        parsedApp.physicalAddress = hasNoSelection(select) ? EMPTY_STRING :  select.get(0).text().replace("\n"," ").trim();

        select = doc.select(PlayStoreConfig.EXTRA_APPS);

        if (hasSelection(select)) {
            List<String> collection = select.stream()
                    .map(new Function<Element, String>() {
                        @Override
                        public String apply(Element element) {
                            return PlayStoreConfig.ORIGIN + element.attr("href");
                        }
                    })
                    .filter(new Predicate<String>() {
                        @Override
                        public boolean test(String s) {
                            return StringUtils.isNotEmpty(s);
                        }
                    })
                    .distinct()
                    .collect(Collectors.toList());

            parsedApp.relatedUrls = collection.toArray(new String[collection.size()]);
        }


        parsedApp.appReviews = parseAppReview(doc);

        return parsedApp;
    }

    private static List<AppReview>  parseAppReview(Document doc) {
        Elements select, innerSelect;
        select = doc.select(PlayStoreConfig.REVIEWS);
        List<AppReview> appReviews = new ArrayList<>();

        for (Element element : select) {
            AppReview review = new AppReview();
            review.timestamp = new Date();

            innerSelect = element.select(PlayStoreConfig.REVIEW_AUTHOR_URL);
            review.authorUrl = hasNoSelection(innerSelect) ? EMPTY_STRING : PlayStoreConfig.ORIGIN + innerSelect.get(0).attr("href");

            innerSelect = element.select(PlayStoreConfig.REVIEW_AUTHOR_NAME);
            review.authorName = hasNoSelection(innerSelect) ? EMPTY_STRING : innerSelect.get(0).text();

            innerSelect = element.select(PlayStoreConfig.REVIEW_AUTHOR_PIC);
            review.authorPicUrl = hasNoSelection(innerSelect) ? EMPTY_STRING : innerSelect.get(0).attr("src").trim();

            innerSelect = element.select(PlayStoreConfig.REVIEW_PERMALINK);
            review.permalink = hasNoSelection(innerSelect) ? EMPTY_STRING : PlayStoreConfig.ORIGIN + innerSelect.get(0).attr("href");

            innerSelect = element.select(PlayStoreConfig.REVIEW_REVIEW_DATE);
            if (hasSelection(innerSelect)) {
                review.reviewDate = innerSelect.get(0).text().trim();
            }

            innerSelect = element.select(PlayStoreConfig.REVIEW_RATING);
            review.starRatings = hasNoSelection(innerSelect) ? -1 : Integer.parseInt(innerSelect.get(0).attr("style").
                    replace("width:",EMPTY_STRING).replace("%;",EMPTY_STRING).trim()) / 20;

            innerSelect = element.select(PlayStoreConfig.REVIEW_TITLE);
            review.reviewTitle = hasNoSelection(innerSelect) ? EMPTY_STRING : innerSelect.get(0).text().trim();

            innerSelect = element.select(PlayStoreConfig.REVIEW_BODY);
            String expandReviewText = innerSelect.select("a").text();
            review.reviewBody = hasNoSelection(innerSelect) ? EMPTY_STRING : innerSelect.get(0).text()
                    .replace(review.reviewTitle, EMPTY_STRING).replace(expandReviewText, EMPTY_STRING)
                    .trim();

            appReviews.add(review);
        }

        return appReviews;
    }
}
