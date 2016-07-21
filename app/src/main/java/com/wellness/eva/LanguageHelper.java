package com.wellness.eva;



import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by Mario on 7/20/2016.
 */

public class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {

        Configuration config;
        config = new Configuration(res.getConfiguration());


        switch (locale) {
            case "es":
                config.locale = new Locale("es");
                break;

            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
        // reload files from assets directory
    }
}

