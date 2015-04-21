package parser;

import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 17.04.2015
 * Time: 21:45
 * Email: mrahbar.azad@gmail.com
 */
public abstract class Parser {

    protected static final String EMPTY_STRING = "";

    protected static boolean hasNoSelection(Elements select) {
        return (select == null || select.size() == 0);
    }

    protected static boolean hasSelection(Elements select) {
        return !hasNoSelection(select);
    }

    protected static double parseNumber(String text) {
        try {
            return Integer.parseInt(text.replace(".", EMPTY_STRING).replace(",", EMPTY_STRING));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected static double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
