package org.jetbrains.kotlin.com.intellij.serialization;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public final class PropertyAccessor implements MutableAccessor {
    private static final Logger LOG = Logger.getInstance((Class<?>) PropertyAccessor.class);
    private final Type myGenericType;
    private final String myName;
    private final Method myReadMethod;
    private final Class<?> myType;
    private final Method myWriteMethod;

    /* JADX WARN: Code duplicated, block: B:11:0x0013  */
    /* JADX WARN: Code duplicated, block: B:6:0x0009  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
                objArr[0] = "readMethod";
                break;
            case 3:
            case 19:
            case 20:
            case 21:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/serialization/PropertyAccessor";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[0] = "o";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[0] = "host";
                break;
            case 18:
                objArr[0] = "annotationClass";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i != 3) {
            switch (i) {
                case 19:
                    objArr[1] = "getName";
                    break;
                case 20:
                    objArr[1] = "getValueClass";
                    break;
                case 21:
                    objArr[1] = "getGenericType";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/serialization/PropertyAccessor";
                    break;
            }
        } else {
            objArr[1] = "getGetterName";
        }
        switch (i) {
            case 3:
            case 19:
            case 20:
            case 21:
                break;
            case 4:
                objArr[2] = "read";
                break;
            case 5:
                objArr[2] = "readUnsafe";
                break;
            case 6:
                objArr[2] = "readInt";
                break;
            case 7:
                objArr[2] = "readLong";
                break;
            case 8:
                objArr[2] = "readFloat";
                break;
            case 9:
                objArr[2] = "readDouble";
                break;
            case 10:
                objArr[2] = "readBoolean";
                break;
            case 11:
                objArr[2] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            case 12:
                objArr[2] = "setBoolean";
                break;
            case 13:
                objArr[2] = "setInt";
                break;
            case 14:
                objArr[2] = "setShort";
                break;
            case 15:
                objArr[2] = "setLong";
                break;
            case 16:
                objArr[2] = "setDouble";
                break;
            case 17:
                objArr[2] = "setFloat";
                break;
            case 18:
                objArr[2] = "getAnnotation";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    PropertyAccessor(String str, Class<?> cls, Method method, Method method2) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (method == null) {
            $$$reportNull$$$0(2);
        }
        this.myName = str;
        this.myType = cls;
        this.myReadMethod = method;
        this.myWriteMethod = method2;
        this.myGenericType = method.getGenericReturnType();
        try {
            method.setAccessible(true);
            if (method2 != null) {
                method2.setAccessible(true);
            }
        } catch (SecurityException unused) {
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public Object read(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(4);
        }
        try {
            return this.myReadMethod.invoke(obj, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new SerializationException(e);
        } catch (InvocationTargetException e2) {
            ExceptionUtilRt.rethrowUnchecked(e2.getTargetException());
            throw new SerializationException(e2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void set(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(11);
        }
        Method method = this.myWriteMethod;
        if (method == null) {
            throw new SerializationException(obj.getClass().getName() + "::" + this.myName + " lacks a setter");
        }
        try {
            method.invoke(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new SerializationException(e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof NullPointerException) {
                Type type = this.myGenericType;
                if ((type instanceof Class) && ((Class) type).isEnum() && cause.getMessage().contains("Parameter specified as non-null is null:")) {
                    Object[] enumConstants = ((Class) this.myGenericType).getEnumConstants();
                    if (enumConstants.length > 0) {
                        try {
                            LOG.warn("Cannot set enum value, will be set to first enum value", e2);
                            this.myWriteMethod.invoke(obj, enumConstants[0]);
                            return;
                        } catch (IllegalAccessException | InvocationTargetException unused) {
                            throw new SerializationException(e2);
                        }
                    }
                }
            }
            throw new SerializationException(cause);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setBoolean(Object obj, boolean z) {
        if (obj == null) {
            $$$reportNull$$$0(12);
        }
        set(obj, Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setInt(Object obj, int i) {
        if (obj == null) {
            $$$reportNull$$$0(13);
        }
        set(obj, Integer.valueOf(i));
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setShort(Object obj, short s) {
        if (obj == null) {
            $$$reportNull$$$0(14);
        }
        set(obj, Short.valueOf(s));
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setLong(Object obj, long j) {
        if (obj == null) {
            $$$reportNull$$$0(15);
        }
        set(obj, Long.valueOf(j));
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setDouble(Object obj, double d) {
        if (obj == null) {
            $$$reportNull$$$0(16);
        }
        set(obj, Double.valueOf(d));
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setFloat(Object obj, float f) {
        if (obj == null) {
            $$$reportNull$$$0(17);
        }
        set(obj, Float.valueOf(f));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        Method method;
        if (cls == null) {
            $$$reportNull$$$0(18);
        }
        T t = (T) this.myReadMethod.getAnnotation(cls);
        return (t != null || (method = this.myWriteMethod) == null) ? t : (T) method.getAnnotation(cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public Type getGenericType() {
        Type type = this.myGenericType;
        if (type == null) {
            $$$reportNull$$$0(21);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public boolean isWritable() {
        Method method = this.myWriteMethod;
        return method != null && method.isAccessible();
    }

    public String toString() {
        return "PropertyAccessor(name=" + this.myName + ", class=" + this.myReadMethod.getDeclaringClass().getName() + Constant.AFTER_QUTO;
    }
}
