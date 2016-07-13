package com.wellness.eva.messaging;

import com.wellness.eva.validation.PreferencesObserver;
import com.wellness.eva.validation.UserPreferences;

/**
 * Created by SinJa on 7/9/16.
 */

public class BroadcastReceiver extends PreferencesObserver
{
    private UserPreferences userPreferences;

    public BroadcastReceiver(UserPreferences userPreferences)
    {
        this.userPreferences = userPreferences;
        this.userPreferences.attach(this);
    }

    /**
     * Stop receiving medical emergencies from current vicinity
     * if the user makes changes to the preferences
     * and decides to not receive emergency situations
     */
    @Override
    public void update() {

    }
}
