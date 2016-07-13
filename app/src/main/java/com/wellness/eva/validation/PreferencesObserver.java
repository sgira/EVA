package com.wellness.eva.validation;

/**
 * Created by SinJa on 7/9/16.
 */

public abstract class PreferencesObserver {

    // Subject of Observer object
    protected UserPreferences userPreferences;
    public abstract void update();

}
