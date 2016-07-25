package com.wellness.eva.validation_test;

import android.content.Context;
import android.test.suitebuilder.annotation.SmallTest;

import com.wellness.eva.validation.SettingsSecurity;

import junit.framework.TestCase;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by SinJa on 7/24/16.
 */
public class UserPreferencesTest extends TestCase
{
    @Mock
    Context context;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    private SettingsSecurity settingsSecurity;

    public void setUp() throws Exception {
        super.setUp();
        settingsSecurity = new SettingsSecurity();
    }

    @SmallTest
    public void testUpdateFunction() {
//        UserPreferences subj = new UserPreferences();
//        PreferencesObserver mockedObserver = EasyMock.createMock(PreferencesObserver.class);
//
//        mockedObserver.update(equals(subj), EasyMock.anyObject());
//        EasyMock.replay(mockedObserver);
//
//
//        subj.isSendBroadcast();
//
//        EasyMock.verify(mockedObserver);
    }

    public void testIsAutoCall911() throws Exception {

    }

    public void testSetAutoCall911() throws Exception {

    }

    public void testIsEnglish() throws Exception {

    }

    public void testSetEnglish() throws Exception {

    }

    public void testIsSendBroadcast() throws Exception {

    }

    public void testSetSendBroadcast() throws Exception {

    }

    public void testIsReceiveBroadcast() throws Exception {

    }

    public void testSetReceiveBroadcast() throws Exception {

    }

    public void testGetState() throws Exception {

    }

    public void testSetState() throws Exception {

    }

    public void testAttach() throws Exception {

    }

    public void testNotifyAllObservers() throws Exception {

    }
}