package com.thoughtworks.xstream.converters.enums;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/enums/EnumConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class EnumConverter implements Converter {
    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return (cls != null && cls.isEnum()) || Enum.class.isAssignableFrom(cls);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        hierarchicalStreamWriter.setValue(((Enum) obj).name());
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Class requiredType = unmarshallingContext.getRequiredType();
        if (requiredType.getSuperclass() != Enum.class) {
            requiredType = requiredType.getSuperclass();
        }
        String value = hierarchicalStreamReader.getValue();
        try {
            return Enum.valueOf(requiredType, value);
        } catch (IllegalArgumentException e) {
            for (Enum r3 : (Enum[]) requiredType.getEnumConstants()) {
                if (r3.name().equalsIgnoreCase(value)) {
                    return r3;
                }
            }
            throw e;
        }
    }
}
