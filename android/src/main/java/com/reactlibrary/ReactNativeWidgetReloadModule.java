// ReactNativeWidgetReloadModule.java

package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class ReactNativeWidgetReloadModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Class widgetClass;
    private String idsField;

    public ReactNativeWidgetReloadModule(ReactApplicationContext reactContext, Class widgetClass, String idsField) {
        super(reactContext);
        this.reactContext = reactContext;

        this.widgetClass = widgetClass;

        if (idsField == null) {
            this.idsField = AppWidgetManager.EXTRA_APPWIDGET_IDS;
        } else {
            this.idsField = idsField;
        }
    }

    @Override
    public String getName() {
        return "ReactNativeWidgetReload";
    }

    @ReactMethod
    public void reloadAllWidgets(final Promise promise) {
        Log.d(_moduleName, "Reload widgets");

        final int [] ids = _getIds();

        _sendUpdateIntent(ids);
        promise.resolve(_convertIds(ids));
    }

    private void _sendUpdateIntent(int[] ids) {
        Log.d(_moduleName, "Sending reload intent");
        Context context = reactContext.getApplicationContext();

        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(this.idsField, ids);
        context.sendBroadcast(intent);
    }

    private int[] _getIds() {
        Context context = reactContext.getApplicationContext();

        AppWidgetManager man = AppWidgetManager.getInstance(context);
        return man.getAppWidgetIds(
                new ComponentName(context, this.widgetClass));
    }

    private WritableArray _convertIds(int[] ids) {
        WritableArray resultArray = new WritableNativeArray();
        for (int i = 0; i < ids.length; i++) {
            resultArray.pushInt(ids[i]);
        }

        return resultArray;
    }
}
