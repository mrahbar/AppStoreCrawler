package controllers;

import model.apple.AppleStoreAppModel;
import model.playstore.PlayStoreAppModel;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import parser.AppleStoreParser;
import parser.PlayStoreParser;
import play.*;
import play.mvc.*;

import utils.AppleStoreConfig;
import utils.PlayStoreConfig;
import views.html.*;

import java.io.IOException;

import static play.data.Form.form;
import static play.libs.Json.toJson;

public class Application extends Controller {

    public static class ErrorResponse {
        int statusCode;
        String message;

        public ErrorResponse(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }
    }

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Application.crawlPlayStore(),
                        controllers.routes.javascript.Application.crawlAppleStore()
                )
        );
    }

    public static Result index() {
        return ok(index.render());
    }

    public static Result crawlPlayStore(String appUrl) {
        if (StringUtils.isNoneEmpty(appUrl)) {
            try {
                if (!appUrl.contains(PlayStoreConfig.ORIGIN))
                    appUrl  = PlayStoreConfig.PLAY_STORE_PREFIX + appUrl;

                Document document = Jsoup.connect(appUrl)
                        .userAgent(PlayStoreConfig.USER_AGENT)
                        .referrer(PlayStoreConfig.REFERER)
                        .get();

                PlayStoreAppModel appPage = PlayStoreParser.parseAppPage(document);

                return ok(toJson(appPage));
            } catch (IOException e) {
                return internalServerError(toJson(new ErrorResponse(500, "Error parsing app: " + appUrl)));
            }
        } else {
            return badRequest(toJson(new ErrorResponse(400, "App url or name can't be empty!")));
        }
    }

    public static Result crawlAppleStore(String appUrl) {
        if (StringUtils.isNoneEmpty(appUrl)) {
            try {
                if (!appUrl.contains(AppleStoreConfig.ROOT_STORE_URL))
                    appUrl  = AppleStoreConfig.ROOT_STORE_URL + appUrl;

                Document document = Jsoup.connect(appUrl)
                        .userAgent(PlayStoreConfig.USER_AGENT)
                        .referrer(PlayStoreConfig.REFERER)
                        .get();

                AppleStoreAppModel appPage = AppleStoreParser.parseAppPage(document);

                return ok(toJson(appPage));
            } catch (IOException e) {
                return internalServerError(toJson(new ErrorResponse(500, "Error parsing app: " + appUrl)));
            }
        } else {
            return badRequest(toJson(new ErrorResponse(400, "App url or name can't be empty!")));
        }
    }
}
