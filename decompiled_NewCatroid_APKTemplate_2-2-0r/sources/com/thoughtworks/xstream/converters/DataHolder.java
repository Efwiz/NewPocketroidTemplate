package com.thoughtworks.xstream.converters;

import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/DataHolder.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface DataHolder {
    Object get(Object obj);

    Iterator keys();

    void put(Object obj, Object obj2);
}
