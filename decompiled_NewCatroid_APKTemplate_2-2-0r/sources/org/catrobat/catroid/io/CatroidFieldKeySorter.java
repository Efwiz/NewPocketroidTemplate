package org.catrobat.catroid.io;

import android.util.Log;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.reflection.FieldKey;
import com.thoughtworks.xstream.converters.reflection.FieldKeySorter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/CatroidFieldKeySorter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CatroidFieldKeySorter implements FieldKeySorter {
    private static final String TAG = "CatroidFieldKeySorter";

    @Override // com.thoughtworks.xstream.converters.reflection.FieldKeySorter
    public Map sort(final Class type, final Map keyedByFieldKey) {
        XStreamFieldKeyOrder xStreamFieldKeyOrder = (XStreamFieldKeyOrder) findAnnotationInClassHierarchy(type, XStreamFieldKeyOrder.class);
        if (xStreamFieldKeyOrder != null) {
            return sortByList(Arrays.asList(xStreamFieldKeyOrder.value()), keyedByFieldKey);
        }
        return sortAlphabeticallyByClassHierarchy(keyedByFieldKey);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <E extends Annotation> E findAnnotationInClassHierarchy(Class<?> cls, Class<? extends E> cls2) {
        while (cls != Object.class) {
            E e = (E) cls.getAnnotation(cls2);
            if (e != null) {
                return e;
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    private Map sortByList(final List<String> fieldOrder, final Map keyedByFieldKey) {
        checkMissingSerializableField(fieldOrder, keyedByFieldKey.entrySet());
        TreeMap treeMap = new TreeMap(new Comparator() { // from class: org.catrobat.catroid.io.CatroidFieldKeySorter.1
            @Override // java.util.Comparator
            public int compare(final Object objectOne, final Object objectTwo) {
                return fieldOrder.indexOf(CatroidFieldKeySorter.getAliasOrFieldName((FieldKey) objectOne)) - fieldOrder.indexOf(CatroidFieldKeySorter.getAliasOrFieldName((FieldKey) objectTwo));
            }
        });
        treeMap.putAll(keyedByFieldKey);
        return treeMap;
    }

    private void checkMissingSerializableField(List<String> fieldOrder, Set<Map.Entry<FieldKey, Field>> fields) {
        for (Map.Entry<FieldKey, Field> entry : fields) {
            FieldKey key = entry.getKey();
            String aliasOrFieldName = getAliasOrFieldName(key);
            if (!fieldOrder.contains(aliasOrFieldName) && isSerializable(entry.getValue())) {
                throw new XStreamMissingSerializableFieldException("Missing field '" + aliasOrFieldName + "' in XStreamFieldKeyOrder annotation for class " + key.getDeclaringClass());
            }
        }
    }

    private boolean isSerializable(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) ? false : true;
    }

    private Map sortAlphabeticallyByClassHierarchy(final Map keyedByFieldKey) {
        TreeMap treeMap = new TreeMap(new Comparator() { // from class: org.catrobat.catroid.io.CatroidFieldKeySorter.2
            @Override // java.util.Comparator
            public int compare(final Object objectOne, final Object objectTwo) {
                FieldKey fieldKey = (FieldKey) objectOne;
                FieldKey fieldKey2 = (FieldKey) objectTwo;
                int depth = fieldKey.getDepth() - fieldKey2.getDepth();
                return depth == 0 ? CatroidFieldKeySorter.getAliasOrFieldName(fieldKey).compareTo(CatroidFieldKeySorter.getAliasOrFieldName(fieldKey2)) : depth;
            }
        });
        treeMap.putAll(keyedByFieldKey);
        return treeMap;
    }

    public static String getAliasOrFieldName(FieldKey fieldKey) {
        String fieldName = fieldKey.getFieldName();
        try {
            XStreamAlias xStreamAlias = (XStreamAlias) fieldKey.getDeclaringClass().getDeclaredField(fieldName).getAnnotation(XStreamAlias.class);
            return xStreamAlias != null ? xStreamAlias.value() : fieldName;
        } catch (NoSuchFieldException | SecurityException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return fieldName;
        }
    }
}
