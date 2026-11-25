package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/ArrayConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ArrayConverter extends AbstractCollectionConverter {
    public ArrayConverter(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls != null && cls.isArray();
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            writeCompleteItem(Array.get(obj, i), marshallingContext, hierarchicalStreamWriter);
        }
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        ArrayList arrayList = new ArrayList();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            arrayList.add(readCompleteItem(hierarchicalStreamReader, unmarshallingContext, null));
        }
        Object objNewInstance = Array.newInstance(unmarshallingContext.getRequiredType().getComponentType(), arrayList.size());
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Array.set(objNewInstance, i, it.next());
            i++;
        }
        return objNewInstance;
    }
}
