package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.util.Locale;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/LocaleConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class LocaleConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$util$Locale;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$Locale;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.Locale");
            class$java$util$Locale = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        String strSubstring;
        String strSubstring2;
        int[] iArrUnderscorePositions = underscorePositions(str);
        int i = iArrUnderscorePositions[0];
        String strSubstring3 = "";
        if (i == -1) {
            strSubstring2 = "";
        } else {
            if (iArrUnderscorePositions[1] == -1) {
                strSubstring = str.substring(0, i);
                String strSubstring4 = str.substring(iArrUnderscorePositions[0] + 1);
                strSubstring2 = "";
                strSubstring3 = strSubstring4;
            } else {
                strSubstring = str.substring(0, i);
                strSubstring3 = str.substring(iArrUnderscorePositions[0] + 1, iArrUnderscorePositions[1]);
                strSubstring2 = str.substring(iArrUnderscorePositions[1] + 1);
            }
            str = strSubstring;
        }
        return new Locale(str, strSubstring3, strSubstring2);
    }

    private int[] underscorePositions(String str) {
        int[] iArr = new int[2];
        int i = 0;
        while (i < 2) {
            iArr[i] = str.indexOf(95, (i == 0 ? 0 : iArr[i - 1]) + 1);
            i++;
        }
        return iArr;
    }
}
