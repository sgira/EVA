package com.wellness.eva;

/**
 * Created by sindyg on 7/25/2016.
 */

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class MockTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MyMockApplication.class.getName(), context);
    }
}