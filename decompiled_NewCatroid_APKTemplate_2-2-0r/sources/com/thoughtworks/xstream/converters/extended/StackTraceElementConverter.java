package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.JVM;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/StackTraceElementConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class StackTraceElementConverter extends AbstractSingleValueConverter {
    private static final StackTraceElementFactory FACTORY;
    private static final Pattern PATTERN = Pattern.compile("^(.+)\\.([^\\(]+)\\(([^:]*)(:(\\d+))?\\)$");
    static /* synthetic */ Class class$java$lang$StackTraceElement;

    static {
        StackTraceElementFactory stackTraceElementFactory;
        StackTraceElementFactory stackTraceElementFactory2 = null;
        if (JVM.isVersion(5)) {
            try {
                stackTraceElementFactory = (StackTraceElementFactory) JVM.loadClassForName("com.thoughtworks.xstream.converters.extended.StackTraceElementFactory15", false).newInstance();
            } catch (Exception | LinkageError unused) {
                stackTraceElementFactory = null;
            }
        } else {
            stackTraceElementFactory = null;
        }
        if (stackTraceElementFactory == null) {
            stackTraceElementFactory = new StackTraceElementFactory();
        }
        try {
            stackTraceElementFactory.unknownSourceElement("a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
            stackTraceElementFactory2 = stackTraceElementFactory;
        } catch (Exception | NoClassDefFoundError unused2) {
        }
        FACTORY = stackTraceElementFactory2;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$lang$StackTraceElement;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.StackTraceElement");
            class$java$lang$StackTraceElement = clsClass$;
        }
        return clsClass$.equals(cls) && FACTORY != null;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return super.toString(obj).replaceFirst(":\\?\\?\\?", "");
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches()) {
            String strGroup = matcher.group(1);
            String strGroup2 = matcher.group(2);
            String strGroup3 = matcher.group(3);
            if (strGroup3.equals("Unknown Source")) {
                return FACTORY.unknownSourceElement(strGroup, strGroup2);
            }
            if (strGroup3.equals("Native Method")) {
                return FACTORY.nativeMethodElement(strGroup, strGroup2);
            }
            if (matcher.group(4) != null) {
                return FACTORY.element(strGroup, strGroup2, strGroup3, Integer.parseInt(matcher.group(5)));
            }
            return FACTORY.element(strGroup, strGroup2, strGroup3);
        }
        throw new ConversionException(new StringBuffer("Could not parse StackTraceElement : ").append(str).toString());
    }
}
