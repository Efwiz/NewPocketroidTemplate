package com.thoughtworks.xstream.converters.basic;

import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/ShortConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ShortConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$lang$Short;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != Short.TYPE) {
            Class clsClass$ = class$java$lang$Short;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_SHORT);
                class$java$lang$Short = clsClass$;
            }
            if (cls != clsClass$) {
                return false;
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        int iIntValue = Integer.decode(str).intValue();
        if (iIntValue < -32768 || iIntValue > 65535) {
            throw new NumberFormatException(new StringBuffer("For input string: \"").append(str).append('\"').toString());
        }
        return new Short((short) iIntValue);
    }
}
