package com.reandroid.jcommand.utils;

import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.LastArgs;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.annotations.OtherOption;
import com.reandroid.jcommand.exceptions.CommandFormatException;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class ReflectionUtil {
    public static void setCollection(Object obj, Field field, String str) {
        try {
            Collection hashSet = (Collection) field.get(obj);
            if (hashSet == null) {
                if (isInstanceClass(field.getType(), List.class)) {
                    hashSet = new ArrayList();
                } else {
                    hashSet = new HashSet();
                }
                field.set(obj, hashSet);
            }
            hashSet.add(createObject(getCollectionEntryType(field), str));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    private static Object createObject(Class<?> cls, String str) {
        if (cls == null || String.class.equals(cls)) {
            return str;
        }
        if (cls.isEnum()) {
            for (Object obj : cls.getEnumConstants()) {
                Enum r3 = (Enum) obj;
                if (str.equalsIgnoreCase(r3.name())) {
                    return r3;
                }
            }
            throw new CommandFormatException(cls, str);
        }
        if (File.class.equals(cls)) {
            return new File(str);
        }
        if (Integer.class.equals(cls)) {
            try {
                return Integer.decode(str);
            } catch (NumberFormatException unused) {
                throw new CommandFormatException(Integer.class, str);
            }
        }
        if (Long.class.equals(cls)) {
            try {
                return Long.decode(str);
            } catch (NumberFormatException unused2) {
                throw new CommandFormatException(Long.class, str);
            }
        }
        if (Double.class.equals(cls)) {
            try {
                return Double.valueOf(Double.parseDouble(str));
            } catch (NumberFormatException unused3) {
                throw new CommandFormatException(Long.class, str);
            }
        }
        if (Boolean.class.equals(cls)) {
            if ("true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str)) {
                return true;
            }
            if ("false".equalsIgnoreCase(str) || "no".equalsIgnoreCase(str)) {
                return false;
            }
            throw new CommandFormatException(Boolean.class, str);
        }
        throw new RuntimeException("Unsupported collection entry type: " + cls);
    }

    public static Class<?> getCollectionEntryType(Field field) {
        Type[] actualTypeArguments;
        Type type = field.getAnnotatedType().getType();
        if ((type instanceof ParameterizedType) && (actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments()) != null && actualTypeArguments.length == 1) {
            try {
                return Class.forName(actualTypeArguments[0].getTypeName());
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static void setFile(Object obj, Field field, String str) {
        try {
            field.set(obj, new File(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setObject(Object obj, Field field, Object obj2) {
        try {
            field.set(obj, obj2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setEnum(Object obj, Field field, String str) {
        Class<?> type = field.getType();
        for (Object obj2 : type.getEnumConstants()) {
            if (str.equalsIgnoreCase(((Enum) obj2).name())) {
                setObject(obj, field, obj2);
                return;
            }
        }
        throw new CommandFormatException(type, str);
    }

    public static void setDoubleObject(Object obj, Field field, String str) {
        try {
            field.set(obj, Double.valueOf(Double.parseDouble(str)));
        } catch (NumberFormatException unused) {
            throw new CommandFormatException(Double.class, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDouble(Object obj, Field field, String str) {
        try {
            field.setDouble(obj, Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            throw new CommandFormatException(Double.class, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLongObject(Object obj, Field field, String str) {
        try {
            field.set(obj, Long.decode(str));
        } catch (NumberFormatException unused) {
            throw new CommandFormatException(Long.class, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLong(Object obj, Field field, String str) {
        try {
            field.setLong(obj, Long.decode(str).longValue());
        } catch (NumberFormatException unused) {
            throw new CommandFormatException(Long.class, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setIntObject(Object obj, Field field, String str) {
        try {
            field.set(obj, Integer.decode(str));
        } catch (NumberFormatException unused) {
            throw new CommandFormatException(Integer.class, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setInt(Object obj, Field field, String str) {
        try {
            field.setInt(obj, Integer.decode(str).intValue());
        } catch (NumberFormatException unused) {
            throw new CommandFormatException(Integer.class, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setBooleanObject(Object obj, Field field, String str) {
        boolean z;
        if ("true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str)) {
            z = true;
        } else if ("false".equalsIgnoreCase(str) || "no".equalsIgnoreCase(str)) {
            z = false;
        } else {
            throw new CommandFormatException(Boolean.class, str);
        }
        try {
            field.set(obj, z);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setBoolean(Object obj, Field field, String str) {
        boolean z;
        if ("true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str)) {
            z = true;
        } else {
            if (!"false".equalsIgnoreCase(str) && !"no".equalsIgnoreCase(str)) {
                throw new CommandFormatException(Boolean.class, str);
            }
            z = false;
        }
        setBoolean(obj, field, z);
    }

    public static void setBoolean(Object obj, Field field, boolean z) {
        try {
            if (field.getType().equals(Boolean.TYPE)) {
                field.setBoolean(obj, z);
            } else {
                field.set(obj, Boolean.valueOf(z));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> listNames(Field field) {
        ArrayList arrayList = new ArrayList();
        OptionArg optionArg = (OptionArg) field.getAnnotation(OptionArg.class);
        if (optionArg != null) {
            arrayList.add(optionArg.name());
            for (String str : optionArg.alternates()) {
                if (str != null) {
                    arrayList.add(str);
                }
            }
        }
        ChoiceArg choiceArg = (ChoiceArg) field.getAnnotation(ChoiceArg.class);
        if (choiceArg != null) {
            arrayList.add(choiceArg.name());
            for (String str2 : choiceArg.alternates()) {
                if (str2 != null) {
                    arrayList.add(str2);
                }
            }
        }
        return arrayList;
    }

    public static String getArgName(Field field) {
        OptionArg optionArg = (OptionArg) field.getAnnotation(OptionArg.class);
        if (optionArg != null) {
            return optionArg.name();
        }
        ChoiceArg choiceArg = (ChoiceArg) field.getAnnotation(ChoiceArg.class);
        if (choiceArg != null) {
            return choiceArg.name();
        }
        return null;
    }

    public static boolean isFlagArg(Field field) {
        OptionArg optionArg = (OptionArg) field.getAnnotation(OptionArg.class);
        if (optionArg != null) {
            return optionArg.flag();
        }
        return false;
    }

    public static List<OptionArg> listOptionArgs(Class<?> cls) {
        List<Field> listListInstanceFields = listInstanceFields(cls);
        ArrayList arrayList = new ArrayList();
        Iterator<Field> it = listListInstanceFields.iterator();
        while (it.hasNext()) {
            OptionArg optionArg = (OptionArg) it.next().getAnnotation(OptionArg.class);
            if (optionArg != null) {
                arrayList.add(optionArg);
            }
        }
        return arrayList;
    }

    public static List<OtherOption> listOtherOptions(Class<?> cls) {
        List<Method> listListMethods = listMethods(cls);
        ArrayList arrayList = new ArrayList();
        Iterator<Method> it = listListMethods.iterator();
        while (it.hasNext()) {
            OtherOption otherOption = (OtherOption) it.next().getAnnotation(OtherOption.class);
            if (otherOption != null) {
                arrayList.add(otherOption);
            }
        }
        return arrayList;
    }

    public static List<ChoiceArg> listChoiceArgs(Class<?> cls) {
        List<Field> listListInstanceFields = listInstanceFields(cls);
        ArrayList arrayList = new ArrayList();
        Iterator<Field> it = listListInstanceFields.iterator();
        while (it.hasNext()) {
            ChoiceArg choiceArg = (ChoiceArg) it.next().getAnnotation(ChoiceArg.class);
            if (choiceArg != null) {
                arrayList.add(choiceArg);
            }
        }
        return arrayList;
    }

    public static LastArgs getLastArgs(Class<?> cls) {
        Iterator<Field> it = listInstanceFields(cls).iterator();
        while (it.hasNext()) {
            LastArgs lastArgs = (LastArgs) it.next().getAnnotation(LastArgs.class);
            if (lastArgs != null) {
                return lastArgs;
            }
        }
        return null;
    }

    public static List<Field> listInstanceFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        listInstanceFields(cls, arrayList);
        return arrayList;
    }

    private static void listInstanceFields(Class<?> cls, List<Field> list) {
        if (cls == null || Object.class.equals(cls)) {
            return;
        }
        for (Field field : cls.getDeclaredFields()) {
            if ((field.getModifiers() & 8) != 8) {
                list.add(field);
            }
        }
        listInstanceFields(cls.getSuperclass(), list);
    }

    public static List<Method> listMethods(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        listMethods(cls, arrayList);
        return arrayList;
    }

    private static void listMethods(Class<?> cls, List<Method> list) {
        if (cls == null || Object.class.equals(cls)) {
            return;
        }
        for (Method method : cls.getDeclaredMethods()) {
            if (method != null) {
                list.add(method);
            }
        }
        listMethods(cls.getSuperclass(), list);
    }

    public static <T> T createNew(Class<T> cls) {
        try {
            return cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isInstanceClass(Class<?> cls, Class<?> cls2) {
        if (cls == null) {
            return false;
        }
        if (cls.equals(cls2)) {
            return true;
        }
        if (cls.isPrimitive() || cls.equals(Object.class)) {
            return false;
        }
        for (Class<?> cls3 : cls.getInterfaces()) {
            if (isInstanceClass(cls3, cls2)) {
                return true;
            }
        }
        return isInstanceClass(cls.getSuperclass(), cls2);
    }
}
