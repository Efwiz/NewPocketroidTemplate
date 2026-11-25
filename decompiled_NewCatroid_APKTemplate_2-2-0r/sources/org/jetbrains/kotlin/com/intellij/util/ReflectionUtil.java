package org.jetbrains.kotlin.com.intellij.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes3.dex */
public final class ReflectionUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ReflectionUtil.class);
    private static final Object unsafe;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 11 || i == 15 || i == 18 || i == 21 || i == 48 || i == 50 || i == 65 || i == 73 || i == 88 || i == 7 || i == 8 || i == 9 || i == 68 || i == 69 || i == 90 || i == 91 || i == 94 || i == 95) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 11 || i == 15 || i == 18 || i == 21 || i == 48 || i == 50 || i == 65 || i == 73 || i == 88 || i == 7 || i == 8 || i == 9 || i == 68 || i == 69 || i == 90 || i == 91 || i == 94 || i == 95) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 13:
                objArr[0] = "classType";
                break;
            case 2:
            case 62:
            default:
                objArr[0] = "variable";
                break;
            case 4:
                objArr[0] = "anInterface";
                break;
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 15:
            case 18:
            case 21:
            case 48:
            case 50:
            case 65:
            case 68:
            case 69:
            case 73:
            case 88:
            case 90:
            case 91:
            case 94:
            case 95:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ReflectionUtil";
                break;
            case 6:
            case 89:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 10:
                objArr[0] = "parameterizedType";
                break;
            case 12:
                objArr[0] = "genericType";
                break;
            case 14:
            case 16:
            case 19:
            case 27:
                objArr[0] = "clazz";
                break;
            case 17:
            case 28:
            case 30:
            case 33:
            case 36:
            case 39:
            case 42:
                objArr[0] = "name";
                break;
            case 20:
            case 55:
            case 57:
            case 61:
                objArr[0] = "fieldName";
                break;
            case 22:
                objArr[0] = "rootClass";
                break;
            case 23:
            case 25:
                objArr[0] = "checker";
                break;
            case 24:
                objArr[0] = "visited";
                break;
            case 26:
                objArr[0] = Namer.METADATA_SUPERTYPES;
                break;
            case 29:
                objArr[0] = "object";
                break;
            case 31:
            case 58:
            case 59:
            case 84:
            case 85:
            case 86:
                objArr[0] = "field";
                break;
            case 32:
            case 49:
                objArr[0] = "methods";
                break;
            case 34:
            case 37:
            case 40:
            case 53:
                objArr[0] = "parameters";
                break;
            case 35:
            case 38:
            case 41:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 63:
            case 64:
            case 66:
            case 67:
            case 70:
                objArr[0] = "aClass";
                break;
            case 51:
                objArr[0] = "instanceClass";
                break;
            case 52:
                objArr[0] = "methodName";
                break;
            case 54:
            case 56:
            case 60:
            case 98:
                objArr[0] = "objectClass";
                break;
            case 71:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 72:
                objArr[0] = "args";
                break;
            case 74:
            case 77:
            case 82:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 75:
            case 78:
            case 83:
                objArr[0] = "to";
                break;
            case 76:
            case 79:
                objArr[0] = "fields";
                break;
            case 80:
                objArr[0] = "first";
                break;
            case 81:
                objArr[0] = "second";
                break;
            case 87:
                objArr[0] = "fqn";
                break;
            case 92:
                objArr[0] = "ownerClass";
                break;
            case 93:
                objArr[0] = "fieldType";
                break;
            case 96:
                objArr[0] = "ancestor";
                break;
            case 97:
                objArr[0] = "descendant";
                break;
        }
        if (i == 5) {
            objArr[1] = "declarationToString";
        } else if (i == 11) {
            objArr[1] = "getActualTypeArguments";
        } else if (i == 15) {
            objArr[1] = "collectFields";
        } else if (i == 18) {
            objArr[1] = "findField";
        } else if (i == 21) {
            objArr[1] = "findAssignableField";
        } else if (i == 48) {
            objArr[1] = "getClassDeclaredFields";
        } else if (i == 50) {
            objArr[1] = "filterRealMethods";
        } else if (i == 65) {
            objArr[1] = "getDefaultConstructor";
        } else if (i == 73) {
            objArr[1] = "createInstance";
        } else if (i == 88) {
            objArr[1] = "forName";
        } else if (i == 7 || i == 8 || i == 9) {
            objArr[1] = "getRawType";
        } else if (i == 68 || i == 69) {
            objArr[1] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
        } else if (i == 90 || i == 91) {
            objArr[1] = "boxType";
        } else if (i == 94) {
            objArr[1] = "getTheOnlyVolatileInstanceFieldOfClass";
        } else if (i != 95) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ReflectionUtil";
        } else {
            objArr[1] = "getUnsafe";
        }
        switch (i) {
            case 4:
                objArr[2] = "declarationToString";
                break;
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 15:
            case 18:
            case 21:
            case 48:
            case 50:
            case 65:
            case 68:
            case 69:
            case 73:
            case 88:
            case 90:
            case 91:
            case 94:
            case 95:
                break;
            case 6:
                objArr[2] = "getRawType";
                break;
            case 10:
                objArr[2] = "getActualTypeArguments";
                break;
            case 12:
            case 13:
                objArr[2] = "substituteGenericType";
                break;
            case 14:
                objArr[2] = "collectFields";
                break;
            case 16:
            case 17:
                objArr[2] = "findField";
                break;
            case 19:
            case 20:
                objArr[2] = "findAssignableField";
                break;
            case 22:
            case 23:
                objArr[2] = "findFieldInHierarchy";
                break;
            case 24:
            case 25:
            case 26:
                objArr[2] = "processInterfaces";
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[2] = "resetField";
                break;
            case 32:
            case 33:
            case 34:
                objArr[2] = "findMethod";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "getMethod";
                break;
            case 38:
            case 39:
            case 40:
                objArr[2] = "getDeclaredMethod";
                break;
            case 41:
            case 42:
                objArr[2] = "getDeclaredField";
                break;
            case 43:
            case 44:
                objArr[2] = "getClassPublicMethods";
                break;
            case 45:
            case 46:
                objArr[2] = "getClassDeclaredMethods";
                break;
            case 47:
                objArr[2] = "getClassDeclaredFields";
                break;
            case 49:
                objArr[2] = "filterRealMethods";
                break;
            case 51:
            case 52:
            case 53:
                objArr[2] = "getMethodDeclaringClass";
                break;
            case 54:
            case 55:
                objArr[2] = "getField";
                break;
            case 56:
            case 57:
                objArr[2] = "getStaticFieldValue";
                break;
            case 58:
                objArr[2] = "getFieldValue";
                break;
            case 59:
                objArr[2] = "isInstanceField";
                break;
            case 60:
            case 61:
                objArr[2] = "setField";
                break;
            case 62:
            case 63:
                objArr[2] = "resolveVariableInHierarchy";
                break;
            case 64:
                objArr[2] = "getDefaultConstructor";
                break;
            case 66:
            case 67:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
            case 70:
                objArr[2] = "createAsDataClass";
                break;
            case 71:
            case 72:
                objArr[2] = "createInstance";
                break;
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
                objArr[2] = "copyFields";
                break;
            case 80:
            case 81:
                objArr[2] = "comparePublicNonFinalFields";
                break;
            case 82:
            case 83:
            case 84:
                objArr[2] = "copyFieldValue";
                break;
            case 85:
                objArr[2] = "isPublic";
                break;
            case 86:
                objArr[2] = "isFinal";
                break;
            case 87:
                objArr[2] = "forName";
                break;
            case 89:
                objArr[2] = "boxType";
                break;
            case 92:
            case 93:
                objArr[2] = "getTheOnlyVolatileInstanceFieldOfClass";
                break;
            case 96:
            case 97:
                objArr[2] = "isAssignable";
                break;
            case 98:
                objArr[2] = "dumpFields";
                break;
            default:
                objArr[2] = "resolveVariable";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 11 && i != 15 && i != 18 && i != 21 && i != 48 && i != 50 && i != 65 && i != 73 && i != 88 && i != 7 && i != 8 && i != 9 && i != 68 && i != 69 && i != 90 && i != 91 && i != 94 && i != 95) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        try {
            Class<?> cls = Class.forName("sun.misc.Unsafe");
            Object staticFieldValue = getStaticFieldValue(cls, cls, "theUnsafe");
            unsafe = staticFieldValue;
            if (staticFieldValue == null) {
                throw new RuntimeException("Could not find 'theUnsafe' field in the Unsafe class");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ReflectionUtil() {
    }

    public static List<Field> collectFields(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(14);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Class<?>> it = JBIterableClassTraverser.classTraverser(cls).iterator();
        while (it.hasNext()) {
            Collections.addAll(arrayList, it.next().getDeclaredFields());
        }
        return arrayList;
    }

    public static Field findField(Class<?> cls, final Class<?> cls2, final String str) throws NoSuchFieldException {
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        Field fieldFindFieldInHierarchy = findFieldInHierarchy(cls, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.ReflectionUtil$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ReflectionUtil.lambda$findField$0(str, cls2, (Field) obj);
            }
        });
        if (fieldFindFieldInHierarchy == null) {
            throw new NoSuchFieldException("Class: " + cls + " name: " + str + " type: " + cls2);
        }
        if (fieldFindFieldInHierarchy == null) {
            $$$reportNull$$$0(18);
        }
        return fieldFindFieldInHierarchy;
    }

    static /* synthetic */ boolean lambda$findField$0(String str, Class cls, Field field) {
        return str.equals(field.getName()) && (cls == null || field.getType().equals(cls));
    }

    public static Field findAssignableField(Class<?> cls, final Class<?> cls2, final String str) throws NoSuchFieldException {
        if (cls == null) {
            $$$reportNull$$$0(19);
        }
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        Field fieldFindFieldInHierarchy = findFieldInHierarchy(cls, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.ReflectionUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ReflectionUtil.lambda$findAssignableField$1(str, cls2, (Field) obj);
            }
        });
        if (fieldFindFieldInHierarchy == null) {
            throw new NoSuchFieldException("Class: " + cls + " fieldName: " + str + " fieldType: " + cls2);
        }
        if (fieldFindFieldInHierarchy == null) {
            $$$reportNull$$$0(21);
        }
        return fieldFindFieldInHierarchy;
    }

    static /* synthetic */ boolean lambda$findAssignableField$1(String str, Class cls, Field field) {
        return str.equals(field.getName()) && (cls == null || cls.isAssignableFrom(field.getType()));
    }

    public static Field findFieldInHierarchy(Class<?> cls, Predicate<? super Field> predicate) {
        if (cls == null) {
            $$$reportNull$$$0(22);
        }
        if (predicate == null) {
            $$$reportNull$$$0(23);
        }
        for (Class<?> superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                if (predicate.test(field)) {
                    field.setAccessible(true);
                    return field;
                }
            }
        }
        return processInterfaces(cls.getInterfaces(), new HashSet(), predicate);
    }

    private static Field processInterfaces(Class<?>[] clsArr, Set<? super Class<?>> set, Predicate<? super Field> predicate) {
        if (set == null) {
            $$$reportNull$$$0(24);
        }
        if (predicate == null) {
            $$$reportNull$$$0(25);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(26);
        }
        for (Class<?> cls : clsArr) {
            if (set.add(cls)) {
                for (Field field : cls.getDeclaredFields()) {
                    if (predicate.test(field)) {
                        field.setAccessible(true);
                        return field;
                    }
                }
                Field fieldProcessInterfaces = processInterfaces(cls.getInterfaces(), set, predicate);
                if (fieldProcessInterfaces != null) {
                    return fieldProcessInterfaces;
                }
            }
        }
        return null;
    }

    public static void resetField(Class<?> cls, Class<?> cls2, String str) {
        if (cls == null) {
            $$$reportNull$$$0(27);
        }
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        try {
            resetField(null, findField(cls, cls2, str));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetField(Object obj, Field field) {
        if (field == null) {
            $$$reportNull$$$0(31);
        }
        field.setAccessible(true);
        Class<?> type = field.getType();
        try {
            if (type.isPrimitive()) {
                if (Boolean.TYPE.equals(type)) {
                    field.set(obj, Boolean.FALSE);
                    return;
                }
                if (Integer.TYPE.equals(type)) {
                    field.set(obj, 0);
                    return;
                } else if (Double.TYPE.equals(type)) {
                    field.set(obj, Double.valueOf(0.0d));
                    return;
                } else {
                    if (Float.TYPE.equals(type)) {
                        field.set(obj, Float.valueOf(0.0f));
                        return;
                    }
                    return;
                }
            }
            field.set(obj, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method makeAccessible(Method method) {
        method.setAccessible(true);
        return method;
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>... clsArr) {
        if (cls == null) {
            $$$reportNull$$$0(35);
        }
        if (str == null) {
            $$$reportNull$$$0(36);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(37);
        }
        try {
            return makeAccessible(cls.getMethod(str, clsArr));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Method getDeclaredMethod(Class<?> cls, String str, Class<?>... clsArr) {
        if (cls == null) {
            $$$reportNull$$$0(38);
        }
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(40);
        }
        try {
            return makeAccessible(cls.getDeclaredMethod(str, clsArr));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Field getDeclaredField(Class<?> cls, final String str) {
        if (cls == null) {
            $$$reportNull$$$0(41);
        }
        if (str == null) {
            $$$reportNull$$$0(42);
        }
        return findFieldInHierarchy(cls, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.ReflectionUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equals(((Field) obj).getName());
            }
        });
    }

    public static Class<?> getMethodDeclaringClass(Class<?> cls, String str, Class<?>... clsArr) {
        if (cls == null) {
            $$$reportNull$$$0(51);
        }
        if (str == null) {
            $$$reportNull$$$0(52);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(53);
        }
        Method method = getMethod(cls, str, clsArr);
        if (method != null) {
            return method.getDeclaringClass();
        }
        while (cls != null) {
            Method declaredMethod = getDeclaredMethod(cls, str, clsArr);
            if (declaredMethod != null) {
                return declaredMethod.getDeclaringClass();
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    public static <T> T getField(Class<?> cls, Object obj, Class<T> cls2, String str) {
        if (cls == null) {
            $$$reportNull$$$0(54);
        }
        if (str == null) {
            $$$reportNull$$$0(55);
        }
        try {
            return (T) getFieldValue(findAssignableField(cls, cls2, str), obj);
        } catch (NoSuchFieldException e) {
            LOG.debug(e);
            return null;
        }
    }

    public static <T> T getStaticFieldValue(Class<?> cls, Class<T> cls2, String str) {
        if (cls == null) {
            $$$reportNull$$$0(56);
        }
        if (str == null) {
            $$$reportNull$$$0(57);
        }
        try {
            Field fieldFindAssignableField = findAssignableField(cls, cls2, str);
            if (isInstanceField(fieldFindAssignableField)) {
                throw new IllegalArgumentException("Field " + cls + "." + str + " is not static");
            }
            return (T) getFieldValue(fieldFindAssignableField, null);
        } catch (NoSuchFieldException e) {
            LOG.debug(e);
            return null;
        }
    }

    public static <T> T getFieldValue(Field field, Object obj) {
        if (field == null) {
            $$$reportNull$$$0(58);
        }
        try {
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            LOG.debug(e);
            return null;
        }
    }

    public static boolean isInstanceField(Field field) {
        if (field == null) {
            $$$reportNull$$$0(59);
        }
        return !Modifier.isStatic(field.getModifiers());
    }

    public static <T> T newInstance(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(66);
        }
        return (T) newInstance(cls, true);
    }

    public static <T> T newInstance(Class<T> cls, boolean z) {
        T t;
        if (cls == null) {
            $$$reportNull$$$0(67);
        }
        try {
            Constructor<T> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            try {
                declaredConstructor.setAccessible(true);
            } catch (SecurityException unused) {
            }
            T tNewInstance = declaredConstructor.newInstance(new Object[0]);
            if (tNewInstance == null) {
                $$$reportNull$$$0(68);
            }
            return tNewInstance;
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if ((targetException instanceof ControlFlowException) && (targetException instanceof RuntimeException)) {
                    throw ((RuntimeException) targetException);
                }
            }
            if (!z || (t = (T) createAsDataClass(cls)) == null) {
                ExceptionUtilRt.rethrowUnchecked(e);
                throw new RuntimeException(e);
            }
            if (t == null) {
                $$$reportNull$$$0(69);
            }
            return t;
        }
    }

    private static <T> T createAsDataClass(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(70);
        }
        Annotation[] annotations = cls.getAnnotations();
        int length = annotations.length;
        int i = 0;
        while (true) {
            Exception e = null;
            if (i >= length) {
                return null;
            }
            String name2 = annotations[i].annotationType().getName();
            if (name2.equals("kotlin.Metadata") || name2.equals("kotlin.jvm.internal.KotlinClass")) {
                Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
                SmartList<Constructor> smartList = new SmartList();
                for (Constructor<?> constructor : declaredConstructors) {
                    try {
                        constructor.setAccessible(true);
                    } catch (Throwable unused) {
                    }
                    try {
                        if (constructor.getParameterCount() == 0) {
                            return (T) constructor.newInstance(new Object[0]);
                        }
                        Class<?>[] parameterTypes = constructor.getParameterTypes();
                        for (Class<?> cls2 : parameterTypes) {
                            if (cls2.getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) {
                                smartList.add(constructor);
                            }
                        }
                        return (T) constructor.newInstance(new Object[parameterTypes.length]);
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                for (Constructor constructor2 : smartList) {
                    try {
                        constructor2.setAccessible(true);
                    } catch (Throwable unused2) {
                    }
                    try {
                        continue;
                        return (T) constructor2.newInstance(new Object[0]);
                    } catch (Exception e3) {
                        e = e3;
                    }
                }
                if (e != null) {
                    ExceptionUtil.rethrow(e);
                }
            }
            i++;
        }
    }

    public static <T> T createInstance(Constructor<T> constructor, Object... objArr) {
        if (constructor == null) {
            $$$reportNull$$$0(71);
        }
        if (objArr == null) {
            $$$reportNull$$$0(72);
        }
        try {
            T tNewInstance = constructor.newInstance(objArr);
            if (tNewInstance == null) {
                $$$reportNull$$$0(73);
            }
            return tNewInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static Object getUnsafe() {
        Object obj = unsafe;
        if (obj == null) {
            $$$reportNull$$$0(95);
        }
        return obj;
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2) {
        if (cls == null) {
            $$$reportNull$$$0(96);
        }
        if (cls2 == null) {
            $$$reportNull$$$0(97);
        }
        return cls == cls2 || cls.isAssignableFrom(cls2);
    }
}
