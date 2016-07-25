package com.wellness.eva;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;

/**
 * Created by sindyg on 7/25/2016.
 */
public class Utility {
    public static ViewAssertion isVisible() {
        return new ViewAssertion() {
            public void check(View view, NoMatchingViewException noView) {
                //assertThat(view, new VisibilityMatcher(View.VISIBLE));
            }
        };
    }

    public static ViewAssertion isGone() {
        return new ViewAssertion() {
            public void check(View view, NoMatchingViewException noView) {
                //assertThat(view, new VisibilityMatcher(View.GONE));
            }
        };
    }

    public static ViewAssertion isInvisible() {
        return new ViewAssertion() {
            public void check(View view, NoMatchingViewException noView) {
                //assertThat(view, new VisibilityMatcher(View.INVISIBLE));
            }
        };
    }
}
