// ReactNativeWidgetReloadPackage.java

package com.reactlibrary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class ReactNativeWidgetReloadPackage implements ReactPackage {
    private Class widgetClass;
    private String idsField;

    public ReactNativeWidgetReloadPackage(Class widgetClass, String idsField) {
        this.widgetClass = widgetClass;
        this.idsField = idsField;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new ReactNativeWidgetReloadModule(reactContext, this.widgetClass, this.idsField));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
