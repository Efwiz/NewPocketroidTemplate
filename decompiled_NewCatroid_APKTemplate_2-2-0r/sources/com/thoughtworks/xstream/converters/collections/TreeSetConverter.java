package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.Fields;
import com.thoughtworks.xstream.core.util.PresortedSet;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/TreeSetConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class TreeSetConverter extends CollectionConverter {
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$util$SortedMap;
    static /* synthetic */ Class class$java$util$TreeSet;
    private static final Object constantValue;
    private static final Field sortedMapField;
    private transient TreeMapConverter treeMapConverter;

    static {
        Field fieldLocate;
        Map map;
        Object[] array;
        Object obj;
        Object obj2 = null;
        if (JVM.hasOptimizedTreeSetAddAll()) {
            Class clsClass$ = class$java$util$TreeSet;
            if (clsClass$ == null) {
                clsClass$ = class$("java.util.TreeSet");
                class$java$util$TreeSet = clsClass$;
            }
            Class clsClass$2 = class$java$util$SortedMap;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.util.SortedMap");
                class$java$util$SortedMap = clsClass$2;
            }
            fieldLocate = Fields.locate(clsClass$, clsClass$2, false);
        } else {
            fieldLocate = null;
        }
        sortedMapField = fieldLocate;
        if (fieldLocate != null) {
            TreeSet treeSet = new TreeSet();
            treeSet.add(SchemaSymbols.ATTVAL_TRUE_1);
            treeSet.add("2");
            try {
                map = (Map) fieldLocate.get(treeSet);
            } catch (IllegalAccessException unused) {
                map = null;
            }
            if (map != null && (obj = (array = map.values().toArray())[0]) == array[1]) {
                obj2 = obj;
            }
        } else {
            Class clsClass$3 = class$java$util$TreeSet;
            if (clsClass$3 == null) {
                clsClass$3 = class$("java.util.TreeSet");
                class$java$util$TreeSet = clsClass$3;
            }
            Class clsClass$4 = class$java$lang$Object;
            if (clsClass$4 == null) {
                clsClass$4 = class$(CommonClassNames.JAVA_LANG_OBJECT);
                class$java$lang$Object = clsClass$4;
            }
            Field fieldLocate2 = Fields.locate(clsClass$3, clsClass$4, true);
            if (fieldLocate2 != null) {
                try {
                    obj2 = fieldLocate2.get(null);
                } catch (IllegalAccessException unused2) {
                }
            }
        }
        constantValue = obj2;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TreeSetConverter(Mapper mapper) throws Throwable {
        Class clsClass$ = class$java$util$TreeSet;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.TreeSet");
            class$java$util$TreeSet = clsClass$;
        }
        super(mapper, clsClass$);
        readResolve();
    }

    @Override // com.thoughtworks.xstream.converters.collections.CollectionConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        this.treeMapConverter.marshalComparator(((SortedSet) obj).comparator(), hierarchicalStreamWriter, marshallingContext);
        super.marshal(obj, hierarchicalStreamWriter, marshallingContext);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.thoughtworks.xstream.converters.collections.CollectionConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        TreeSet treeSet;
        TreeMap treeMap;
        TreeMap treeMap2 = null;
        TreeSet treeSet2 = null;
        Comparator comparatorUnmarshalComparator = this.treeMapConverter.unmarshalComparator(hierarchicalStreamReader, unmarshallingContext, null);
        boolean z = comparatorUnmarshalComparator instanceof Mapper.Null;
        Comparator comparator = z ? null : comparatorUnmarshalComparator;
        Field field = sortedMapField;
        if (field != null) {
            TreeSet treeSet3 = comparator == null ? new TreeSet() : new TreeSet(comparator);
            try {
                Object obj = field.get(treeSet3);
                if (obj instanceof TreeMap) {
                    treeMap = (TreeMap) obj;
                    treeSet2 = treeSet3;
                } else {
                    treeMap = null;
                }
                TreeMap treeMap3 = treeMap;
                treeSet = treeSet2;
                treeMap2 = treeMap3;
            } catch (IllegalAccessException e) {
                throw new ObjectAccessException("Cannot get backing map of TreeSet", e);
            }
        } else {
            treeSet = null;
        }
        if (treeMap2 == null) {
            PresortedSet presortedSet = new PresortedSet(comparator);
            treeSet = comparator == null ? new TreeSet() : new TreeSet(comparator);
            if (z) {
                addCurrentElementToCollection(hierarchicalStreamReader, unmarshallingContext, treeSet, presortedSet);
                hierarchicalStreamReader.moveUp();
            }
            populateCollection(hierarchicalStreamReader, unmarshallingContext, treeSet, presortedSet);
            if (presortedSet.size() > 0) {
                treeSet.addAll(presortedSet);
            }
        } else {
            this.treeMapConverter.populateTreeMap(hierarchicalStreamReader, unmarshallingContext, treeMap2, comparatorUnmarshalComparator);
        }
        return treeSet;
    }

    private Object readResolve() {
        this.treeMapConverter = new TreeMapConverter(mapper()) { // from class: com.thoughtworks.xstream.converters.collections.TreeSetConverter.1
            @Override // com.thoughtworks.xstream.converters.collections.MapConverter
            protected void populateMap(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Map map, final Map map2) {
                TreeSetConverter.this.populateCollection(hierarchicalStreamReader, unmarshallingContext, new AbstractList() { // from class: com.thoughtworks.xstream.converters.collections.TreeSetConverter.1.1
                    @Override // java.util.AbstractList, java.util.List
                    public Object get(int i) {
                        return null;
                    }

                    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
                    public boolean add(Object obj) {
                        return map2.put(obj, TreeSetConverter.constantValue != null ? TreeSetConverter.constantValue : obj) != null;
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return map2.size();
                    }
                });
            }

            @Override // com.thoughtworks.xstream.converters.collections.MapConverter
            protected void putCurrentEntryIntoMap(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Map map, Map map2) {
                Object item = readItem(hierarchicalStreamReader, unmarshallingContext, map);
                map2.put(item, item);
            }
        };
        return this;
    }
}
