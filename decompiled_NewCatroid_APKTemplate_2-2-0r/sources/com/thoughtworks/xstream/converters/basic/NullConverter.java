package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/NullConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class NullConverter implements Converter {
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != null) {
            Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
            }
            if (!clsClass$.isAssignableFrom(cls)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) throws Throwable {
        Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
        if (clsClass$ == null) {
            clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
            class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
        }
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, "null", clsClass$);
        hierarchicalStreamWriter.endNode();
    }
}
