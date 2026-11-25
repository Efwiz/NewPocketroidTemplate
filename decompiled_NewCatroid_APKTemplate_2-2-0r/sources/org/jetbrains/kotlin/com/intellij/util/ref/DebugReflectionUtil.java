package org.jetbrains.kotlin.com.intellij.util.ref;

import android.Manifest;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Map;
import java.util.function.Predicate;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public final class DebugReflectionUtil {
    private static final Key<Boolean> REPORTED_LEAKED;
    private static final Method Unsafe_shouldBeInitialized;
    private static final Map<Class<?>, Field[]> allFields = Collections.synchronizedMap(CollectionFactory.createCustomHashingStrategyMap(new HashingStrategy<Class<?>>() { // from class: org.jetbrains.kotlin.com.intellij.util.ref.DebugReflectionUtil.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public boolean equals(Class<?> cls, Class<?> cls2) {
            return cls == cls2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public int hashCode(Class<?> cls) {
            if (cls == null) {
                return 0;
            }
            return cls.getName().hashCode();
        }
    }));
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ref/DebugReflectionUtil";
                break;
            case 2:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
            case 9:
                objArr[0] = "root";
                break;
            case 4:
                objArr[0] = "startRoots";
                break;
            case 5:
                objArr[0] = "lookFor";
                break;
            case 6:
            case 11:
            case 14:
                objArr[0] = "shouldExamineValue";
                break;
            case 7:
                objArr[0] = "leakProcessor";
                break;
            case 8:
            case 13:
                objArr[0] = "queue";
                break;
            case 10:
            case 12:
                objArr[0] = "backLink";
                break;
            default:
                objArr[0] = "aClass";
                break;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ref/DebugReflectionUtil";
        } else {
            objArr[1] = "getAllFields";
        }
        switch (i) {
            case 1:
                break;
            case 2:
                objArr[2] = "isTrivial";
                break;
            case 3:
                objArr[2] = "isInitialized";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "walkObjects";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "queueStronglyReferencedValues";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "queue";
                break;
            default:
                objArr[2] = "getAllFields";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    static {
        Method declaredMethod;
        try {
            declaredMethod = ReflectionUtil.getDeclaredMethod(Class.forName("sun.misc.Unsafe"), "shouldBeInitialized", Class.class);
        } catch (ClassNotFoundException unused) {
            declaredMethod = null;
        }
        Unsafe_shouldBeInitialized = declaredMethod;
        REPORTED_LEAKED = Key.create("REPORTED_LEAKED");
    }

    private static Field[] getAllFields(Class<?> cls) {
        Field[] fieldArr;
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        Field[] fieldArr2 = allFields.get(cls);
        if (fieldArr2 == null) {
            try {
                Field[] declaredFields = cls.getDeclaredFields();
                ArrayList arrayList = new ArrayList(declaredFields.length + 5);
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (!isTrivial(field.getType())) {
                        arrayList.add(field);
                    }
                }
                Class<? super Object> superclass = cls.getSuperclass();
                if (superclass != null) {
                    for (Field field2 : getAllFields(superclass)) {
                        if (!arrayList.contains(field2)) {
                            arrayList.add(field2);
                        }
                    }
                }
                fieldArr = arrayList.isEmpty() ? EMPTY_FIELD_ARRAY : (Field[]) arrayList.toArray(new Field[0]);
            } catch (IncompatibleClassChangeError | NoClassDefFoundError | SecurityException unused) {
                fieldArr = EMPTY_FIELD_ARRAY;
            } catch (RuntimeException e) {
                if (e.getClass().getName().equals("java.lang.reflect.InaccessibleObjectException")) {
                    fieldArr = EMPTY_FIELD_ARRAY;
                } else {
                    throw e;
                }
            }
            fieldArr2 = fieldArr;
            allFields.put(cls, fieldArr2);
        }
        if (fieldArr2 == null) {
            $$$reportNull$$$0(1);
        }
        return fieldArr2;
    }

    private static boolean isTrivial(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        return cls.isPrimitive() || cls == String.class || cls == Class.class || (cls.isArray() && isTrivial(cls.getComponentType()));
    }

    private static boolean isInitialized(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        Method method = Unsafe_shouldBeInitialized;
        if (method == null) {
            return false;
        }
        try {
            return !((Boolean) method.invoke(ReflectionUtil.getUnsafe(), cls)).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <V> boolean walkObjects(int i, Map<Object, String> map, Class<V> cls, Predicate<Object> predicate, PairProcessor<? super V, ? super BackLink<?>> pairProcessor) {
        if (map == null) {
            $$$reportNull$$$0(4);
        }
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        if (predicate == null) {
            $$$reportNull$$$0(6);
        }
        if (pairProcessor == null) {
            $$$reportNull$$$0(7);
        }
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet(100);
        ArrayDeque arrayDeque = new ArrayDeque(100);
        for (Map.Entry<Object, String> entry : map.entrySet()) {
            Object key = entry.getKey();
            final String value = entry.getValue();
            arrayDeque.addLast(new BackLink<Object>(key, null, 0 == true ? 1 : 0) { // from class: org.jetbrains.kotlin.com.intellij.util.ref.DebugReflectionUtil.2
                private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "result", "org/jetbrains/kotlin/com/intellij/util/ref/DebugReflectionUtil$2", "print"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.util.ref.DebugReflectionUtil.BackLink
                void print(StringBuilder sb) {
                    if (sb == null) {
                        $$$reportNull$$$0(0);
                    }
                    super.print(sb);
                    sb.append(" (from ").append(value).append(Constant.AFTER_QUTO);
                }
            });
        }
        while (true) {
            BackLink backLink = (BackLink) arrayDeque.pollFirst();
            if (backLink == null) {
                return true;
            }
            if (backLink.depth <= i) {
                Manifest manifest = (Object) backLink.value;
                if (cls.isAssignableFrom(manifest.getClass()) && markLeaked(manifest) && !pairProcessor.process(manifest, backLink)) {
                    return false;
                }
                if (intOpenHashSet.add(System.identityHashCode(manifest))) {
                    queueStronglyReferencedValues(arrayDeque, manifest, backLink, predicate);
                }
            }
        }
    }

    private static void queueStronglyReferencedValues(Deque<? super BackLink<?>> deque, Object obj, BackLink<?> backLink, Predicate<Object> predicate) {
        if (deque == null) {
            $$$reportNull$$$0(8);
        }
        if (obj == null) {
            $$$reportNull$$$0(9);
        }
        if (backLink == null) {
            $$$reportNull$$$0(10);
        }
        if (predicate == null) {
            $$$reportNull$$$0(11);
        }
        Class<?> cls = obj.getClass();
        for (Field field : getAllFields(cls)) {
            String name2 = field.getName();
            if (!(obj instanceof Reference) || (!"referent".equals(name2) && !"discovered".equals(name2))) {
                try {
                    queue(field.get(obj), field, backLink, deque, predicate);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (cls.isArray()) {
            try {
                for (Object obj2 : (Object[]) obj) {
                    queue(obj2, null, backLink, deque, predicate);
                }
            } catch (ClassCastException unused) {
            }
        }
        if (obj instanceof Class) {
            Class cls2 = (Class) obj;
            if (isInitialized(cls2)) {
                for (Field field2 : getAllFields(cls2)) {
                    if ((field2.getModifiers() & 8) != 0) {
                        try {
                            queue(field2.get(null), field2, backLink, deque, predicate);
                        } catch (IllegalAccessException unused2) {
                        }
                    }
                }
            }
        }
    }

    private static void queue(Object obj, Field field, BackLink<?> backLink, Deque<? super BackLink<?>> deque, Predicate<Object> predicate) {
        if (backLink == null) {
            $$$reportNull$$$0(12);
        }
        if (deque == null) {
            $$$reportNull$$$0(13);
        }
        if (predicate == null) {
            $$$reportNull$$$0(14);
        }
        if (obj == null || isTrivial(obj.getClass()) || !predicate.test(obj)) {
            return;
        }
        deque.addLast(new BackLink(obj, field, backLink));
    }

    private static boolean markLeaked(Object obj) {
        return !(obj instanceof UserDataHolderEx) || ((UserDataHolderEx) obj).replace(REPORTED_LEAKED, null, Boolean.TRUE);
    }

    public static class BackLink<V> {
        private final BackLink<?> backLink;
        private final int depth;
        private final Field field;
        private final V value;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "value";
            } else {
                objArr[0] = "result";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ref/DebugReflectionUtil$BackLink";
            if (i != 1) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "print";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        BackLink(V v, Field field, BackLink<?> backLink) {
            if (v == null) {
                $$$reportNull$$$0(0);
            }
            this.value = v;
            this.field = field;
            this.backLink = backLink;
            this.depth = backLink != null ? backLink.depth + 1 : 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (BackLink backLink = this; backLink != null; backLink = backLink.backLink) {
                backLink.print(sb);
            }
            return sb.toString();
        }

        void print(StringBuilder sb) {
            String strFirst;
            String strValueOf;
            if (sb == null) {
                $$$reportNull$$$0(1);
            }
            V v = this.value;
            try {
                if (v instanceof FList) {
                    strValueOf = "FList (size=" + ((FList) v).size() + Constant.AFTER_QUTO;
                } else {
                    strValueOf = v instanceof Collection ? "Collection (size=" + ((Collection) v).size() + Constant.AFTER_QUTO : String.valueOf(v);
                }
                strFirst = StringUtil.first(StringUtil.convertLineSeparators(strValueOf, "\\n"), 200, true);
            } catch (Throwable th) {
                strFirst = "(" + th.getMessage() + " while computing .toString())";
            }
            Field field = this.field;
            sb.append("via '").append(field == null ? TypeDescription.Generic.OfWildcardType.SYMBOL : field.getDeclaringClass().getName() + "." + field.getName()).append("'; Value: '").append(strFirst).append("' of ").append(v.getClass()).append("\n");
        }
    }
}
