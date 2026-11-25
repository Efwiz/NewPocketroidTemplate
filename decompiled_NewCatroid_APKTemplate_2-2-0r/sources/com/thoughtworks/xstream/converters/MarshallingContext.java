package com.thoughtworks.xstream.converters;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/MarshallingContext.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface MarshallingContext extends DataHolder {
    void convertAnother(Object obj);

    void convertAnother(Object obj, Converter converter);
}
