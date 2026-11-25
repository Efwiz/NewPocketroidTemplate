package org.jetbrains.kotlin.com.intellij.serialization;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: loaded from: classes3.dex */
final class FieldAccessor implements MutableAccessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Field field;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 16:
            case 17:
            case 18:
            case 19:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 16:
            case 17:
            case 18:
            case 19:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "o";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[0] = "host";
                break;
            case 15:
                objArr[0] = "annotationClass";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/serialization/FieldAccessor";
                break;
            default:
                objArr[0] = "field";
                break;
        }
        switch (i) {
            case 16:
                objArr[1] = "getName";
                break;
            case 17:
                objArr[1] = "getValueClass";
                break;
            case 18:
                objArr[1] = "getGenericType";
                break;
            case 19:
                objArr[1] = "toString";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/serialization/FieldAccessor";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "read";
                break;
            case 2:
                objArr[2] = "readUnsafe";
                break;
            case 3:
                objArr[2] = "readInt";
                break;
            case 4:
                objArr[2] = "readLong";
                break;
            case 5:
                objArr[2] = "readFloat";
                break;
            case 6:
                objArr[2] = "readDouble";
                break;
            case 7:
                objArr[2] = "readBoolean";
                break;
            case 8:
                objArr[2] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            case 9:
                objArr[2] = "setBoolean";
                break;
            case 10:
                objArr[2] = "setInt";
                break;
            case 11:
                objArr[2] = "setShort";
                break;
            case 12:
                objArr[2] = "setLong";
                break;
            case 13:
                objArr[2] = "setFloat";
                break;
            case 14:
                objArr[2] = "setDouble";
                break;
            case 15:
                objArr[2] = "getAnnotation";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 16:
            case 17:
            case 18:
            case 19:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    FieldAccessor(Field field) {
        if (field == null) {
            $$$reportNull$$$0(0);
        }
        this.field = field;
        field.setAccessible(true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public Object read(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(1);
        }
        try {
            return this.field.get(obj);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Reading " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void set(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(8);
        }
        try {
            this.field.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setBoolean(Object obj, boolean z) {
        if (obj == null) {
            $$$reportNull$$$0(9);
        }
        try {
            this.field.setBoolean(obj, z);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setInt(Object obj, int i) {
        if (obj == null) {
            $$$reportNull$$$0(10);
        }
        try {
            this.field.setInt(obj, i);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setShort(Object obj, short s) {
        if (obj == null) {
            $$$reportNull$$$0(11);
        }
        try {
            this.field.setShort(obj, s);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setLong(Object obj, long j) {
        if (obj == null) {
            $$$reportNull$$$0(12);
        }
        try {
            this.field.setLong(obj, j);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setFloat(Object obj, float f) {
        if (obj == null) {
            $$$reportNull$$$0(13);
        }
        try {
            this.field.setFloat(obj, f);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor
    public void setDouble(Object obj, double d) {
        if (obj == null) {
            $$$reportNull$$$0(14);
        }
        try {
            this.field.setDouble(obj, d);
        } catch (IllegalAccessException e) {
            throw new SerializationException("Writing " + this.field, e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(15);
        }
        return (T) this.field.getAnnotation(cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public String getName() {
        String name2 = this.field.getName();
        if (name2 == null) {
            $$$reportNull$$$0(16);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public Type getGenericType() {
        Type genericType = this.field.getGenericType();
        if (genericType == null) {
            $$$reportNull$$$0(18);
        }
        return genericType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor
    public boolean isWritable() {
        return this.field.isAccessible();
    }

    public String toString() {
        String str = "FieldAccessor(name=" + this.field.getName() + ", class=" + this.field.getDeclaringClass().getName() + Constant.AFTER_QUTO;
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        return str;
    }
}
