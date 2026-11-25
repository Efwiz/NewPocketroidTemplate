package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Collections;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/SingletonMapConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SingletonMapConverter extends MapConverter {
    private static final Class MAP = Collections.singletonMap(Boolean.TRUE, null).getClass();

    public SingletonMapConverter(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.converters.collections.MapConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return MAP == cls;
    }

    @Override // com.thoughtworks.xstream.converters.collections.MapConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        hierarchicalStreamReader.moveDown();
        Object completeItem = readCompleteItem(hierarchicalStreamReader, unmarshallingContext, null);
        Object completeItem2 = readCompleteItem(hierarchicalStreamReader, unmarshallingContext, null);
        hierarchicalStreamReader.moveUp();
        return Collections.singletonMap(completeItem, completeItem2);
    }
}
