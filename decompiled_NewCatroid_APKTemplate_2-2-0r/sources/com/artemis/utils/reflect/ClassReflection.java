package com.artemis.utils.reflect;

import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: classes8.dex */
public final class ClassReflection {
    public static Class forName(String str) throws ReflectionException {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException("Class not found: " + str, e);
        }
    }

    public static String getSimpleName(Class cls) {
        return cls.getSimpleName();
    }

    public static boolean isInstance(Class cls, Object obj) {
        return cls.isInstance(obj);
    }

    public static boolean isAssignableFrom(Class cls, Class cls2) {
        return cls.isAssignableFrom(cls2);
    }

    public static boolean isMemberClass(Class cls) {
        return cls.isMemberClass();
    }

    public static boolean isStaticClass(Class cls) {
        return Modifier.isStatic(cls.getModifiers());
    }

    public static boolean isAbstractClass(Class cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }

    public static <T> T newInstance(Class<T> cls) throws ReflectionException {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Could not instantiate instance of class: " + cls.getName() + ". Make sure class has a public no-arg constructor.", e);
        } catch (InstantiationException e2) {
            throw new ReflectionException("Could not instantiate instance of class: " + cls.getName() + ". Make sure class has a public no-arg constructor.", e2);
        }
    }

    public static Constructor[] getConstructors(Class cls) {
        java.lang.reflect.Constructor<?>[] constructors = cls.getConstructors();
        Constructor[] constructorArr = new Constructor[constructors.length];
        int length = constructors.length;
        for (int i = 0; i < length; i++) {
            constructorArr[i] = new Constructor(constructors[i]);
        }
        return constructorArr;
    }

    public static Constructor getConstructor(Class cls, Class... clsArr) throws ReflectionException {
        try {
            return new Constructor(cls.getConstructor(clsArr));
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("Constructor not found for class: " + cls.getName(), e);
        } catch (SecurityException e2) {
            throw new ReflectionException("Security violation occurred while getting constructor for class: '" + cls.getName() + "'.", e2);
        }
    }

    public static Constructor getDeclaredConstructor(Class cls, Class... clsArr) throws ReflectionException {
        try {
            return new Constructor(cls.getDeclaredConstructor(clsArr));
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("Constructor not found for class: " + cls.getName(), e);
        } catch (SecurityException e2) {
            throw new ReflectionException("Security violation while getting constructor for class: " + cls.getName(), e2);
        }
    }

    public static Method[] getMethods(Class cls) {
        java.lang.reflect.Method[] methods = cls.getMethods();
        Method[] methodArr = new Method[methods.length];
        int length = methods.length;
        for (int i = 0; i < length; i++) {
            methodArr[i] = new Method(methods[i]);
        }
        return methodArr;
    }

    public static Method getMethod(Class cls, String str, Class... clsArr) throws ReflectionException {
        try {
            return new Method(cls.getMethod(str, clsArr));
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("Method not found: " + str + ", for class: " + cls.getName(), e);
        } catch (SecurityException e2) {
            throw new ReflectionException("Security violation while getting method: " + str + ", for class: " + cls.getName(), e2);
        }
    }

    public static Method[] getDeclaredMethods(Class cls) {
        java.lang.reflect.Method[] declaredMethods = cls.getDeclaredMethods();
        Method[] methodArr = new Method[declaredMethods.length];
        int length = declaredMethods.length;
        for (int i = 0; i < length; i++) {
            methodArr[i] = new Method(declaredMethods[i]);
        }
        return methodArr;
    }

    public static Method getDeclaredMethod(Class cls, String str, Class... clsArr) throws ReflectionException {
        try {
            return new Method(cls.getDeclaredMethod(str, clsArr));
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("Method not found: " + str + ", for class: " + cls.getName(), e);
        } catch (SecurityException e2) {
            throw new ReflectionException("Security violation while getting method: " + str + ", for class: " + cls.getName(), e2);
        }
    }

    public static Field[] getFields(Class cls) {
        java.lang.reflect.Field[] fields = cls.getFields();
        Field[] fieldArr = new Field[fields.length];
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            fieldArr[i] = new Field(fields[i]);
        }
        return fieldArr;
    }

    public static Field getField(Class cls, String str) throws ReflectionException {
        try {
            return new Field(cls.getField(str));
        } catch (NoSuchFieldException e) {
            throw new ReflectionException("Field not found: " + str + ", for class: " + cls.getName(), e);
        } catch (SecurityException e2) {
            throw new ReflectionException("Security violation while getting field: " + str + ", for class: " + cls.getName(), e2);
        }
    }

    public static Field[] getDeclaredFields(Class cls) {
        java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
        Field[] fieldArr = new Field[declaredFields.length];
        int length = declaredFields.length;
        for (int i = 0; i < length; i++) {
            fieldArr[i] = new Field(declaredFields[i]);
        }
        return fieldArr;
    }

    public static <T extends java.lang.annotation.Annotation> T getAnnotation(Class cls, Class<T> cls2) {
        Annotation declaredAnnotation = getDeclaredAnnotation(cls, cls2);
        if (declaredAnnotation != null) {
            return (T) declaredAnnotation.getAnnotation(cls2);
        }
        return null;
    }

    public static Field getDeclaredField(Class cls, String str) throws ReflectionException {
        try {
            return new Field(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e) {
            throw new ReflectionException("Field not found: " + str + ", for class: " + cls.getName(), e);
        } catch (SecurityException e2) {
            throw new ReflectionException("Security violation while getting field: " + str + ", for class: " + cls.getName(), e2);
        }
    }

    public static boolean isAnnotationPresent(Class cls, Class<? extends java.lang.annotation.Annotation> cls2) {
        return cls.isAnnotationPresent(cls2);
    }

    public static Annotation[] getDeclaredAnnotations(Class cls) {
        java.lang.annotation.Annotation[] declaredAnnotations = cls.getDeclaredAnnotations();
        Annotation[] annotationArr = new Annotation[declaredAnnotations.length];
        for (int i = 0; i < declaredAnnotations.length; i++) {
            annotationArr[i] = new Annotation(declaredAnnotations[i]);
        }
        return annotationArr;
    }

    public static Annotation getDeclaredAnnotation(Class cls, Class<? extends java.lang.annotation.Annotation> cls2) {
        for (java.lang.annotation.Annotation annotation : cls.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(cls2)) {
                return new Annotation(annotation);
            }
        }
        return null;
    }
}
