package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ErrorWritingException;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/AbstractCollectionConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractCollectionConverter implements Converter {
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;
    private final Mapper mapper;

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public abstract boolean canConvert(Class cls);

    @Override // com.thoughtworks.xstream.converters.Converter
    public abstract void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext);

    @Override // com.thoughtworks.xstream.converters.Converter
    public abstract Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext);

    public AbstractCollectionConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    protected Mapper mapper() {
        return this.mapper;
    }

    protected void writeItem(Object obj, MarshallingContext marshallingContext, HierarchicalStreamWriter hierarchicalStreamWriter) throws Throwable {
        if (obj == null) {
            writeNullItem(marshallingContext, hierarchicalStreamWriter);
            return;
        }
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, mapper().serializedClass(obj.getClass()), obj.getClass());
        writeBareItem(obj, marshallingContext, hierarchicalStreamWriter);
        hierarchicalStreamWriter.endNode();
    }

    protected void writeCompleteItem(Object obj, MarshallingContext marshallingContext, HierarchicalStreamWriter hierarchicalStreamWriter) throws Throwable {
        writeItem(obj, marshallingContext, hierarchicalStreamWriter);
    }

    protected void writeBareItem(Object obj, MarshallingContext marshallingContext, HierarchicalStreamWriter hierarchicalStreamWriter) {
        marshallingContext.convertAnother(obj);
    }

    protected void writeNullItem(MarshallingContext marshallingContext, HierarchicalStreamWriter hierarchicalStreamWriter) throws Throwable {
        String strSerializedClass = mapper().serializedClass(null);
        Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
        if (clsClass$ == null) {
            clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
            class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
        }
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, strSerializedClass, clsClass$);
        hierarchicalStreamWriter.endNode();
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    protected Object readItem(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Object obj) {
        return readBareItem(hierarchicalStreamReader, unmarshallingContext, obj);
    }

    protected Object readBareItem(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Object obj) {
        return unmarshallingContext.convertAnother(obj, HierarchicalStreams.readClassType(hierarchicalStreamReader, mapper()));
    }

    protected Object readCompleteItem(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext, Object obj) {
        hierarchicalStreamReader.moveDown();
        Object item = readItem(hierarchicalStreamReader, unmarshallingContext, obj);
        hierarchicalStreamReader.moveUp();
        return item;
    }

    protected Object createCollection(Class cls) {
        ErrorWritingException conversionException;
        Class clsDefaultImplementationOf = mapper().defaultImplementationOf(cls);
        try {
            return clsDefaultImplementationOf.newInstance();
        } catch (IllegalAccessException e) {
            conversionException = new ObjectAccessException("Cannot instantiate default collection", e);
            conversionException.add("collection-type", cls.getName());
            conversionException.add("default-type", clsDefaultImplementationOf.getName());
            throw conversionException;
        } catch (InstantiationException e2) {
            conversionException = new ConversionException("Cannot instantiate default collection", e2);
            conversionException.add("collection-type", cls.getName());
            conversionException.add("default-type", clsDefaultImplementationOf.getName());
            throw conversionException;
        }
    }
}
