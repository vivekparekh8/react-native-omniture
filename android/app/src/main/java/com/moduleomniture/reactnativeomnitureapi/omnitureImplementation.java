package com.moduleomniture.reactnativeomnitureapi;


import android.widget.Toast;

import com.adobe.mobile.Analytics;
import com.adobe.mobile.Config;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vivekparekh on 10/26/16.
 */
public class omnitureImplementation extends ReactContextBaseJavaModule {
    public omnitureImplementation(ReactApplicationContext reactApplicationContext){
        super(reactApplicationContext);
        initAnalytics();
    }
    private void initAnalytics(){
        try {
//            InputStream configInput = Configuration.CHECK_URL.equalsIgnoreCase("QA") ? getAssets().open("ADBMobileConfig_QA.json") : getAssets().open("ADBMobileConfig_PROD.json");
            InputStream configInput = getReactApplicationContext().getAssets().open("ADBMobileConfig.json");

            Config.overrideConfigStream(configInput);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Config.setContext(getReactApplicationContext());
        Config.setDebugLogging(true);
    }
    private List<Object> recursivelyDeconstructReadableArray(ReadableArray readableArray) {
        List<Object> deconstructedList = new ArrayList<>(readableArray.size());
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableType indexType = readableArray.getType(i);
            switch(indexType) {
                case Null:
                    deconstructedList.add(i, null);
                    break;
                case Boolean:
                    deconstructedList.add(i, readableArray.getBoolean(i));
                    break;
                case Number:
                    deconstructedList.add(i, readableArray.getDouble(i));
                    break;
                case String:
                    deconstructedList.add(i, readableArray.getString(i));
                    break;
                case Map:
                    deconstructedList.add(i, convert(readableArray.getMap(i)));
                    break;
                case Array:
                    deconstructedList.add(i, recursivelyDeconstructReadableArray(readableArray.getArray(i)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object at index " + i + ".");
            }
        }
        return deconstructedList;
    }
    private HashMap<String, Object> convert(ReadableMap readableMap) {
            ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            HashMap<String, Object> deconstructedMap = new HashMap<>();
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                ReadableType type = readableMap.getType(key);
                switch (type) {
                    case Null:
                        deconstructedMap.put(key, null);
                        break;
                    case Boolean:
                        deconstructedMap.put(key, readableMap.getBoolean(key));
                        break;
                    case Number:
                        deconstructedMap.put(key, readableMap.getDouble(key));
                        break;
                    case String:
                        deconstructedMap.put(key, readableMap.getString(key));
                        break;
                    case Map:
                        deconstructedMap.put(key, convert(readableMap.getMap(key)));
                        break;
                    case Array:
                        deconstructedMap.put(key, recursivelyDeconstructReadableArray(readableMap.getArray(key)));
                        break;
                    default:
                        throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
                }

            }
            return deconstructedMap;
    }

    @Override
    public String getName() {
        return "omnitureImplementation";
    }
    @ReactMethod
    public void trackAction(String state, final ReadableMap contextData) {
        Analytics.trackAction((String)state, this.convert(contextData));
        String a = Analytics.trackingTimedActionExists("motologin")?"yes":"no";
        Toast.makeText(getReactApplicationContext(),a,Toast.LENGTH_LONG).show();

    }
    @ReactMethod
    public void setEnvironment() {
        Toast.makeText(getReactApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
    }
}
