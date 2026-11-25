package com.thoughtworks.xstream.converters.reflection;

import java.lang.reflect.Field;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/FieldUtil15.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
class FieldUtil15 implements FieldDictionary.FieldUtil {
    FieldUtil15() {
    }

    @Override // com.thoughtworks.xstream.converters.reflection.FieldDictionary.FieldUtil
    public boolean isSynthetic(Field field) {
        return field.isSynthetic();
    }
}
