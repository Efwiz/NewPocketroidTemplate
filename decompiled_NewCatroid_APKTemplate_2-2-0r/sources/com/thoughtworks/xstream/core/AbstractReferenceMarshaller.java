package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.core.util.ObjectIdDictionary;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.io.path.PathTracker;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/AbstractReferenceMarshaller.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractReferenceMarshaller extends TreeMarshaller implements MarshallingContext {
    private ObjectIdDictionary implicitElements;
    private Path lastPath;
    private PathTracker pathTracker;
    private ObjectIdDictionary references;

    protected abstract String createReference(Path path, Object obj);

    protected abstract Object createReferenceKey(Path path, Object obj);

    protected abstract void fireValidReference(Object obj);

    public AbstractReferenceMarshaller(HierarchicalStreamWriter hierarchicalStreamWriter, ConverterLookup converterLookup, Mapper mapper) {
        super(hierarchicalStreamWriter, converterLookup, mapper);
        this.references = new ObjectIdDictionary();
        this.implicitElements = new ObjectIdDictionary();
        this.pathTracker = new PathTracker();
        this.writer = new PathTrackingWriter(hierarchicalStreamWriter, this.pathTracker);
    }

    @Override // com.thoughtworks.xstream.core.TreeMarshaller
    public void convert(Object obj, Converter converter) {
        final Object item;
        if (getMapper().isImmutableValueType(obj.getClass())) {
            converter.marshal(obj, this.writer, this);
            return;
        }
        final Path path = this.pathTracker.getPath();
        Id id2 = (Id) this.references.lookupId(obj);
        if (id2 != null && id2.getPath() != path) {
            String strAliasForSystemAttribute = getMapper().aliasForSystemAttribute("reference");
            if (strAliasForSystemAttribute != null) {
                this.writer.addAttribute(strAliasForSystemAttribute, createReference(path, id2.getItem()));
                return;
            }
            return;
        }
        if (id2 == null) {
            item = createReferenceKey(path, obj);
        } else {
            item = id2.getItem();
        }
        Path path2 = this.lastPath;
        if (path2 == null || !path.isAncestor(path2)) {
            fireValidReference(item);
            this.lastPath = path;
            this.references.associateId(obj, new Id(item, path));
        }
        converter.marshal(obj, this.writer, new ReferencingMarshallingContext() { // from class: com.thoughtworks.xstream.core.AbstractReferenceMarshaller.1
            @Override // com.thoughtworks.xstream.converters.DataHolder
            public void put(Object obj2, Object obj3) {
                AbstractReferenceMarshaller.this.put(obj2, obj3);
            }

            @Override // com.thoughtworks.xstream.converters.DataHolder
            public Iterator keys() {
                return AbstractReferenceMarshaller.this.keys();
            }

            @Override // com.thoughtworks.xstream.converters.DataHolder
            public Object get(Object obj2) {
                return AbstractReferenceMarshaller.this.get(obj2);
            }

            @Override // com.thoughtworks.xstream.converters.MarshallingContext
            public void convertAnother(Object obj2, Converter converter2) {
                AbstractReferenceMarshaller.this.convertAnother(obj2, converter2);
            }

            @Override // com.thoughtworks.xstream.converters.MarshallingContext
            public void convertAnother(Object obj2) {
                AbstractReferenceMarshaller.this.convertAnother(obj2);
            }

            @Override // com.thoughtworks.xstream.core.ReferencingMarshallingContext
            public void replace(Object obj2, Object obj3) {
                AbstractReferenceMarshaller.this.references.associateId(obj3, new Id(item, path));
            }

            @Override // com.thoughtworks.xstream.core.ReferencingMarshallingContext
            public Object lookupReference(Object obj2) {
                return ((Id) AbstractReferenceMarshaller.this.references.lookupId(obj2)).getItem();
            }

            @Override // com.thoughtworks.xstream.core.ReferencingMarshallingContext
            public Path currentPath() {
                return AbstractReferenceMarshaller.this.pathTracker.getPath();
            }

            @Override // com.thoughtworks.xstream.core.ReferencingMarshallingContext
            public void registerImplicit(Object obj2) {
                if (!AbstractReferenceMarshaller.this.implicitElements.containsId(obj2)) {
                    AbstractReferenceMarshaller.this.implicitElements.associateId(obj2, item);
                    return;
                }
                throw new ReferencedImplicitElementException(obj2, path);
            }
        });
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/AbstractReferenceMarshaller$Id.class
      classes11.dex
     */
    private static class Id {
        private Object item;
        private Path path;

        public Id(Object obj, Path path) {
            this.item = obj;
            this.path = path;
        }

        protected Object getItem() {
            return this.item;
        }

        protected Path getPath() {
            return this.path;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/AbstractReferenceMarshaller$ReferencedImplicitElementException.class
      classes11.dex
     */
    public static class ReferencedImplicitElementException extends ConversionException {
        public ReferencedImplicitElementException(Object obj, Path path) {
            super("Cannot reference implicit element");
            add("implicit-element", obj.toString());
            add("referencing-element", path.toString());
        }
    }
}
