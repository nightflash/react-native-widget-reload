// ReactNativeWidgetReloadModule.java

package com.reactlibrary;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.appwidget.AppWidgetManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class ReactNativeWidgetReloadModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Class widgetClass;
    private String idsField;

    final private String _moduleName = "RNWidgetManager";

    public ReactNativeWidgetReloadModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeWidgetReload";
    }

    @ReactMethod
    public void androidConfigure(String className, String idsExtra, final Promise promise) {
        Log.d(_moduleName, "androidConfigure" + className + " ids: " + idsExtra);

        try {
            this.widgetClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        idsField = idsExtra;

        promise.resolve(true);
    }

    @ReactMethod
    public void reloadAllWidgets(final Promise promise) {
        Log.d(_moduleName, "Reload widgets");

        sendUpdateIntent(getWidgetIds());
        promise.resolve(true);
    }

    private int[] getWidgetIds() {
        Context context = reactContext.getApplicationContext();

        AppWidgetManager man = AppWidgetManager.getInstance(context);
        return man.getAppWidgetIds(
                new ComponentName(context, this.widgetClass));
    }

    private void sendUpdateIntent(int[] ids) {
        Log.d(_moduleName, "Sending reload intent");
        Context context = reactContext.getApplicationContext();

        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(this.idsField, ids);
        context.sendBroadcast(intent);
    }
}
