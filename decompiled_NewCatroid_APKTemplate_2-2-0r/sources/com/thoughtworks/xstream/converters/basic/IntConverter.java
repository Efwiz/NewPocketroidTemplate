package com.thoughtworks.xstream.converters.basic;

import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/IntConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class IntConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$lang$Integer;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != Integer.TYPE) {
            Class clsClass$ = class$java$lang$Integer;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_INTEGER);
                class$java$lang$Integer = clsClass$;
            }
            if (cls != clsClass$) {
                return false;
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        long jLongValue = Long.decode(str).longValue();
        if (jLongValue < -2147483648L || jLongValue > 4294967295L) {
            throw new NumberFormatException(new StringBuffer("For input string: \"").append(str).append('\"').toString());
        }
        return new Integer((int) jLongValue);
    }
}
