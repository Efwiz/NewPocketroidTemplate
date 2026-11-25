package org.catrobat.catroid.io;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.SerializableConverter;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamUserVariableConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class XStreamUserVariableConverter extends SerializableConverter {
    private static final String TYPE = "type";

    public XStreamUserVariableConverter(Mapper mapper, ReflectionProvider reflectionProvider, ClassLoaderReference classLoaderReference) {
        super(mapper, reflectionProvider, classLoaderReference);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.SerializableConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class type) {
        return type.equals(UserVariable.class);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.SerializableConverter, com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    public void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) throws Throwable {
        writer.addAttribute("type", source.getClass().getSimpleName());
        super.doMarshal(source, writer, context);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.SerializableConverter, com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        if (reader.getAttribute("type") != null) {
            return super.doUnmarshal(result, reader, context);
        }
        return new UserVariable(reader.getValue());
    }
}
