package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.ConversionException;
import java.net.URI;
import java.net.URISyntaxException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/URIConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class URIConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$net$URI;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$net$URI;
        if (clsClass$ == null) {
            clsClass$ = class$("java.net.URI");
            class$java$net$URI = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            throw new ConversionException(e);
        }
    }
}
