package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.ErrorWritingException;
import java.lang.reflect.Field;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import sun.misc.Unsafe;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/SunLimitedUnsafeReflectionProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SunLimitedUnsafeReflectionProvider extends PureJavaReflectionProvider {
    static /* synthetic */ Class class$java$lang$Void;
    static /* synthetic */ Class class$sun$misc$Unsafe;
    protected static final Exception exception;
    protected static final Unsafe unsafe;

    @Override // com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider
    protected void validateFieldAccess(Field field) {
    }

    static {
        Unsafe unsafe2;
        Exception exc = null;
        try {
            Class clsClass$ = class$sun$misc$Unsafe;
            if (clsClass$ == null) {
                clsClass$ = class$("sun.misc.Unsafe");
                class$sun$misc$Unsafe = clsClass$;
            }
            Field declaredField = clsClass$.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            unsafe2 = (Unsafe) declaredField.get(null);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            unsafe2 = null;
            exc = e;
        }
        exception = exc;
        unsafe = unsafe2;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public SunLimitedUnsafeReflectionProvider() {
    }

    public SunLimitedUnsafeReflectionProvider(FieldDictionary fieldDictionary) {
        super(fieldDictionary);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0036  */
    @Override // com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider, com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Object newInstance(Class cls) throws Throwable {
        ErrorWritingException conversionException;
        Exception exc = exception;
        if (exc != null) {
            ObjectAccessException objectAccessException = new ObjectAccessException("Cannot construct type", exc);
            objectAccessException.add("construction-type", cls.getName());
            throw objectAccessException;
        }
        if (cls == Void.TYPE) {
            conversionException = new ConversionException("Type void cannot have an instance");
        } else {
            Class clsClass$ = class$java$lang$Void;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_VOID);
                class$java$lang$Void = clsClass$;
            }
            if (cls == clsClass$) {
                conversionException = new ConversionException("Type void cannot have an instance");
            } else {
                try {
                    return unsafe.allocateInstance(cls);
                } catch (IllegalArgumentException e) {
                    conversionException = new ObjectAccessException("Cannot construct type", e);
                } catch (InstantiationException e2) {
                    conversionException = new ConversionException("Cannot construct type", e2);
                } catch (SecurityException e3) {
                    conversionException = new ObjectAccessException("Cannot construct type", e3);
                }
            }
        }
        conversionException.add("construction-type", cls.getName());
        throw conversionException;
    }

    private Object readResolve() {
        init();
        return this;
    }
}
