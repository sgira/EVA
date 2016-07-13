package com.wellness.eva.messaging;

import com.wellness.eva.validation.PreferencesObserver;
import com.wellness.eva.validation.UserPreferences;

/**
 * Created by SinJa on 7/9/16.
 */

public class BroadcastEmergency extends PreferencesObserver
{
    private UserPreferences userPreferences;

    /**
     * Create an emergency broadcast message with access to the
     * user preferences
     * @param userPreferences
     */
    public BroadcastEmergency(UserPreferences userPreferences)
    {
        this.userPreferences = userPreferences;
        this.userPreferences.attach(this);
    }

    /**
     * Cancel the broadcast if the user makes changes to the preferences
     * and decides to not broadcast emergency situations
     */
    @Override
    public void update() {

    }
}
