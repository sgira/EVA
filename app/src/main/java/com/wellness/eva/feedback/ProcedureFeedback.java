package com.wellness.eva.feedback;

/**
 * Created by SinJa on 7/9/16.
 */

public abstract class ProcedureFeedback {

    abstract void initialize();
    abstract void start();
    abstract void end();

    public final void getFeedback()
    {
        // Initialize feedback with type of emergency
        initialize();

        // Start feedback evaluation
        start();

        // End evaluation
        end();
    }
}
