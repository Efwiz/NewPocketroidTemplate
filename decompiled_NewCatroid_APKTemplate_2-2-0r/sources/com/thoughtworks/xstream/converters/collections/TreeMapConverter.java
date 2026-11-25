package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.Fields;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.core.util.PresortedMap;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/TreeMapConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class TreeMapConverter extends MapConverter {
    private static final Comparator NULL_MARKER = new NullComparator();
    static /* synthetic */ Class class$java$util$Comparator;
    static /* synthetic */ Class class$java$util$TreeMap;
    private static final Field comparatorField;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/TreeMapConverter$NullComparator.class
      classes11.dex
     */
    private static final class NullComparator extends Mapper.Null implements Comparator {
        private NullComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    static {
        Class clsClass$ = class$java$util$TreeMap;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.TreeMap");
            class$java$util$TreeMap = clsClass$;
        }
        Class clsClass$2 = class$java$util$Comparator;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_UTIL_COMPARATOR);
            class$java$util$Comparator = clsClass$2;
        }
        comparatorField = Fields.locate(clsClass$, clsClass$2, false);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TreeMapConverter(Mapper mapper) throws Throwable {
        Class clsClass$ = class$java$util$TreeMap;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.TreeMap");
            class$java$util$TreeMap = clsClass$;
        }
        super(mapper, clsClass$);
    }

    @Override // com.thoughtworks.xstream.converters.collections.MapConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) throws Throwable {
        marshalComparator(((SortedMap) obj).comparator(), hierarchicalStreamWriter, marshallingContext);
        super.marshal(obj, hierarchicalStreamWriter, marshallingContext);
    }

    protected void marshalComparator(Comparator comparator, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        if (comparator != null) {
            hierarchicalStreamWriter.startNode("comparator");
            hierarchicalStreamWriter.addAttribute(mapper().aliasForSystemAttribute("class"), mapper().serializedClass(comparator.getClass()));
            marshallingContext.convertAnother(comparator);
            hierarchicalStreamWriter.endNode();
        }
    }

    @Override // com.thoughtworks.xstream.converters.collections.MapConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        TreeMap treeMap = comparatorField != null ? new TreeMap() : null;
        Comparator comparatorUnmarshalComparator = unmarshalComparator(hierarchicalStreamReader, unmarshallingContext, treeMap);
        if (treeMap == null) {
            treeMap = (comparatorUnmarshalComparator == null || comparatorUnmarshalComparator == NULL_MARKER) ? new TreeMap() : new TreeMap(comparatorUnmarshalComparator);
        }
        populateTreeMap(hierarchicalStreamReader, unmarshallingContext, treeMap, comparatorUnmarshalComparator);
        return treeMap;
    }

    protected Comparator unmarshalComparator(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, TreeMap treeMap) {
        Comparator comparator = null;
        if (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            if (hierarchicalStreamReader.getNodeName().equals("comparator")) {
                comparator = (Comparator) unmarshallingContext.convertAnother(treeMap, HierarchicalStreams.readClassType(hierarchicalStreamReader, mapper()));
            } else if (!hierarchicalStreamReader.getNodeName().equals("no-comparator")) {
                return NULL_MARKER;
            }
            hierarchicalStreamReader.moveUp();
        }
        return comparator;
    }

    protected void populateTreeMap(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, TreeMap treeMap, Comparator comparator) {
        Field field;
        boolean z = comparator == NULL_MARKER;
        Comparator comparator2 = null;
        if (z) {
            comparator = null;
        }
        if (comparator != null && JVM.hasOptimizedTreeMapPutAll()) {
            comparator2 = comparator;
        }
        PresortedMap presortedMap = new PresortedMap(comparator2);
        if (z) {
            putCurrentEntryIntoMap(hierarchicalStreamReader, unmarshallingContext, treeMap, presortedMap);
            hierarchicalStreamReader.moveUp();
        }
        populateMap(hierarchicalStreamReader, unmarshallingContext, treeMap, presortedMap);
        try {
            if (JVM.hasOptimizedTreeMapPutAll()) {
                if (comparator != null && (field = comparatorField) != null) {
                    field.set(treeMap, comparator);
                }
                treeMap.putAll(presortedMap);
                return;
            }
            Field field2 = comparatorField;
            if (field2 != null) {
                field2.set(treeMap, presortedMap.comparator());
                treeMap.putAll(presortedMap);
                field2.set(treeMap, comparator);
                return;
            }
            treeMap.putAll(presortedMap);
        } catch (IllegalAccessException e) {
            throw new ObjectAccessException("Cannot set comparator of TreeMap", e);
        }
    }
}
