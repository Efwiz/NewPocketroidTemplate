package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.ConversionException;
import java.net.MalformedURLException;
import java.net.URL;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/URLConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class URLConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$net$URL;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$net$URL;
        if (clsClass$ == null) {
            clsClass$ = class$("java.net.URL");
            class$java$net$URL = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            throw new ConversionException(e);
        }
    }
}
