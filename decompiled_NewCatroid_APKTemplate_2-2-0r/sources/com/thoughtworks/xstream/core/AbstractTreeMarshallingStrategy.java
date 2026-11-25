package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.MarshallingStrategy;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/AbstractTreeMarshallingStrategy.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractTreeMarshallingStrategy implements MarshallingStrategy {
    protected abstract TreeMarshaller createMarshallingContext(HierarchicalStreamWriter hierarchicalStreamWriter, ConverterLookup converterLookup, Mapper mapper);

    protected abstract TreeUnmarshaller createUnmarshallingContext(Object obj, HierarchicalStreamReader hierarchicalStreamReader, ConverterLookup converterLookup, Mapper mapper);

    @Override // com.thoughtworks.xstream.MarshallingStrategy
    public Object unmarshal(Object obj, HierarchicalStreamReader hierarchicalStreamReader, DataHolder dataHolder, ConverterLookup converterLookup, Mapper mapper) {
        return createUnmarshallingContext(obj, hierarchicalStreamReader, converterLookup, mapper).start(dataHolder);
    }

    @Override // com.thoughtworks.xstream.MarshallingStrategy
    public void marshal(HierarchicalStreamWriter hierarchicalStreamWriter, Object obj, ConverterLookup converterLookup, Mapper mapper, DataHolder dataHolder) {
        createMarshallingContext(hierarchicalStreamWriter, converterLookup, mapper).start(obj, dataHolder);
    }
}
