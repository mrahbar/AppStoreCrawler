package parser;

import model.apple.AppleAppReview;
import model.apple.AppleStoreAppModel;
import model.apple.InAppPurchase;
import model.apple.Rating;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.AppleStoreConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 17.04.2015
 * Time: 21:42
 * Email: mrahbar.azad@gmail.com
 */
public class AppleStoreParser extends Parser {

    public static AppleStoreAppModel parseAppPage(Document doc) {
        Elements select;
        AppleStoreAppModel parsedApp = new AppleStoreAppModel();

        // Updating Reference Date
        parsedApp.referenceDate = new Date();

        parsedApp.url = doc.location();

        select = doc.select(AppleStoreConfig.XPATH_TITLE);
        parsedApp.name = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_DEVELOPER_NAME);
        parsedApp.developerName = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_DEVELOPER_URL);
        parsedApp.developerUrl = hasNoSelection(select) ? EMPTY_STRING : select.get(0).attr("href");

        select = doc.select(AppleStoreConfig.XPATH_APP_PRICE);
        parsedApp.price = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_APP_PRICE);
        parsedApp.price = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();
        try {
            parsedApp.isFree = Integer.parseInt(select.get(0).attr("content")) == 0;
        } catch (Exception e) {}

        select = doc.select(AppleStoreConfig.XPATH_CATEGORY);
        parsedApp.category = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_UPDATE_DATE);
        parsedApp.updateDate = hasNoSelection(select) ? EMPTY_STRING : select.get(0).attr("content");

        select = doc.select(AppleStoreConfig.XPATH_DESCRIPTION);
        parsedApp.description = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_WHATSNEW);
        parsedApp.whatsNew = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_VERSION);
        parsedApp.version = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_APP_SIZE);
        parsedApp.appSize = hasNoSelection(select) ? EMPTY_STRING : select.get(0).parent().text()
                .replace(select.get(0).text(), EMPTY_STRING).trim();

        select = doc.select(AppleStoreConfig.XPATH_THUMBNAIL);
        parsedApp.thumbnailUrl = hasNoSelection(select) ? EMPTY_STRING : select.get(0).attr("src-swap-high-dpi");

        select = doc.select(AppleStoreConfig.XPATH_LANGUAGES);
        parsedApp.languages = hasNoSelection(select) ? null : select.get(0).parent().text()
                .replace(select.get(0).text(), EMPTY_STRING).replace(" ", EMPTY_STRING).trim().split(",");

        select = doc.select(AppleStoreConfig.XPATH_COMPATIBILITY);
        parsedApp.compatibility = hasNoSelection(select) ? EMPTY_STRING : select.get(0).text().trim();

        select = doc.select(AppleStoreConfig.XPATH_MINIMUM_AGE);
        if(hasNoSelection(select)) {
            parsedApp.minimumAge = EMPTY_STRING;
        } else {
            String[] split = select.get(0).text().split(" ");
            parsedApp.minimumAge = split.length >= 2 ? split[1] : EMPTY_STRING;
        }


        select = doc.select(AppleStoreConfig.XPATH_RATING_REASONS);
        if (hasSelection(select)) {
            parsedApp.ageRatingReasons = select.stream()
                    .map(new Function<Element, String>() {
                        @Override
                        public String apply(Element element) {
                            return element.text();
                        }
                    })
                    .reduce((t, u) -> t + "\n" + u)
                    .get();
        }

        select = doc.select(AppleStoreConfig.XPATH_RATINGS);
        if (hasSelection(select)) {
            Rating rating = new Rating();

            if (select.size() == 2) {
                String[] currentStrings = select.get(0).attr("aria-label").split(",");
                String[] allTimeStrings = select.get(1).attr("aria-label").split(",");
                rating.starsRatingCurrentVersion = currentStrings[0].trim();
                rating.ratingsCurrentVersion = currentStrings[1].trim();
                rating.starsVersionAllVersions = allTimeStrings[0].trim();
                rating.ratingsAllVersions = allTimeStrings[1].trim();
            } else if (select.size() == 1) {
                String[] allTimeStrings = select.get(0).attr("aria-label").split(",");
                rating.starsVersionAllVersions = allTimeStrings[0].trim();
                rating.ratingsAllVersions = allTimeStrings[1].trim();
            }

            parsedApp.rating = rating;
        }

        select = doc.select(AppleStoreConfig.XPATH_IN_APP_PURCHASES);
        if (hasSelection(select)) {
            List<InAppPurchase> inAppPurchaseList = new ArrayList<>();

            int purchaseRanking = 1;
            for (Element element : select) {
                InAppPurchase purchase = new InAppPurchase();
                purchase.ranking = purchaseRanking++;
                purchase.inAppName = element.select("span[class=in-app-title]").text().trim();
                purchase.inAppPrice = element.select("span[class=in-app-price]").text().trim();
                inAppPurchaseList.add(purchase);
            }

            parsedApp.topInAppPurchases = inAppPurchaseList.toArray(new InAppPurchase[inAppPurchaseList.size()]);
        }

        select = doc.select(AppleStoreConfig.XPATH_SCREENSHOTS);

        if (hasSelection(select)) {
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

        select = doc.select(AppleStoreConfig.XPATH_REVIEWS);

        if (hasSelection(select)) {
            List<AppleAppReview> reviews = new ArrayList<>();

            for (Element element : select) {
                AppleAppReview review = new AppleAppReview();

                review.starRatings = element.select("div[class=rating]").attr("aria-label").trim();
                review.authorName = element.select("span[class=user-info]").text().replace("by", EMPTY_STRING).trim();
                review.reviewTitle = element.select("span[class=customerReviewTitle]").text().trim();
                review.reviewBody = element.select("p[class=content]").text().trim();

                reviews.add(review);
            }

            parsedApp.reviews = reviews;
        }

        select = doc.select(AppleStoreConfig.XPATH_RELATED_APPS);

        if (hasSelection(select)) {
            List<String> collection = select.stream()
                    .map(new Function<Element, String>() {
                        @Override
                        public String apply(Element element) {
                            return element.attr("href");
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

            parsedApp.relatedAppsUrl = collection.toArray(new String[collection.size()]);
        }

        select = doc.select(AppleStoreConfig.XPATH_MOREBYDEV_APPS);

        if (hasSelection(select)) {
            List<String> collection = select.stream()
                    .map(new Function<Element, String>() {
                        @Override
                        public String apply(Element element) {
                            return element.attr("href");
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

            parsedApp.moreAppsByDeveloper = collection.toArray(new String[collection.size()]);
        }

        return parsedApp;
    }
}
