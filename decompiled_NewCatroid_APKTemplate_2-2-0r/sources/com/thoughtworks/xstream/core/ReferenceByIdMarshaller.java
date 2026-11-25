package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferenceByIdMarshaller.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReferenceByIdMarshaller extends AbstractReferenceMarshaller {
    private final IDGenerator idGenerator;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferenceByIdMarshaller$IDGenerator.class
      classes11.dex
     */
    public interface IDGenerator {
        String next(Object obj);
    }

    public ReferenceByIdMarshaller(HierarchicalStreamWriter hierarchicalStreamWriter, ConverterLookup converterLookup, Mapper mapper, IDGenerator iDGenerator) {
        super(hierarchicalStreamWriter, converterLookup, mapper);
        this.idGenerator = iDGenerator;
    }

    public ReferenceByIdMarshaller(HierarchicalStreamWriter hierarchicalStreamWriter, ConverterLookup converterLookup, Mapper mapper) {
        this(hierarchicalStreamWriter, converterLookup, mapper, new SequenceGenerator(1));
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceMarshaller
    protected String createReference(Path path, Object obj) {
        return obj.toString();
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceMarshaller
    protected Object createReferenceKey(Path path, Object obj) {
        return this.idGenerator.next(obj);
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceMarshaller
    protected void fireValidReference(Object obj) {
        String strAliasForSystemAttribute = getMapper().aliasForSystemAttribute("id");
        if (strAliasForSystemAttribute != null) {
            this.writer.addAttribute(strAliasForSystemAttribute, obj.toString());
        }
    }
}
