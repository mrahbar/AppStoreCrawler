package model.apple;

import java.util.Date;
import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 17.04.2015
 * Time: 21:42
 * Email: mrahbar.azad@gmail.com
 */
public class AppleStoreAppModel {
    public Date referenceDate;
    public String   url                      ;
    public String   name                     ;
    public String   developerName            ;
    public String   developerUrl             ;
    public String   price                    ;
    public boolean  isFree                   ;
    public String   description              ;
    public String   thumbnailUrl             ;
    public String   compatibility            ;
    public String   category                 ;
    public String   updateDate               ;
    public String   version                  ;
    public String appSize;
    public String[] languages                ;
    public String   minimumAge               ;
    public String   ageRatingReasons         ;
    public String[] screenshots              ;
    public List<AppleAppReview> reviews      ;
    public String[] relatedAppsUrl           ;
    public String[] moreAppsByDeveloper      ;
    public String   whatsNew                 ;
    public Rating   rating                   ;
    public InAppPurchase[] topInAppPurchases ;
}
