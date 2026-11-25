package com.thoughtworks.xstream.converters.basic;

import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/DoubleConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class DoubleConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$lang$Double;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != Double.TYPE) {
            Class clsClass$ = class$java$lang$Double;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_DOUBLE);
                class$java$lang$Double = clsClass$;
            }
            if (cls != clsClass$) {
                return false;
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return Double.valueOf(str);
    }
}
