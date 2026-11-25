package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferenceByXPathMarshaller.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReferenceByXPathMarshaller extends AbstractReferenceMarshaller {
    private final int mode;

    @Override // com.thoughtworks.xstream.core.AbstractReferenceMarshaller
    protected Object createReferenceKey(Path path, Object obj) {
        return path;
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceMarshaller
    protected void fireValidReference(Object obj) {
    }

    public ReferenceByXPathMarshaller(HierarchicalStreamWriter hierarchicalStreamWriter, ConverterLookup converterLookup, Mapper mapper, int i) {
        super(hierarchicalStreamWriter, converterLookup, mapper);
        this.mode = i;
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceMarshaller
    protected String createReference(Path path, Object obj) {
        Path pathRelativeTo = (Path) obj;
        if ((this.mode & ReferenceByXPathMarshallingStrategy.ABSOLUTE) <= 0) {
            pathRelativeTo = path.relativeTo(pathRelativeTo);
        }
        return (this.mode & ReferenceByXPathMarshallingStrategy.SINGLE_NODE) > 0 ? pathRelativeTo.explicit() : pathRelativeTo.toString();
    }
}
