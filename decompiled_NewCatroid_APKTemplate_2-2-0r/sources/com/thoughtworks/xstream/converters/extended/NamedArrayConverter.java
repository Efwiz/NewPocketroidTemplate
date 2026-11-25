package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.core.util.Primitives;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/NamedArrayConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class NamedArrayConverter implements Converter {
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;
    private final Class arrayType;
    private final String itemName;
    private final Mapper mapper;

    public NamedArrayConverter(Class cls, Mapper mapper, String str) {
        if (!cls.isArray()) {
            throw new IllegalArgumentException(new StringBuffer().append(cls.getName()).append(" is not an array").toString());
        }
        this.arrayType = cls;
        this.mapper = mapper;
        this.itemName = str;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == this.arrayType;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) throws Throwable {
        Class<?> clsUnbox;
        String strAliasForSystemAttribute;
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 == null) {
                clsUnbox = class$com$thoughtworks$xstream$mapper$Mapper$Null;
                if (clsUnbox == null) {
                    clsUnbox = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                    class$com$thoughtworks$xstream$mapper$Mapper$Null = clsUnbox;
                }
            } else if (this.arrayType.getComponentType().isPrimitive()) {
                clsUnbox = Primitives.unbox(obj2.getClass());
            } else {
                clsUnbox = obj2.getClass();
            }
            ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, this.itemName, clsUnbox);
            if (!clsUnbox.equals(this.arrayType.getComponentType()) && (strAliasForSystemAttribute = this.mapper.aliasForSystemAttribute("class")) != null) {
                hierarchicalStreamWriter.addAttribute(strAliasForSystemAttribute, this.mapper.serializedClass(clsUnbox));
            }
            if (obj2 != null) {
                marshallingContext.convertAnother(obj2);
            }
            hierarchicalStreamWriter.endNode();
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) throws Throwable {
        ArrayList arrayList = new ArrayList();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            String classAttribute = HierarchicalStreams.readClassAttribute(hierarchicalStreamReader, this.mapper);
            Class<?> componentType = classAttribute == null ? this.arrayType.getComponentType() : this.mapper.realClass(classAttribute);
            Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
            }
            Object objConvertAnother = null;
            if (!clsClass$.equals(componentType)) {
                objConvertAnother = unmarshallingContext.convertAnother(null, componentType);
            }
            arrayList.add(objConvertAnother);
            hierarchicalStreamReader.moveUp();
        }
        Object objNewInstance = Array.newInstance(this.arrayType.getComponentType(), arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(objNewInstance, i, arrayList.get(i));
        }
        return objNewInstance;
    }
}
