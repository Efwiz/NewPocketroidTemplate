package com.thoughtworks.xstream;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/MarshallingStrategy.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface MarshallingStrategy {
    void marshal(HierarchicalStreamWriter hierarchicalStreamWriter, Object obj, ConverterLookup converterLookup, Mapper mapper, DataHolder dataHolder);

    Object unmarshal(Object obj, HierarchicalStreamReader hierarchicalStreamReader, DataHolder dataHolder, ConverterLookup converterLookup, Mapper mapper);
}
