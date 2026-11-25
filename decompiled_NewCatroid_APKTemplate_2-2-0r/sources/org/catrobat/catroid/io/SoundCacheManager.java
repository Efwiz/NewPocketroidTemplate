package org.catrobat.catroid.io;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/SoundCacheManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundCacheManager {
    private static final SoundCacheManager INSTANCE = new SoundCacheManager();
    private static final String TAG = "SoundCacheManager";
    private SoundPool soundPool;
    private Map<String, Integer> soundIdMap = new HashMap();
    private boolean isInitialized = false;

    private SoundCacheManager() {
    }

    public static SoundCacheManager getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        if (this.isInitialized) {
            return;
        }
        this.soundPool = new SoundPool.Builder().setMaxStreams(16).setAudioAttributes(new AudioAttributes.Builder().setUsage(14).setContentType(4).build()).build();
        this.isInitialized = true;
        Log.i(TAG, "SoundPool initialized.");
    }

    public void loadSound(String cacheName, String filePath) {
        if (!this.isInitialized) {
            initialize();
        }
        if (this.soundIdMap.containsKey(cacheName)) {
            this.soundPool.unload(this.soundIdMap.get(cacheName).intValue());
        }
        this.soundIdMap.put(cacheName, Integer.valueOf(this.soundPool.load(filePath, 1)));
        Log.i(TAG, "Sound loaded into cache: '" + cacheName + "' from " + filePath);
    }

    public void playSound(String cacheName) {
        if (!this.isInitialized) {
            Log.e(TAG, "SoundCacheManager not initialized. Cannot play sound.");
            return;
        }
        Integer num = this.soundIdMap.get(cacheName);
        if (num != null) {
            this.soundPool.play(num.intValue(), 1.0f, 1.0f, 1, 0, 1.0f);
        } else {
            Log.e(TAG, "Sound not found in cache: " + cacheName);
        }
    }

    public void release() {
        if (this.isInitialized) {
            this.soundPool.release();
            this.soundPool = null;
            this.soundIdMap.clear();
            this.isInitialized = false;
            Log.i(TAG, "SoundPool released.");
        }
    }
}
