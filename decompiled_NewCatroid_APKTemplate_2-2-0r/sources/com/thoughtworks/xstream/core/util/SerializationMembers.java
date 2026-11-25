package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.ErrorWritingException;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.core.Caching;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/SerializationMembers.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SerializationMembers implements Caching {
    private static final FastField[] OBJECT_TYPE_FIELDS;
    private static final int PERSISTENT_FIELDS_MODIFIER = 26;
    static /* synthetic */ Class class$java$io$ObjectInputStream;
    static /* synthetic */ Class class$java$io$ObjectOutputStream;
    static /* synthetic */ Class class$java$lang$Object;
    private static final Method NO_METHOD = new Object() { // from class: com.thoughtworks.xstream.core.util.SerializationMembers.1
        private void noMethod() {
        }
    }.getClass().getDeclaredMethods()[0];
    private static final Object[] EMPTY_ARGS = new Object[0];
    private static final Class[] EMPTY_CLASSES = new Class[0];
    private static final Map NO_FIELDS = Collections.EMPTY_MAP;
    private Map declaredCache = Collections.synchronizedMap(new HashMap());
    private Map resRepCache = Collections.synchronizedMap(new HashMap());
    private final Map fieldCache = Collections.synchronizedMap(new HashMap());

    public SerializationMembers() {
        int i = 0;
        while (true) {
            FastField[] fastFieldArr = OBJECT_TYPE_FIELDS;
            if (i >= fastFieldArr.length) {
                break;
            }
            this.declaredCache.put(fastFieldArr[i], NO_METHOD);
            i++;
        }
        for (int i2 = 0; i2 < 2; i2++) {
            this.resRepCache.put(OBJECT_TYPE_FIELDS[i2], NO_METHOD);
        }
    }

    static {
        FastField[] fastFieldArr = new FastField[4];
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$;
        }
        fastFieldArr[0] = new FastField(clsClass$, "readResolve");
        Class clsClass$2 = class$java$lang$Object;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$2;
        }
        fastFieldArr[1] = new FastField(clsClass$2, "writeReplace");
        Class clsClass$3 = class$java$lang$Object;
        if (clsClass$3 == null) {
            clsClass$3 = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$3;
        }
        fastFieldArr[2] = new FastField(clsClass$3, "readObject");
        Class clsClass$4 = class$java$lang$Object;
        if (clsClass$4 == null) {
            clsClass$4 = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$4;
        }
        fastFieldArr[3] = new FastField(clsClass$4, "writeObject");
        OBJECT_TYPE_FIELDS = fastFieldArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public Object callReadResolve(Object obj) {
        ErrorWritingException conversionException;
        if (obj == null) {
            return null;
        }
        Class<?> cls = obj.getClass();
        Method rRMethod = getRRMethod(cls, "readResolve");
        if (rRMethod == null) {
            return obj;
        }
        try {
            return rRMethod.invoke(obj, EMPTY_ARGS);
        } catch (IllegalAccessException e) {
            conversionException = new ObjectAccessException("Cannot access method", e);
            conversionException.add("method", new StringBuffer().append(cls.getName()).append(".readResolve()").toString());
            throw conversionException;
        } catch (InvocationTargetException e2) {
            conversionException = new ConversionException("Failed calling method", e2.getTargetException());
            conversionException.add("method", new StringBuffer().append(cls.getName()).append(".readResolve()").toString());
            throw conversionException;
        }
    }

    public Object callWriteReplace(Object obj) {
        ErrorWritingException conversionException;
        ErrorWritingException e;
        if (obj == null) {
            return null;
        }
        Class<?> cls = obj.getClass();
        Method rRMethod = getRRMethod(cls, "writeReplace");
        if (rRMethod == null) {
            return obj;
        }
        try {
            Object objInvoke = rRMethod.invoke(obj, EMPTY_ARGS);
            return (objInvoke == null || obj.getClass().equals(objInvoke.getClass())) ? objInvoke : callWriteReplace(objInvoke);
        } catch (ErrorWritingException e2) {
            e = e2;
            e.add("method", new StringBuffer().append(cls.getName()).append(".writeReplace()").toString());
            throw e;
        } catch (IllegalAccessException e3) {
            conversionException = new ObjectAccessException("Cannot access method", e3);
            e = conversionException;
            e.add("method", new StringBuffer().append(cls.getName()).append(".writeReplace()").toString());
            throw e;
        } catch (InvocationTargetException e4) {
            conversionException = new ConversionException("Failed calling method", e4.getTargetException());
            e = conversionException;
            e.add("method", new StringBuffer().append(cls.getName()).append(".writeReplace()").toString());
            throw e;
        }
    }

    public boolean supportsReadObject(Class cls, boolean z) throws Throwable {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$java$io$ObjectInputStream;
        if (clsClass$ == null) {
            clsClass$ = class$("java.io.ObjectInputStream");
            class$java$io$ObjectInputStream = clsClass$;
        }
        clsArr[0] = clsClass$;
        return getMethod(cls, "readObject", clsArr, z) != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.thoughtworks.xstream.converters.ErrorWritingException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r5v7 */
    public void callReadObject(Class cls, Object obj, ObjectInputStream objectInputStream) throws Throwable {
        Throwable conversionException;
        ?? r5;
        try {
            Class[] clsArr = new Class[1];
            Class clsClass$ = class$java$io$ObjectInputStream;
            if (clsClass$ == null) {
                clsClass$ = class$("java.io.ObjectInputStream");
                class$java$io$ObjectInputStream = clsClass$;
            }
            clsArr[0] = clsClass$;
            getMethod(cls, "readObject", clsArr, false).invoke(obj, objectInputStream);
            r5 = 0;
        } catch (IllegalAccessException e) {
            conversionException = new ObjectAccessException("Cannot access method", e);
            r5 = conversionException;
        } catch (InvocationTargetException e2) {
            conversionException = new ConversionException("Failed calling method", e2.getTargetException());
            r5 = conversionException;
        }
        if (r5 == 0) {
            return;
        }
        r5.add("method", new StringBuffer().append(obj.getClass().getName()).append(".readObject()").toString());
        throw r5;
    }

    public boolean supportsWriteObject(Class cls, boolean z) throws Throwable {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$java$io$ObjectOutputStream;
        if (clsClass$ == null) {
            clsClass$ = class$("java.io.ObjectOutputStream");
            class$java$io$ObjectOutputStream = clsClass$;
        }
        clsArr[0] = clsClass$;
        return getMethod(cls, "writeObject", clsArr, z) != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.thoughtworks.xstream.converters.ErrorWritingException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r5v7 */
    public void callWriteObject(Class cls, Object obj, ObjectOutputStream objectOutputStream) throws Throwable {
        Throwable conversionException;
        ?? r5;
        try {
            Class[] clsArr = new Class[1];
            Class clsClass$ = class$java$io$ObjectOutputStream;
            if (clsClass$ == null) {
                clsClass$ = class$("java.io.ObjectOutputStream");
                class$java$io$ObjectOutputStream = clsClass$;
            }
            clsArr[0] = clsClass$;
            getMethod(cls, "writeObject", clsArr, false).invoke(obj, objectOutputStream);
            r5 = 0;
        } catch (IllegalAccessException e) {
            conversionException = new ObjectAccessException("Cannot access method", e);
            r5 = conversionException;
        } catch (InvocationTargetException e2) {
            conversionException = new ConversionException("Failed calling method", e2.getTargetException());
            r5 = conversionException;
        }
        if (r5 == 0) {
            return;
        }
        r5.add("method", new StringBuffer().append(obj.getClass().getName()).append(".writeObject()").toString());
        throw r5;
    }

    private Method getMethod(Class cls, String str, Class[] clsArr, boolean z) {
        Method method = getMethod(cls, str, clsArr);
        if (method == NO_METHOD || !(z || method.getDeclaringClass().equals(cls))) {
            return null;
        }
        return method;
    }

    private Method getMethod(Class cls, String str, Class[] clsArr) {
        if (cls == null) {
            return null;
        }
        FastField fastField = new FastField(cls, str);
        Method method = (Method) this.declaredCache.get(fastField);
        if (method == null) {
            try {
                method = cls.getDeclaredMethod(str, clsArr);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
            } catch (NoSuchMethodException unused) {
                method = getMethod(cls.getSuperclass(), str, clsArr);
            }
            this.declaredCache.put(fastField, method);
        }
        return method;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0045  */
    private Method getRRMethod(Class cls, String str) {
        Method method;
        FastField fastField = new FastField(cls, str);
        Method method2 = (Method) this.resRepCache.get(fastField);
        if (method2 == null) {
            Method method3 = getMethod(cls, str, EMPTY_CLASSES, true);
            if (method3 == null || method3.getDeclaringClass() == cls) {
                if (method3 == null) {
                    method = NO_METHOD;
                    method2 = method;
                } else {
                    method2 = method3;
                }
            } else if ((method3.getModifiers() & 5) != 0 || ((method3.getModifiers() & 2) <= 0 && cls.getPackage() == method3.getDeclaringClass().getPackage())) {
                method2 = method3;
            } else {
                method = NO_METHOD;
                method2 = method;
            }
            this.resRepCache.put(fastField, method2);
        }
        if (method2 == NO_METHOD) {
            return null;
        }
        return method2;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0066 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:32:0x0068  */
    /* JADX WARN: Code duplicated, block: B:34:0x0074  */
    /* JADX WARN: Code duplicated, block: B:39:0x0096  */
    /* JADX WARN: Code duplicated, block: B:48:? A[RETURN, SYNTHETIC] */
    public Map getSerializablePersistentFields(Class cls) {
        Map map;
        IllegalAccessException e;
        ClassCastException e2;
        ErrorWritingException objectAccessException;
        if (cls == null) {
            return null;
        }
        Map map2 = (Map) this.fieldCache.get(cls.getName());
        if (map2 == null) {
            try {
                Field declaredField = cls.getDeclaredField("serialPersistentFields");
                if ((declaredField.getModifiers() & 26) == 26) {
                    declaredField.setAccessible(true);
                    ObjectStreamField[] objectStreamFieldArr = (ObjectStreamField[]) declaredField.get(null);
                    if (objectStreamFieldArr != null) {
                        map = new HashMap();
                        for (int i = 0; i < objectStreamFieldArr.length; i++) {
                            try {
                                map.put(objectStreamFieldArr[i].getName(), objectStreamFieldArr[i]);
                            } catch (ClassCastException e3) {
                                e2 = e3;
                                objectAccessException = new ConversionException("Incompatible field type", e2);
                                map2 = map;
                                if (objectAccessException != null) {
                                    objectAccessException.add("field", new StringBuffer().append(cls.getName()).append(".serialPersistentFields").toString());
                                    throw objectAccessException;
                                }
                                if (map2 == null) {
                                    map2 = NO_FIELDS;
                                }
                                this.fieldCache.put(cls.getName(), map2);
                                if (map2 == NO_FIELDS) {
                                    return null;
                                }
                                return map2;
                            } catch (IllegalAccessException e4) {
                                e = e4;
                                objectAccessException = new ObjectAccessException("Cannot get field", e);
                                map2 = map;
                                if (objectAccessException != null) {
                                    objectAccessException.add("field", new StringBuffer().append(cls.getName()).append(".serialPersistentFields").toString());
                                    throw objectAccessException;
                                }
                                if (map2 == null) {
                                    map2 = NO_FIELDS;
                                }
                                this.fieldCache.put(cls.getName(), map2);
                                if (map2 == NO_FIELDS) {
                                    return null;
                                }
                                return map2;
                            } catch (NoSuchFieldException unused) {
                            }
                        }
                        map2 = map;
                    }
                }
            } catch (ClassCastException e5) {
                map = map2;
                e2 = e5;
            } catch (IllegalAccessException e6) {
                map = map2;
                e = e6;
            } catch (NoSuchFieldException unused2) {
            }
            objectAccessException = null;
            if (objectAccessException != null) {
                objectAccessException.add("field", new StringBuffer().append(cls.getName()).append(".serialPersistentFields").toString());
                throw objectAccessException;
            }
            if (map2 == null) {
                map2 = NO_FIELDS;
            }
            this.fieldCache.put(cls.getName(), map2);
        }
        if (map2 == NO_FIELDS) {
            return null;
        }
        return map2;
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public void flushCache() {
        Set setKeySet = this.declaredCache.keySet();
        FastField[] fastFieldArr = OBJECT_TYPE_FIELDS;
        setKeySet.retainAll(Arrays.asList(fastFieldArr));
        this.resRepCache.keySet().retainAll(Arrays.asList(fastFieldArr));
    }
}
