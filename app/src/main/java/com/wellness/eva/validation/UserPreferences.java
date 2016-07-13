package com.wellness.eva.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Persistent data contains user preferences
 */
public class UserPreferences
{
    private List<PreferencesObserver> observers = new ArrayList<PreferencesObserver>();
    private int state;
    private boolean sendBroadcast;
    private boolean receiveBroadcast;
    private boolean spanish;
    private boolean english;
    private boolean autoCall911;

    public boolean isAutoCall911() {
        return autoCall911;
    }

    public void setAutoCall911(boolean autoCall911) {
        this.autoCall911 = autoCall911;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public boolean isSendBroadcast() {
        return sendBroadcast;
    }

    public void setSendBroadcast(boolean sendBroadcast) {
        this.sendBroadcast = sendBroadcast;
    }

    public boolean isReceiveBroadcast() {
        return receiveBroadcast;
    }

    public void setReceiveBroadcast(boolean receiveBroadcast) {
        this.receiveBroadcast = receiveBroadcast;
    }

    public boolean isSpanish() {
        return spanish;
    }

    public void setSpanish(boolean spanish) {
        this.spanish = spanish;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(PreferencesObserver observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (PreferencesObserver observer : observers) {
            observer.update();
        }
    }
}
