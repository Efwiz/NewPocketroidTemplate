package com.thoughtworks.xstream.converters;

import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/ErrorWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface ErrorWriter {
    void add(String str, String str2);

    String get(String str);

    Iterator keys();

    void set(String str, String str2);
}
