package com.thoughtworks.xstream.converters;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/UnmarshallingContext.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface UnmarshallingContext extends DataHolder {
    void addCompletionCallback(Runnable runnable, int i);

    Object convertAnother(Object obj, Class cls);

    Object convertAnother(Object obj, Class cls, Converter converter);

    Object currentObject();

    Class getRequiredType();
}
