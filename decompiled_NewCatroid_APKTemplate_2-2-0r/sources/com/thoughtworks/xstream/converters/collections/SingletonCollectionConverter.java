package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Collections;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/SingletonCollectionConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SingletonCollectionConverter extends CollectionConverter {
    private static final Class LIST = Collections.singletonList(Boolean.TRUE).getClass();
    private static final Class SET = Collections.singleton(Boolean.TRUE).getClass();

    public SingletonCollectionConverter(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.converters.collections.CollectionConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return LIST == cls || SET == cls;
    }

    @Override // com.thoughtworks.xstream.converters.collections.CollectionConverter, com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Object completeItem = readCompleteItem(hierarchicalStreamReader, unmarshallingContext, null);
        if (unmarshallingContext.getRequiredType() == LIST) {
            return Collections.singletonList(completeItem);
        }
        return Collections.singleton(completeItem);
    }
}
