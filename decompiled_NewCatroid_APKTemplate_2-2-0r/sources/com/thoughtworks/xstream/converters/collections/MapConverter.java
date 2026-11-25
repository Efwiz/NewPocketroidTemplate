package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/MapConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class MapConverter extends AbstractCollectionConverter {
    static /* synthetic */ Class class$java$util$HashMap;
    static /* synthetic */ Class class$java$util$Hashtable;
    static /* synthetic */ Class class$java$util$Map;
    static /* synthetic */ Class class$java$util$Map$Entry;
    private final Class type;

    public MapConverter(Mapper mapper) {
        this(mapper, null);
    }

    public MapConverter(Mapper mapper, Class cls) throws Throwable {
        super(mapper);
        this.type = cls;
        if (cls != null) {
            Class clsClass$ = class$java$util$Map;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_UTIL_MAP);
                class$java$util$Map = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                return;
            }
            StringBuffer stringBufferAppend = new StringBuffer().append(cls).append(" not of type ");
            Class clsClass$2 = class$java$util$Map;
            if (clsClass$2 == null) {
                clsClass$2 = class$(CommonClassNames.JAVA_UTIL_MAP);
                class$java$util$Map = clsClass$2;
            }
            throw new IllegalArgumentException(stringBufferAppend.append(clsClass$2).toString());
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Object obj = this.type;
        if (obj != null) {
            return cls.equals(obj);
        }
        Class clsClass$ = class$java$util$HashMap;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_UTIL_HASH_MAP);
            class$java$util$HashMap = clsClass$;
        }
        if (!cls.equals(clsClass$)) {
            Class clsClass$2 = class$java$util$Hashtable;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.util.Hashtable");
                class$java$util$Hashtable = clsClass$2;
            }
            if (!cls.equals(clsClass$2) && !cls.getName().equals(CommonClassNames.JAVA_UTIL_LINKED_HASH_MAP) && !cls.getName().equals(CommonClassNames.JAVA_UTIL_CONCURRENT_HASH_MAP) && !cls.getName().equals("sun.font.AttributeMap")) {
                return false;
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) throws Throwable {
        Map map = (Map) obj;
        Mapper mapper = mapper();
        Class clsClass$ = class$java$util$Map$Entry;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.Map$Entry");
            class$java$util$Map$Entry = clsClass$;
        }
        String strSerializedClass = mapper.serializedClass(clsClass$);
        for (Map.Entry entry : map.entrySet()) {
            ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, strSerializedClass, entry.getClass());
            writeCompleteItem(entry.getKey(), marshallingContext, hierarchicalStreamWriter);
            writeCompleteItem(entry.getValue(), marshallingContext, hierarchicalStreamWriter);
            hierarchicalStreamWriter.endNode();
        }
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Map map = (Map) createCollection(unmarshallingContext.getRequiredType());
        populateMap(hierarchicalStreamReader, unmarshallingContext, map);
        return map;
    }

    protected void populateMap(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Map map) {
        populateMap(hierarchicalStreamReader, unmarshallingContext, map, map);
    }

    protected void populateMap(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Map map, Map map2) {
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            putCurrentEntryIntoMap(hierarchicalStreamReader, unmarshallingContext, map, map2);
            hierarchicalStreamReader.moveUp();
        }
    }

    protected void putCurrentEntryIntoMap(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Map map, Map map2) {
        map2.put(readCompleteItem(hierarchicalStreamReader, unmarshallingContext, map), readCompleteItem(hierarchicalStreamReader, unmarshallingContext, map));
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter
    protected Object createCollection(Class cls) {
        Class cls2 = this.type;
        if (cls2 != null) {
            cls = cls2;
        }
        return super.createCollection(cls);
    }
}
