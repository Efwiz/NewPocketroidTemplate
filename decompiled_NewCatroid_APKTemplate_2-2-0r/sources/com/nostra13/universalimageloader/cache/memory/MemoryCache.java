package com.nostra13.universalimageloader.cache.memory;

import android.graphics.Bitmap;
import java.util.Collection;

/* JADX INFO: loaded from: classes10.dex */
public interface MemoryCache {
    void clear();

    Bitmap get(String str);

    Collection<String> keys();

    boolean put(String str, Bitmap bitmap);

    Bitmap remove(String str);
}
