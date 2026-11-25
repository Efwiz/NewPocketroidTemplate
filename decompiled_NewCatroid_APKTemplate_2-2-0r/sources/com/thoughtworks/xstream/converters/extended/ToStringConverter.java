package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/ToStringConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ToStringConverter extends AbstractSingleValueConverter {
    private static final Class[] STRING_PARAMETER;
    static /* synthetic */ Class class$java$lang$String;
    private final Class clazz;
    private final Constructor ctor;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$java$lang$String;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_STRING);
            class$java$lang$String = clsClass$;
        }
        clsArr[0] = clsClass$;
        STRING_PARAMETER = clsArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public ToStringConverter(Class cls) throws NoSuchMethodException {
        this.clazz = cls;
        this.ctor = cls.getConstructor(STRING_PARAMETER);
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == this.clazz;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            return this.ctor.newInstance(str);
        } catch (IllegalAccessException e) {
            throw new ObjectAccessException("Unable to access single String param constructor", e);
        } catch (InstantiationException e2) {
            throw new ConversionException("Unable to instantiate single String param constructor", e2);
        } catch (InvocationTargetException e3) {
            throw new ConversionException("Unable to target single String param constructor", e3.getTargetException());
        }
    }
}
