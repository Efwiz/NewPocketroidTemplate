package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/CollectionConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CollectionConverter extends AbstractCollectionConverter {
    static /* synthetic */ Class class$java$util$ArrayList;
    static /* synthetic */ Class class$java$util$Collection;
    static /* synthetic */ Class class$java$util$HashSet;
    static /* synthetic */ Class class$java$util$LinkedHashSet;
    static /* synthetic */ Class class$java$util$LinkedList;
    static /* synthetic */ Class class$java$util$Vector;
    private final Class type;

    public CollectionConverter(Mapper mapper) {
        this(mapper, null);
    }

    public CollectionConverter(Mapper mapper, Class cls) throws Throwable {
        super(mapper);
        this.type = cls;
        if (cls != null) {
            Class clsClass$ = class$java$util$Collection;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_UTIL_COLLECTION);
                class$java$util$Collection = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                return;
            }
            StringBuffer stringBufferAppend = new StringBuffer().append(cls).append(" not of type ");
            Class clsClass$2 = class$java$util$Collection;
            if (clsClass$2 == null) {
                clsClass$2 = class$(CommonClassNames.JAVA_UTIL_COLLECTION);
                class$java$util$Collection = clsClass$2;
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
        Class clsClass$ = class$java$util$ArrayList;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_UTIL_ARRAY_LIST);
            class$java$util$ArrayList = clsClass$;
        }
        if (!cls.equals(clsClass$)) {
            Class clsClass$2 = class$java$util$HashSet;
            if (clsClass$2 == null) {
                clsClass$2 = class$(CommonClassNames.JAVA_UTIL_HASH_SET);
                class$java$util$HashSet = clsClass$2;
            }
            if (!cls.equals(clsClass$2)) {
                Class clsClass$3 = class$java$util$LinkedList;
                if (clsClass$3 == null) {
                    clsClass$3 = class$(CommonClassNames.JAVA_UTIL_LINKED_LIST);
                    class$java$util$LinkedList = clsClass$3;
                }
                if (!cls.equals(clsClass$3)) {
                    Class clsClass$4 = class$java$util$Vector;
                    if (clsClass$4 == null) {
                        clsClass$4 = class$("java.util.Vector");
                        class$java$util$Vector = clsClass$4;
                    }
                    if (!cls.equals(clsClass$4)) {
                        Class clsClass$5 = class$java$util$LinkedHashSet;
                        if (clsClass$5 == null) {
                            clsClass$5 = class$(CommonClassNames.JAVA_UTIL_LINKED_HASH_SET);
                            class$java$util$LinkedHashSet = clsClass$5;
                        }
                        if (!cls.equals(clsClass$5)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Iterator it = ((Collection) obj).iterator();
        while (it.hasNext()) {
            writeCompleteItem(it.next(), marshallingContext, hierarchicalStreamWriter);
        }
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Collection collection = (Collection) createCollection(unmarshallingContext.getRequiredType());
        populateCollection(hierarchicalStreamReader, unmarshallingContext, collection);
        return collection;
    }

    protected void populateCollection(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Collection collection) {
        populateCollection(hierarchicalStreamReader, unmarshallingContext, collection, collection);
    }

    protected void populateCollection(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Collection collection, Collection collection2) {
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            addCurrentElementToCollection(hierarchicalStreamReader, unmarshallingContext, collection, collection2);
            hierarchicalStreamReader.moveUp();
        }
    }

    protected void addCurrentElementToCollection(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Collection collection, Collection collection2) {
        collection2.add(readItem(hierarchicalStreamReader, unmarshallingContext, collection));
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
