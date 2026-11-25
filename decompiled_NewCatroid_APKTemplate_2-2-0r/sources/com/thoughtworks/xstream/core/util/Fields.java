package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Fields.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Fields {
    public static Field locate(Class cls, Class cls2, boolean z) {
        Field field = null;
        try {
            Field[] declaredFields = cls.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                if (Modifier.isStatic(declaredFields[i].getModifiers()) == z && cls2.isAssignableFrom(declaredFields[i].getType())) {
                    field = declaredFields[i];
                }
            }
            if (field != null && !field.isAccessible()) {
                field.setAccessible(true);
            }
        } catch (NoClassDefFoundError | SecurityException unused) {
        }
        return field;
    }

    public static Field find(Class cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            return declaredField;
        } catch (NoClassDefFoundError e) {
            throw wrap("Cannot access field", cls, str, e);
        } catch (NoSuchFieldException e2) {
            throw wrap("Cannot access field", cls, str, e2);
        } catch (SecurityException e3) {
            throw wrap("Cannot access field", cls, str, e3);
        }
    }

    public static void write(Field field, Object obj, Object obj2) {
        try {
            field.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw wrap("Cannot write field", field.getType(), field.getName(), e);
        } catch (IllegalArgumentException e2) {
            throw wrap("Cannot write field", field.getType(), field.getName(), e2);
        } catch (NoClassDefFoundError e3) {
            throw wrap("Cannot write field", field.getType(), field.getName(), e3);
        } catch (SecurityException e4) {
            throw wrap("Cannot write field", field.getType(), field.getName(), e4);
        }
    }

    public static Object read(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw wrap("Cannot read field", field.getType(), field.getName(), e);
        } catch (IllegalArgumentException e2) {
            throw wrap("Cannot read field", field.getType(), field.getName(), e2);
        } catch (NoClassDefFoundError e3) {
            throw wrap("Cannot read field", field.getType(), field.getName(), e3);
        } catch (SecurityException e4) {
            throw wrap("Cannot read field", field.getType(), field.getName(), e4);
        }
    }

    private static ObjectAccessException wrap(String str, Class cls, String str2, Throwable th) {
        ObjectAccessException objectAccessException = new ObjectAccessException(str, th);
        objectAccessException.add("field", new StringBuffer().append(cls.getName()).append(".").append(str2).toString());
        return objectAccessException;
    }
}
