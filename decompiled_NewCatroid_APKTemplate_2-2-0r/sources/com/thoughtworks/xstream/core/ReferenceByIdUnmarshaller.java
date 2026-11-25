package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferenceByIdUnmarshaller.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReferenceByIdUnmarshaller extends AbstractReferenceUnmarshaller {
    @Override // com.thoughtworks.xstream.core.AbstractReferenceUnmarshaller
    protected Object getReferenceKey(String str) {
        return str;
    }

    public ReferenceByIdUnmarshaller(Object obj, HierarchicalStreamReader hierarchicalStreamReader, ConverterLookup converterLookup, Mapper mapper) {
        super(obj, hierarchicalStreamReader, converterLookup, mapper);
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceUnmarshaller
    protected Object getCurrentReferenceKey() {
        String strAliasForSystemAttribute = getMapper().aliasForSystemAttribute("id");
        if (strAliasForSystemAttribute == null) {
            return null;
        }
        return this.reader.getAttribute(strAliasForSystemAttribute);
    }
}
