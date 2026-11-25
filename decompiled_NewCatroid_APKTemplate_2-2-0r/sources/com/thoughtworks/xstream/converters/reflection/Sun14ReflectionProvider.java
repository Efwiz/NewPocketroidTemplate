package com.thoughtworks.xstream.converters.reflection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/Sun14ReflectionProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Sun14ReflectionProvider extends SunUnsafeReflectionProvider {
    public Sun14ReflectionProvider() {
    }

    public Sun14ReflectionProvider(FieldDictionary fieldDictionary) {
        super(fieldDictionary);
    }

    private Object readResolve() {
        init();
        return this;
    }
}
