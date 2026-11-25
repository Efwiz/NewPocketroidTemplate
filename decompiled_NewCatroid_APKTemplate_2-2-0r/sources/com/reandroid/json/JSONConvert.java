package com.reandroid.json;

import com.reandroid.json.JSONItem;

/* JADX INFO: loaded from: classes11.dex */
public interface JSONConvert<T extends JSONItem> {
    void fromJson(T t);

    T toJson();
}
