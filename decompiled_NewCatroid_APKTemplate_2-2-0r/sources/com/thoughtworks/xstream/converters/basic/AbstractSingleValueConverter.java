package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/AbstractSingleValueConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractSingleValueConverter implements SingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public abstract boolean canConvert(Class cls);

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public abstract Object fromString(String str);

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
