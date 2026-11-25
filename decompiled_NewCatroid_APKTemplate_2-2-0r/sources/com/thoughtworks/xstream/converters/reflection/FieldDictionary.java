package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.core.Caching;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.OrderRetainingMap;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/FieldDictionary.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class FieldDictionary implements Caching {
    private static final DictionaryEntry OBJECT_DICTIONARY_ENTRY = new DictionaryEntry(Collections.EMPTY_MAP, Collections.EMPTY_MAP);
    static /* synthetic */ Class class$java$lang$Object;
    private transient Map dictionaryEntries;
    private transient FieldUtil fieldUtil;
    private final FieldKeySorter sorter;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/FieldDictionary$FieldUtil.class
      classes11.dex
     */
    interface FieldUtil {
        boolean isSynthetic(Field field);
    }

    public FieldDictionary() {
        this(new ImmutableFieldKeySorter());
    }

    public FieldDictionary(FieldKeySorter fieldKeySorter) {
        this.sorter = fieldKeySorter;
        init();
    }

    private void init() {
        this.dictionaryEntries = new HashMap();
        if (JVM.is15()) {
            try {
                this.fieldUtil = (FieldUtil) JVM.loadClassForName("com.thoughtworks.xstream.converters.reflection.FieldUtil15", true).newInstance();
            } catch (Exception unused) {
            }
        }
        if (this.fieldUtil == null) {
            this.fieldUtil = new FieldUtil14();
        }
    }

    public Iterator serializableFieldsFor(Class cls) {
        return fieldsFor(cls);
    }

    public Iterator fieldsFor(Class cls) {
        return buildMap(cls, true).values().iterator();
    }

    public Field field(Class cls, String str, Class cls2) throws Throwable {
        Field fieldFieldOrNull = fieldOrNull(cls, str, cls2);
        if (fieldFieldOrNull != null) {
            return fieldFieldOrNull;
        }
        throw new MissingFieldException(cls.getName(), str);
    }

    public Field fieldOrNull(Class cls, String str, Class cls2) throws Throwable {
        Map mapBuildMap = buildMap(cls, cls2 != null);
        Object fieldKey = str;
        if (cls2 != null) {
            fieldKey = new FieldKey(str, cls2, -1);
        }
        return (Field) mapBuildMap.get(fieldKey);
    }

    private Map buildMap(Class cls, boolean z) throws Throwable {
        LinkedList<Class> linkedList = new LinkedList();
        DictionaryEntry dictionaryEntryBuildDictionaryEntryForClass = null;
        while (dictionaryEntryBuildDictionaryEntryForClass == null) {
            Class clsClass$ = class$java$lang$Object;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
                class$java$lang$Object = clsClass$;
            }
            if (clsClass$.equals(cls) || cls == null) {
                dictionaryEntryBuildDictionaryEntryForClass = OBJECT_DICTIONARY_ENTRY;
            } else {
                dictionaryEntryBuildDictionaryEntryForClass = getDictionaryEntry(cls);
            }
            if (dictionaryEntryBuildDictionaryEntryForClass == null) {
                linkedList.addFirst(cls);
                cls = cls.getSuperclass();
            }
        }
        for (Class cls2 : linkedList) {
            dictionaryEntryBuildDictionaryEntryForClass = buildDictionaryEntryForClass(cls2, dictionaryEntryBuildDictionaryEntryForClass);
            synchronized (this) {
                DictionaryEntry dictionaryEntry = getDictionaryEntry(cls2);
                if (dictionaryEntry == null) {
                    this.dictionaryEntries.put(cls2, dictionaryEntryBuildDictionaryEntryForClass);
                } else {
                    dictionaryEntryBuildDictionaryEntryForClass = dictionaryEntry;
                }
            }
        }
        return z ? dictionaryEntryBuildDictionaryEntryForClass.getKeyedByFieldKey() : dictionaryEntryBuildDictionaryEntryForClass.getKeyedByFieldName();
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private DictionaryEntry buildDictionaryEntryForClass(Class cls, DictionaryEntry dictionaryEntry) {
        HashMap map = new HashMap(dictionaryEntry.getKeyedByFieldName());
        OrderRetainingMap orderRetainingMap = new OrderRetainingMap(dictionaryEntry.getKeyedByFieldKey());
        Field[] declaredFields = cls.getDeclaredFields();
        if (JVM.reverseFieldDefinition()) {
            int length = declaredFields.length >> 1;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                int length2 = (declaredFields.length - i) - 1;
                Field field = declaredFields[i];
                declaredFields[i] = declaredFields[length2];
                declaredFields[length2] = field;
                length = i;
            }
        }
        for (int i2 = 0; i2 < declaredFields.length; i2++) {
            Field field2 = declaredFields[i2];
            if (!this.fieldUtil.isSynthetic(field2) || !field2.getName().startsWith("$jacoco")) {
                if (!field2.isAccessible()) {
                    field2.setAccessible(true);
                }
                FieldKey fieldKey = new FieldKey(field2.getName(), field2.getDeclaringClass(), i2);
                Field field3 = (Field) map.get(field2.getName());
                if (field3 == null || (field3.getModifiers() & 8) != 0 || (field3 != null && (field2.getModifiers() & 8) == 0)) {
                    map.put(field2.getName(), field2);
                }
                orderRetainingMap.put(fieldKey, field2);
            }
        }
        return new DictionaryEntry(map, this.sorter.sort(cls, orderRetainingMap));
    }

    private synchronized DictionaryEntry getDictionaryEntry(Class cls) {
        return (DictionaryEntry) this.dictionaryEntries.get(cls);
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public synchronized void flushCache() {
        this.dictionaryEntries.clear();
        FieldKeySorter fieldKeySorter = this.sorter;
        if (fieldKeySorter instanceof Caching) {
            ((Caching) fieldKeySorter).flushCache();
        }
    }

    protected Object readResolve() {
        init();
        return this;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/FieldDictionary$DictionaryEntry.class
      classes11.dex
     */
    private static final class DictionaryEntry {
        private final Map keyedByFieldKey;
        private final Map keyedByFieldName;

        public DictionaryEntry(Map map, Map map2) {
            this.keyedByFieldName = map;
            this.keyedByFieldKey = map2;
        }

        public Map getKeyedByFieldName() {
            return this.keyedByFieldName;
        }

        public Map getKeyedByFieldKey() {
            return this.keyedByFieldKey;
        }
    }
}
