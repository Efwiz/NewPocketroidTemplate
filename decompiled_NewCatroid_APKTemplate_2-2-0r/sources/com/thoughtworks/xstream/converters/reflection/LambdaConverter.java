package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.Types;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.io.Serializable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/LambdaConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class LambdaConverter extends SerializableConverter {
    public LambdaConverter(Mapper mapper, ReflectionProvider reflectionProvider, ClassLoaderReference classLoaderReference) {
        super(mapper, reflectionProvider, classLoaderReference);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.SerializableConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return Types.isLambdaType(cls) && (JVM.canCreateDerivedObjectOutputStream() || !Serializable.class.isAssignableFrom(cls));
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        if (obj instanceof Serializable) {
            super.marshal(obj, hierarchicalStreamWriter, marshallingContext);
        }
    }
}
