package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.ConversionException;
import java.util.UUID;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/UUIDConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class UUIDConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$util$UUID;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$UUID;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.UUID");
            class$java$util$UUID = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            return UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Cannot create UUID instance", e);
        }
    }
}
