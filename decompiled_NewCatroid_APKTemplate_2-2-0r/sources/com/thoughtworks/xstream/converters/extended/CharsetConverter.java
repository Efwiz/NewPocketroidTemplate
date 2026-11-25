package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.nio.charset.Charset;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/CharsetConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CharsetConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$nio$charset$Charset;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != null) {
            Class clsClass$ = class$java$nio$charset$Charset;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_NIO_CHARSET_CHARSET);
                class$java$nio$charset$Charset = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return ((Charset) obj).name();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return Charset.forName(str);
    }
}
