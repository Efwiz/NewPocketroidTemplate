package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferenceByXPathMarshallingStrategy.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReferenceByXPathMarshallingStrategy extends AbstractTreeMarshallingStrategy {
    public static int ABSOLUTE = 1;
    public static int RELATIVE = 0;
    public static int SINGLE_NODE = 2;
    private final int mode;

    public ReferenceByXPathMarshallingStrategy(int i) {
        this.mode = i;
    }

    @Override // com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy
    protected TreeUnmarshaller createUnmarshallingContext(Object obj, HierarchicalStreamReader hierarchicalStreamReader, ConverterLookup converterLookup, Mapper mapper) {
        return new ReferenceByXPathUnmarshaller(obj, hierarchicalStreamReader, converterLookup, mapper);
    }

    @Override // com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy
    protected TreeMarshaller createMarshallingContext(HierarchicalStreamWriter hierarchicalStreamWriter, ConverterLookup converterLookup, Mapper mapper) {
        return new ReferenceByXPathMarshaller(hierarchicalStreamWriter, converterLookup, mapper, this.mode);
    }
}
