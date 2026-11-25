package com.thoughtworks.xstream.converters.enums;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/enums/EnumSingleValueConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class EnumSingleValueConverter extends AbstractSingleValueConverter {
    private final Class<? extends Enum> enumType;

    public EnumSingleValueConverter(Class<? extends Enum> cls) {
        if (!Enum.class.isAssignableFrom(cls) && cls != Enum.class) {
            throw new IllegalArgumentException("Converter can only handle defined enums");
        }
        this.enumType = cls;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls != null && this.enumType.isAssignableFrom(cls);
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return ((Enum) Enum.class.cast(obj)).name();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return Enum.valueOf(this.enumType, str);
    }
}
